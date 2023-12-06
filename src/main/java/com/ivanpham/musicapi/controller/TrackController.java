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
    public List<Track> searchTrack( @RequestParam("keywords") Optional<String> keyword){
        System.out.println(keyword);
        List<Track> list = this.trackRepository.findAll();
        if(keyword.isPresent()){
            list = this.trackRepository.searchTrack(keyword.orElse(""));
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
//
//    @GetMapping
//    @ResponseBody
//    public ResponseEntity<List<Track>> getTracks(){
//
//        return ResponseEntity.ok(trackRepository.findAll());
//
//    }
//        @GetMapping("/{id}")
//    public ResponseEntity<Track> getTrackById(@PathVariable Long id) {
//        try {
//            // Sử dụng findById để lấy đối tượng theo ID
//            Optional<Track> optionalTrack = trackRepository.findById(id);
//
//            // Kiểm tra xem đối tượng có tồn tại hay không
//            if (optionalTrack.isPresent()) {
//                // Nếu tồn tại, trả về đối tượng Track
//                return  ResponseEntity.ok(optionalTrack.get());
//            } else {
//                // Nếu không tồn tại, trả về HTTP 404 Not Found
//                return ResponseEntity.notFound().build();
//            }
//        } catch (Exception e) {
//            // Xử lý ngoại lệ và trả về HTTP 500 Internal Server Error
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @ResponseBody
//    @GetMapping("/search")
//    public List<Track> searchTrack( @RequestParam("keywords") Optional<String> keyword){
//        List<Track> list = this.trackRepository.findAll();
//        if(keyword.isPresent()){
//            list = this.trackRepository.searchTrack(keyword.orElse(""));
//
//        }
//        return list;
//    }
//
////    @PostMapping("/upload")
////    public ResponseEntity<?> uploadTrack(@RequestPart("track")Track track, @RequestPart("file") MultipartFile multipartFile) throws Exception {
////        if(trackRepository.existsByTrackName(track.getTrackName())){
////            return ResponseEntity.badRequest().body("Tên đã tồn tại");
////        }else {
////            System.out.println("Đang tải lên...");
////            trackRepository.save(track);
////
////        }
////        return  new ResponseEntity<>("Track được thêm thành công", HttpStatus.CREATED);
////    }
//    @PutMapping("/{id}")
//    public  ResponseEntity<Track> updateTrack(@PathVariable String id, @RequestBody Track trackdata){
//        Optional<Track> optionalTrack =trackRepository.findById(Long.valueOf(id));
//
//        if(optionalTrack.isPresent()){
//            Track track =  optionalTrack.get();
//
//            //Kiem tra cac thong tin cap nhat
////            if(trackdata.getTrackName() != null){
////                track.setTrackName(trackdata.getTrackName());
////            }
////            if ((trackdata.getArtistId()) != null){
////                track.setArtistId(trackdata.getArtistId());
////            }
////            if ((trackdata.getDescription()) != null){
////                track.setArtistId(trackdata.getDescription());
////            }
////            if ((trackdata.getTimestamp()) != null){
////                track.setArtistId(String.valueOf(trackdata.getTimestamp()));
////            }
////            if ((trackdata.getArtistTracks()) != null){
////                track.setArtistId(trackdata.getArtistTracks().toString());
////            }
////            if ((trackdata.getAlbumTracks()) != null){
////                track.setArtistId(trackdata.getAlbumTracks().toString());
////            }
//            trackRepository.save(track);
//            return ResponseEntity.ok(track);
//        }else {
//            return ResponseEntity.notFound().build();
//        }
//
//    }
//    @DeleteMapping("/{id}")
//    public  ResponseEntity<Track> deleteTrack(@PathVariable String id){
//        if(trackRepository.existsById(Long.valueOf(id))){
//            trackRepository.deleteById(Long.valueOf(id));
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.notFound().build();
//    }
//    @GetMapping("/file/{id}")
//    public ResponseEntity<?> getMusicFile(@PathVariable Long id) {
//        // Lấy thông tin về file nhạc từ cơ sở dữ liệu (ví dụ: từ TrackRepository)
//        Track track = trackRepository.findById(id).orElse(null);
//
//        if (track == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        // Đường dẫn đến file nhạc trong thư mục hoặc hệ thống lưu trữ
//
////        String trackPath = System.getProperty("user.dir") + File.separator + "data_song";
////        String trackPath = getClass().getResource(id + "mp3").getPath();
//        Path trackPath = Paths.get("src/main/resources/data_song/" + track.getId() + ".mp3");
//
//        try {
//            // Đọc dữ liệu từ file nhạc và chuyển đổi thành ByteArrayResource
//            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes((trackPath)));
//
//            // Trả về file nhạc dưới dạng dữ liệu nhị phân
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, track.getTrackName())
//                    .body(resource);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
////<<<<<<< Track
//
////        @PostMapping("/upload")
////    public ResponseEntity<String> addTrack(@RequestBody Track track, @RequestParam("file") MultipartFile file) {
////        try {
////            // Thực hiện logic kiểm tra hoặc xử lý trước khi chèn vào cơ sở dữ liệu (nếu cần)
////            if (track.getTrackName() == null || track.getTrackName().isEmpty()) {
////                // Nếu tên bài hát không hợp lệ, ném ngoại lệ và xử lý
////                throw new IllegalArgumentException("Tên bài hát không được để trống.");
////            }
////
////            // Kiểm tra file MP3 tồn tại
////            if (file.isEmpty() || !file.getOriginalFilename().toLowerCase().endsWith(".mp3")) {
////                return new ResponseEntity<>("File không hợp lệ.", HttpStatus.BAD_REQUEST);
////            }
////            // Chèn track vào cơ sở dữ liệu
////            trackRepository.save(track);
////            // Trả về thông báo thành công
////            return new ResponseEntity<>("Track được thêm thành công", HttpStatus.CREATED);
////        } catch (Exception e) {
////            // Xử lý lỗi nếu có
////            return new ResponseEntity<>("Thất bại khi thêm track: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
////        }
////    }
////    @GetMapping("/{id}")
////    public ResponseEntity<Track> getTrackById(@PathVariable Long id) {
////        try {
////            // Sử dụng findById để lấy đối tượng theo ID
////            java.util.Optional<Track> optionalTrack = trackRepository.findById(id);
////
////            // Kiểm tra xem đối tượng có tồn tại hay không
////            if (optionalTrack.isPresent()) {
////                // Nếu tồn tại, trả về đối tượng Track
////                return new ResponseEntity<>(optionalTrack.get(), HttpStatus.OK);
////            } else {
////                // Nếu không tồn tại, trả về HTTP 404 Not Found
////                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////            }
////        } catch (Exception e) {
////            // Xử lý ngoại lệ và trả về HTTP 500 Internal Server Error
////            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
////        }
////    }
////=======
////    @GetMapping("/{id}")
////    public ResponseEntity<Track> getTrackById(@PathVariable Long id) {
////        try {
////            // Sử dụng findById để lấy đối tượng theo ID
////            java.util.Optional<Track> optionalTrack = trackRepository.findById(id);
////
////            // Kiểm tra xem đối tượng có tồn tại hay không
////            if (optionalTrack.isPresent()) {
////                // Nếu tồn tại, trả về đối tượng Track
////                return new ResponseEntity<>(optionalTrack.get(), HttpStatus.OK);
////            } else {
////                // Nếu không tồn tại, trả về HTTP 404 Not Found
////                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////            }
////        } catch (Exception e) {
////            // Xử lý ngoại lệ và trả về HTTP 500 Internal Server Error
////            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
////        }
////    }
//
////>>>>>>> main
////    @PostMapping("/upload")
////    public ResponseEntity<String> addTrack(@RequestBody Track track, @RequestParam("file") MultipartFile file) {
////        try {
////            // Thực hiện logic kiểm tra hoặc xử lý trước khi chèn vào cơ sở dữ liệu (nếu cần)
////            if (track.getTrackName() == null || track.getTrackName().isEmpty()) {
////                // Nếu tên bài hát không hợp lệ, ném ngoại lệ và xử lý
////                throw new IllegalArgumentException("Tên bài hát không được để trống.");
////            }
////
////            // Kiểm tra file MP3 tồn tại
////            if (file.isEmpty() || !file.getOriginalFilename().toLowerCase().endsWith(".mp3")) {
////                return new ResponseEntity<>("File không hợp lệ.", HttpStatus.BAD_REQUEST);
////            }
////            // Chèn track vào cơ sở dữ liệu
////            trackRepository.save(track);
////            // Trả về thông báo thành công
////            return new ResponseEntity<>("Track inserted successfully", HttpStatus.CREATED);
////        } catch (Exception e) {
////            // Xử lý lỗi nếu có
////            return new ResponseEntity<>("Failed to insert track: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
////        }
////    }
////    public boolean isMp3FileExists(String filePath) {
////        File file = new File(filePath);
////        return file.exists();
////    }
}
