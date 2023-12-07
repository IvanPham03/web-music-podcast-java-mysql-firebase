package com.ivanpham.musicapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ivanpham.musicapi.model.Playlist;
import com.ivanpham.musicapi.model.PlaylistTrack;
import com.ivanpham.musicapi.model.Track;
import com.ivanpham.musicapi.model.View;
import com.ivanpham.musicapi.repository.PlaylistRepository;
import com.ivanpham.musicapi.repository.PlaylistTrackRepository;
import com.ivanpham.musicapi.repository.TrackRepository;
import com.ivanpham.musicapi.repository.UserRepository;
import com.ivanpham.musicapi.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/tracks")
public class TrackController {

    @Autowired
    private TrackRepository trackRepository;
    private TrackService trackService;

    @Autowired
    private UserRepository userRepository2;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private PlaylistTrackRepository playlistTrackRepository;

    @GetMapping
    @JsonView(View.BasicTrack.class)
    public ResponseEntity<List<Track>> getTracks() {
        List<Track> tracks = trackRepository.findAll();
        return ResponseEntity.ok(tracks);
    }

    // Lấy một track ngẫu nhiên trong Track
    @GetMapping("/getOneRandomTrack")
    @JsonView(View.BasicTrack.class)
    public ResponseEntity<Track> getOneRandomTrack() {
        List<Track> tracks = trackRepository.findAll();
        Collections.shuffle(tracks);
        return ResponseEntity.ok(tracks.get(0));
    }

    @GetMapping("/{id}")
    @JsonView(View.BasicTrack.class)
    public ResponseEntity<Track> getTrackById(@PathVariable String id) {
        Optional<Track> optionalTrack = trackRepository.findById(id);
        return optionalTrack.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> getMusicFile(@PathVariable String id) {
        try {
            // Tìm Track dựa trên ID
            Optional<Track> optionalTrack = trackRepository.findById(id);

            if (optionalTrack.isPresent()) {

                Track track = optionalTrack.get();
                String url = track.getUrl();
                // Lấy đường dẫn tới file MP3 trong thư mục resources
                ClassLoader classLoader = getClass().getClassLoader();
                String filePath = classLoader.getResource("dataserver/mp3/" + url).getFile();

                // Đọc dữ liệu từ file nhạc và chuyển đổi thành mảng byte
                Path path = Paths.get(filePath);
                byte[] audioBytes = Files.readAllBytes(path);

                // Tạo header để trình duyệt có thể hiểu và phát nhạc
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", id + ".mp3");
                headers.setContentLength(audioBytes.length);

                // Trả về file nhạc dưới dạng mảng byte
                return new ResponseEntity<>(audioBytes, headers, HttpStatus.OK);
            }
            else{
                return ResponseEntity.notFound().build();
            }

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @JsonView(View.BasicTrack.class)
    public ResponseEntity<Track> addTrack(@RequestBody Track track) {
        try {
            Track savedTrack = trackRepository.save(track);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTrack);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    @JsonView(View.BasicTrack.class)
    public ResponseEntity<Track> updateTrack(@PathVariable String id, @RequestBody Track updatedTrack) {
        Optional<Track> optionalTrack = trackRepository.findById(id);

        if (optionalTrack.isPresent()) {
//            updatedTrack.setId(id); // Make sure the updated track has the same ID
//            Track savedTrack = trackRepository.save(updatedTrack);
            Track savedTrack = optionalTrack.orElse(null);
            savedTrack.setTrackName(updatedTrack.getTrackName());
            savedTrack.setCreateAt(updatedTrack.getCreateAt());
            savedTrack.setUpdateOn(updatedTrack.getUpdateOn());
            savedTrack.setGenre(updatedTrack.getGenre());
            trackRepository.save(savedTrack);

            return ResponseEntity.ok(savedTrack);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Xóa Track
    @DeleteMapping("/{trackId}/deleteBy/{userId}")
    public ResponseEntity<String> deleteTrack(@PathVariable String trackId, @PathVariable String userId) {
        // Kiểm tra Album id này có tồn tại
        if(trackRepository.existsById(trackId)) {
            // User là Admin thì xóa
            if (userRepository2.existsByIdAndRoleAdmin(userId)) { // Xem hàm này trong UserRepository2
                trackRepository.deleteById(trackId);
                return ResponseEntity.ok("Xóa thành công!");
            }
            // User là chủ sở hữu thì xóa
            if (trackRepository.isOwner(trackId, userId)) { // Xem hàm trong trackRepository
                trackRepository.deleteById(trackId);
                return ResponseEntity.ok("Xóa thành công!");
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

    // Thêm Track vào Playlist trong Bảng Playlist_track
    @PostMapping("/{trackId}/add-to-playlist/{playlistId}")
    public ResponseEntity<String> addTrackToPlaylist(@PathVariable String trackId, @PathVariable String playlistId) {
        // Find Track by ID
        Track track = trackRepository.findById(trackId).orElse(null);
        if (track == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy Track với id : " + trackId);
        }
        // Find Playlist by ID
        Playlist playlist = playlistRepository.findById(playlistId).orElse(null);
        if (playlist == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy Playlist với id : " + playlistId);
        }
        // Create PlaylistTrack and save to the database
        PlaylistTrack playlistTrack = new PlaylistTrack(playlist, track);
        playlistTrackRepository.save(playlistTrack);
        return ResponseEntity.ok("Thêm Track vào Playlist thành công");
    }

    // Xóa Track khỏi 1 Playlist
    @DeleteMapping("/{trackId}/remove-from-playlist/{playlistId}")
    public ResponseEntity<String> removeTrackFromPlaylist(@PathVariable String trackId, @PathVariable String playlistId) {
        // Find Track by ID
        Track track = trackRepository.findById(trackId).orElse(null);
        if (track == null) {
            return ResponseEntity.badRequest().body("Không tìm tháy Track có id : " + trackId);
        }

        // Find Playlist by ID
        Playlist playlist = playlistRepository.findById(playlistId).orElse(null);
        if (playlist == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy Playlist có id : " + playlistId);
        }

        // Find and delete PlaylistTrack by Playlist and Track
        PlaylistTrack playlistTrack = playlistTrackRepository.findByPlaylistAndTrack(trackId, playlistId);
        if (playlistTrack != null) {
            playlistTrackRepository.deleteById(playlistTrack.getId());
            return ResponseEntity.ok("Xóa Track khỏi Playlist thành công");
        } else {
            return ResponseEntity.badRequest().body("Không tìm thấy Playlist nào chứa Track này");
        }
    }
    @ResponseBody
    @GetMapping("/search")
    public List<Track> searchTrack( @RequestParam("keywords") Optional<String> keyword) {
        System.out.println(keyword);
        List<Track> list = this.trackRepository.findAll();
        if (keyword.isPresent()) {
            list = this.trackRepository.searchTrack(keyword.orElse(""));
        }
        return list;
    }

    // Lấy Track theo Playlist Id
    @GetMapping("/byPlaylist/{playlistId}")
    @JsonView(View.BasicTrack.class)
    public ResponseEntity<List<Track>> getTrackByPlaylistId(@PathVariable String playlistId) {
        List<Track> trackByPlaylistId = trackRepository.findTrackByPlaylistId(playlistId);
        if (trackByPlaylistId != null) {
            return ResponseEntity.ok(trackByPlaylistId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
