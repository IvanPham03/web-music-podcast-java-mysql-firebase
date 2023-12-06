package com.ivanpham.musicapi.model.seedData;

import com.ivanpham.musicapi.model.Track;
import com.ivanpham.musicapi.model.User;
import com.ivanpham.musicapi.model.UserTrack;
import com.ivanpham.musicapi.repository.TrackRepository;
import com.ivanpham.musicapi.repository.UserRepository;
import com.ivanpham.musicapi.repository.UserTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class UserTrackSeeder {
    private final UserTrackRepository userTrackRepository;
    private final UserRepository userRepository;
    private final TrackRepository trackRepository;
    @Autowired
    public UserTrackSeeder(UserTrackRepository userTrackRepository, UserRepository userRepository, TrackRepository trackRepository) {
        this.userTrackRepository = userTrackRepository;
        this.trackRepository = trackRepository;
        this.userRepository = userRepository;
    }

    public void seedUserTracks() {
        if (userTrackRepository.count() == 0) {
            List<UserTrack> userTrackSeeders = createUserTracks();

            // Lưu danh sách tracks vào cơ sở dữ liệu
            userTrackRepository.saveAll(userTrackSeeders);
        }
    }

    // private static void saveArtistPool(List<User> pool, User user){
    //     if (user != null){
    //         pool.add(user);
    //     }
    // }

    // private static void saveTrackPool(List<Track> pool, Track track){
    //     if (track != null){
    //         pool.add(track);
    //     }
    // }

    private List<UserTrack> createUserTracks() {
        List<UserTrack> userTracks = new ArrayList<>();
        // List<User> artistPool = new ArrayList<>();  //pool này để chứa danh sách artist vừa tạo đợi duyệt xem có bị null hay không
        // List<Track> trackPool = new ArrayList<>(); //giống artistPoll nhưng dành cho Track

        // Lấy track
        Track track1 = trackRepository.getByUrl("AnhDaYenBinhToiBietThuongMinh-PhamQuynhAnh-9010380.mp3");
        Track track2 = trackRepository.getByUrl("AnhSeDuaEmVe-NQP-6309479.mp3");

        Track track3 = trackRepository.getByUrl("AnTinhSangTrang-ChauKhaiPhongLeCuong-7976352.mp3");
        Track track4 = trackRepository.getByUrl("ChiemBaoCuoi-Keyo-5992899.mp3");
        Track track5 = trackRepository.getByUrl("ChungTaBayLauNayLa-PhamQuynhAnh-9046058.mp3");
        Track track6 = trackRepository.getByUrl("CungXinLa-TrangHanNamKun-5962285.mp3");
        Track track7 = trackRepository.getByUrl("DonDauAnhVanYeu-ChauKhaiPhong-4166657.mp3");
        Track track8 = trackRepository.getByUrl("DreamingBoy-HallasanVietNamSeachains-7561734.mp3");
        Track track9 = trackRepository.getByUrl("EmSeLaCuaAnh-EmDCindyLeDavis-6103424.mp3");
        Track track10 = trackRepository.getByUrl("FallingInLove-QuocAnhVietNam-7266712.mp3");
        Track track11 = trackRepository.getByUrl("KetThucKhongVui-ChauKhaiPhong-2746549.mp3");
        Track track12 = trackRepository.getByUrl("KeVietNgonTinh-ChauKhaiPhongACV-9558544.mp3");
        Track track13 = trackRepository.getByUrl("KhongTronVenNua-ChauKhaiPhongACV-7197914.mp3");
        Track track14 = trackRepository.getByUrl("LiemSiGiTamNay-LinhQueenNonHanTa-6223913.mp3");
        Track track15 = trackRepository.getByUrl("LiemSiGiTamNay-MisabaeTungViu-6504570.mp3");
        Track track16 = trackRepository.getByUrl("LoveMood-KhoaLangLD-6241843.mp3");
        Track track17 = trackRepository.getByUrl("MaiLaGiacMo-DinhUyenLanPham-4680054.mp3");
        Track track18 = trackRepository.getByUrl("NayEmGiOi-NguyenKhoaLangLD-6136321.mp3");
        Track track19 = trackRepository.getByUrl("NgamHoaLeRoi-ChauKhaiPhong-4850041.mp3");
        Track track20 = trackRepository.getByUrl("NguoiTaThanhDoiHetRoiDay-TheSheepTRI-6059865.mp3");
        Track track21 = trackRepository.getByUrl("NoiDauMinhAnh-ChauKhaiPhongTrinhDinhQuang-4361102.mp3");
        Track track22 = trackRepository.getByUrl("NotAFairyTale-OrangeDTAP-9217147.mp3");
        Track track23 = trackRepository.getByUrl("QuenMotNguoiTungYeu-ChauKhaiPhong-6352300.mp3");
        Track track24 = trackRepository.getByUrl("SayAnh-MyMyDlowVietNam-7565091.mp3");
        Track track25 = trackRepository.getByUrl("TheLaThoi-HoangPhuongTraMy-4178173.mp3");
        Track track26 = trackRepository.getByUrl("ThuongEm-ChauKhaiPhongACV-7803607.mp3");
        Track track27 = trackRepository.getByUrl("ThuongTham-RickyStarKhoa-6464375.mp3");
        Track track28 = trackRepository.getByUrl("TimAcousticCover-Rhy-4184464.mp3");
        Track track29 = trackRepository.getByUrl("ToTinh-JBHaCuong-6183895.mp3");
        Track track30 = trackRepository.getByUrl("YeuLaThe-UMIERightTeeBPBOUNCE-6229650.mp3");

        //lưu vào pool kiểm tra lọc null
        saveTrackPool(trackPool, track1);
        saveTrackPool(trackPool, track2);
        saveTrackPool(trackPool, track3);
        saveTrackPool(trackPool, track4);
        saveTrackPool(trackPool, track5);
        saveTrackPool(trackPool, track6);
        saveTrackPool(trackPool, track7);
        saveTrackPool(trackPool, track8);
        saveTrackPool(trackPool, track9);
        saveTrackPool(trackPool, track10);
        saveTrackPool(trackPool, track11);
        saveTrackPool(trackPool, track12);
        saveTrackPool(trackPool, track13);
        saveTrackPool(trackPool, track14);
        saveTrackPool(trackPool, track15);
        saveTrackPool(trackPool, track16);
        saveTrackPool(trackPool, track17);
        saveTrackPool(trackPool, track18);
        saveTrackPool(trackPool, track19);
        saveTrackPool(trackPool, track20);
        saveTrackPool(trackPool, track21);
        saveTrackPool(trackPool, track22);
        saveTrackPool(trackPool, track23);
        saveTrackPool(trackPool, track24);
        saveTrackPool(trackPool, track25);
        saveTrackPool(trackPool, track26);
        saveTrackPool(trackPool, track27);
        saveTrackPool(trackPool, track28);
        saveTrackPool(trackPool, track29);
        saveTrackPool(trackPool, track30);


        // artist
        User artist1 = userRepository.findByEmail("artist1@gmail.com");
        User artist2 = userRepository.findByEmail("artist2@gmail.com");

        User artist3 = userRepository.findByEmail("artist3@gmail.com");
        User artist4 = userRepository.findByEmail("artist4@gmail.com");
        User artist5 = userRepository.findByEmail("artist5@gmail.com");
        User artist6 = userRepository.findByEmail("artist6@gmail.com");


//        lưu vào pool kiểm tra lọc null
        saveArtistPool(artistPool, artist1);
        saveArtistPool(artistPool, artist2);

        saveArtistPool(artistPool, artist3);
        saveArtistPool(artistPool, artist4);
        saveArtistPool(artistPool, artist5);
        saveArtistPool(artistPool, artist6);

//        if(track1 != null && track2 != null && artist1 != null && artist2 != null){
//           UserTrack userTrack1 = new UserTrack(artist2, track1);
//           UserTrack userTrack2 = new UserTrack(artist1, track1);
//           UserTrack userTrack3 = new UserTrack(artist1, track2);
//
//           userTracks.add(userTrack1);
//           userTracks.add(userTrack2);
//           userTracks.add(userTrack3);
//        }

        

        return userTracks;
    }
}
