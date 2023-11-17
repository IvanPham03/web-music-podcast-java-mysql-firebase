package com.ivanpham.musicapi.controller;
import java.io.File;
import com.ivanpham.musicapi.model.Track;
import com.ivanpham.musicapi.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//@CrossOrigin("*")
@RestController
@RequestMapping("/tracks")
public class TrackController {
    private static final String UPLOAD_DIR = "/path/to/upload/directory"; // Đường dẫn thư mục lưu trữ file
//    @Autowired
    private TrackRepository trackRepository;
    @GetMapping
    @ResponseBody
    public List<Track> getTracks(){

        return trackRepository.findAll();
    }
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
