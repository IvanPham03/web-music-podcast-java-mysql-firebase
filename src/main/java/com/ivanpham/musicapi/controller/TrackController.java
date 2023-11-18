package com.ivanpham.musicapi.controller;
import java.io.File;
import com.ivanpham.musicapi.model.Track;
import com.ivanpham.musicapi.repository.TrackRepository;
import com.ivanpham.musicapi.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

//@CrossOrigin("*")
@RestController
@RequestMapping("/tracks")
public class TrackController {
    private static final String UPLOAD_DIR = "/path/to/upload/directory"; // Đường dẫn thư mục lưu trữ file
    @Autowired
    private TrackRepository trackRepository;
    private TrackService trackService;
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Track>> getTracks(){

        return ResponseEntity.ok(trackRepository.findAll());

    }
        @GetMapping("/{id}")
    public ResponseEntity<Track> getTrackById(@PathVariable Long id) {
        try {
            // Sử dụng findById để lấy đối tượng theo ID
            Optional<Track> optionalTrack = trackRepository.findById(id);

            // Kiểm tra xem đối tượng có tồn tại hay không
            if (optionalTrack.isPresent()) {
                // Nếu tồn tại, trả về đối tượng Track
                return  ResponseEntity.ok(optionalTrack.get());
            } else {
                // Nếu không tồn tại, trả về HTTP 404 Not Found
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ và trả về HTTP 500 Internal Server Error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ResponseBody
    @GetMapping("/search")
    public List<Track> searchTrack( @RequestParam("keywords") Optional<String> keyword){
        List<Track> list = this.trackRepository.findAll();
        if(keyword.isPresent()){
            list = this.trackRepository.searchTrack(keyword.orElse(""));

        }
        return list;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadTrack(@RequestPart("track")Track track, @RequestPart("file") MultipartFile multipartFile) throws Exception {
        if(trackRepository.existsByTrackName(track.getTrackName())){
            return ResponseEntity.badRequest().body("Tên đã tồn tại");
        }else {
            System.out.println("Đang tải lên...");
            trackRepository.save(track);

        }
        return  new ResponseEntity<>("Track được thêm thành công", HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public  ResponseEntity<Track> updateTrack(@PathVariable String id, @RequestBody Track trackdata){
        Optional<Track> optionalTrack =trackRepository.findById(Long.valueOf(id));

        if(optionalTrack.isPresent()){
            Track track =  optionalTrack.get();

            //Kiem tra cac thong tin cap nhat
            if(trackdata.getTrackName() != null){
                track.setTrackName(trackdata.getTrackName());
            }
            if ((trackdata.getArtistId()) != null){
                track.setArtistId(trackdata.getArtistId());
            }
            if ((trackdata.getDescription()) != null){
                track.setArtistId(trackdata.getDescription());
            }
            if ((trackdata.getTimestamp()) != null){
                track.setArtistId(String.valueOf(trackdata.getTimestamp()));
            }
            if ((trackdata.getArtistTracks()) != null){
                track.setArtistId(trackdata.getArtistTracks().toString());
            }
            if ((trackdata.getAlbumTracks()) != null){
                track.setArtistId(trackdata.getAlbumTracks().toString());
            }
            trackRepository.save(track);
            return ResponseEntity.ok(track);
        }else {
            return ResponseEntity.notFound().build();
        }

    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<Track> deleteTrack(@PathVariable String id){
        if(trackRepository.existsById(Long.valueOf(id))){
            trackRepository.deleteById(Long.valueOf(id));
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/file/{id}")
    public ResponseEntity<?> getMusicFile(@PathVariable Long id) {
        // Lấy thông tin về file nhạc từ cơ sở dữ liệu (ví dụ: từ TrackRepository)
        Track track = trackRepository.findById(id).orElse(null);

        if (track == null) {
            return ResponseEntity.notFound().build();
        }

        // Đường dẫn đến file nhạc trong thư mục hoặc hệ thống lưu trữ

//        String trackPath = System.getProperty("user.dir") + File.separator + "data_song";
//        String trackPath = getClass().getResource(id + "mp3").getPath();
        Path trackPath = Paths.get("src/main/resources/data_song/" + track.getId() + ".mp3");

        try {
            // Đọc dữ liệu từ file nhạc và chuyển đổi thành ByteArrayResource
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes((trackPath)));

            // Trả về file nhạc dưới dạng dữ liệu nhị phân
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, track.getTrackName())
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//        @PostMapping("/upload")
//    public ResponseEntity<String> addTrack(@RequestBody Track track, @RequestParam("file") MultipartFile file) {
//        try {
//            // Thực hiện logic kiểm tra hoặc xử lý trước khi chèn vào cơ sở dữ liệu (nếu cần)
//            if (track.getTrackName() == null || track.getTrackName().isEmpty()) {
//                // Nếu tên bài hát không hợp lệ, ném ngoại lệ và xử lý
//                throw new IllegalArgumentException("Tên bài hát không được để trống.");
//            }
//
//            // Kiểm tra file MP3 tồn tại
//            if (file.isEmpty() || !file.getOriginalFilename().toLowerCase().endsWith(".mp3")) {
//                return new ResponseEntity<>("File không hợp lệ.", HttpStatus.BAD_REQUEST);
//            }
//            // Chèn track vào cơ sở dữ liệu
//            trackRepository.save(track);
//            // Trả về thông báo thành công
//            return new ResponseEntity<>("Track được thêm thành công", HttpStatus.CREATED);
//        } catch (Exception e) {
//            // Xử lý lỗi nếu có
//            return new ResponseEntity<>("Thất bại khi thêm track: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @GetMapping("/{id}")
//    public ResponseEntity<Track> getTrackById(@PathVariable Long id) {
//        try {
//            // Sử dụng findById để lấy đối tượng theo ID
//            java.util.Optional<Track> optionalTrack = trackRepository.findById(id);
//
//            // Kiểm tra xem đối tượng có tồn tại hay không
//            if (optionalTrack.isPresent()) {
//                // Nếu tồn tại, trả về đối tượng Track
//                return new ResponseEntity<>(optionalTrack.get(), HttpStatus.OK);
//            } else {
//                // Nếu không tồn tại, trả về HTTP 404 Not Found
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            // Xử lý ngoại lệ và trả về HTTP 500 Internal Server Error
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @PostMapping("/upload")
//    public ResponseEntity<String> addTrack(@RequestBody Track track, @RequestParam("file") MultipartFile file) {
//        try {
//            // Thực hiện logic kiểm tra hoặc xử lý trước khi chèn vào cơ sở dữ liệu (nếu cần)
//            if (track.getTrackName() == null || track.getTrackName().isEmpty()) {
//                // Nếu tên bài hát không hợp lệ, ném ngoại lệ và xử lý
//                throw new IllegalArgumentException("Tên bài hát không được để trống.");
//            }
//
//            // Kiểm tra file MP3 tồn tại
//            if (file.isEmpty() || !file.getOriginalFilename().toLowerCase().endsWith(".mp3")) {
//                return new ResponseEntity<>("File không hợp lệ.", HttpStatus.BAD_REQUEST);
//            }
//            // Chèn track vào cơ sở dữ liệu
//            trackRepository.save(track);
//            // Trả về thông báo thành công
//            return new ResponseEntity<>("Track inserted successfully", HttpStatus.CREATED);
//        } catch (Exception e) {
//            // Xử lý lỗi nếu có
//            return new ResponseEntity<>("Failed to insert track: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    public boolean isMp3FileExists(String filePath) {
//        File file = new File(filePath);
//        return file.exists();
//    }
}
