package com.ivanpham.musicapi.model.seedData;

import com.ivanpham.musicapi.model.Track;
import com.ivanpham.musicapi.model.User;
import com.ivanpham.musicapi.repository.TrackRepository;
import com.ivanpham.musicapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrackSeeder {

    private final TrackRepository trackRepository;
    private final UserRepository userRepository2;

    @Autowired
    public TrackSeeder(TrackRepository trackRepository, UserRepository userRepository2) {
        this.trackRepository = trackRepository;
        this.userRepository2 = userRepository2;
    }

    public void seedTracks() {
        if (trackRepository.count() == 0) {
            List<Track> tracks = createTracks();
            // Lưu danh sách tracks vào cơ sở dữ liệu
            trackRepository.saveAll(tracks);
        }
    }

    private List<Track> createTracks() {
        List<Track> tracks = new ArrayList<>();
        User user1 = userRepository2.findByEmail("admin1@gmail.com");
//        User user2 = userRepository2.findByEmail("");

        if(user1 != null){
            // Tạo các đối tượng Track và thêm vào danh sách tracks
            Track track1 = new Track("Anh đã yên bình, tôi biết thương mình", "AnhDaYenBinhToiBietThuongMinh-PhamQuynhAnh-9010380.mp3","ballad", user1);
            Track track2 = new Track("Anh sẽ đưa em về", "AnhSeDuaEmVe-NQP-6309479.mp3", "ballad", user1);

            Track track3 = new Track("Ân tình sang trang", "AnTinhSangTrang-ChauKhaiPhongLeCuong-7976352.mp3", "ballad", user1);
            Track track4 = new Track("Chiêm bao cuối", "ChiemBaoCuoi-Keyo-5992899.mp3", "ballad", user1);
            Track track5 = new Track("Chúng ta bấy lâu nay là", "ChungTaBayLauNayLa-PhamQuynhAnh-9046058.mp3", "ballad", user1);
            Track track6 = new Track("Cũng xin là", "CungXinLa-TrangHanNamKun-5962285.mp3", "ballad", user1);
            Track track7 = new Track("Đớn đau anh vẫn yêu", "DonDauAnhVanYeu-ChauKhaiPhong-4166657.mp3", "ballad", user1);
            Track track8 = new Track("Dreaming boy", "DreamingBoy-HallasanVietNamSeachains-7561734.mp3", "pop", user1);
            Track track9 = new Track("Em sẽ là của anh", "EmSeLaCuaAnh-EmDCindyLeDavis-6103424.mp3", "ballad", user1);
            Track track10 = new Track("Falling in love", "FallingInLove-QuocAnhVietNam-7266712.mp3", "pop", user1);
            Track track11 = new Track("Kết thúc không vui", "KetThucKhongVui-ChauKhaiPhong-2746549.mp3", "ballad", user1);
            Track track12 = new Track("Kẻ viết ngôn tình", "KeVietNgonTinh-ChauKhaiPhongACV-9558544.mp3", "ballad", user1);
            Track track13 = new Track("Không trọn vẹn nữa", "KhongTronVenNua-ChauKhaiPhongACV-7197914.mp3", "ballad", user1);
            Track track14 = new Track("Liêm sĩ gì tầm này", "LiemSiGiTamNay-LinhQueenNonHanTa-6223913.mp3", "ballad", user1);
            Track track15 = new Track("Liêm sĩ gì tầm này", "LiemSiGiTamNay-MisabaeTungViu-6504570.mp3", "ballad", user1);
            Track track16 = new Track("Love mood", "LoveMood-KhoaLangLD-6241843.mp3", "ballad", user1);
            Track track17 = new Track("Mãi là giấc mơ", "MaiLaGiacMo-DinhUyenLanPham-4680054.mp3", "ballad", user1);
            Track track18 = new Track("Này em gì ơi", "NayEmGiOi-NguyenKhoaLangLD-6136321.mp3", "ballad", user1);
            Track track19 = new Track("Ngắm hoa lệ rơi", "NgamHoaLeRoi-ChauKhaiPhong-4850041.mp3", "ballad", user1);
            Track track20 = new Track("Người ta thành đôi hết rồi đấy", "NguoiTaThanhDoiHetRoiDay-TheSheepTRI-6059865.mp3", "ballad", user1);
            Track track21 = new Track("Nỗi đau mình anh", "NoiDauMinhAnh-ChauKhaiPhongTrinhDinhQuang-4361102.mp3", "ballad", user1);
            Track track22 = new Track("Not a fairy tale", "NotAFairyTale-OrangeDTAP-9217147.mp3", "pop", user1);
            Track track23 = new Track("Quên một người từng yêu", "QuenMotNguoiTungYeu-ChauKhaiPhong-6352300.mp3", "ballad", user1);
            Track track24 = new Track("Say anh", "SayAnh-MyMyDlowVietNam-7565091.mp3", "ballad", user1);
            Track track25 = new Track("Thế là thôi", "TheLaThoi-HoangPhuongTraMy-4178173.mp3", "ballad", user1);
            Track track26 = new Track("Thương em", "ThuongEm-ChauKhaiPhongACV-7803607.mp3", "ballad", user1);
            Track track27 = new Track("Thương thầm", "ThuongTham-RickyStarKhoa-6464375.mp3", "ballad", user1);
            Track track28 = new Track("Tìm Acoustic cover", "TimAcousticCover-Rhy-4184464.mp3", "ballad", user1);
            Track track29 = new Track("Tỏ tình", "ToTinh-JBHaCuong-6183895.mp3", "ballad", user1);
            Track track30 = new Track("Yêu là thế", "YeuLaThe-UMIERightTeeBPBOUNCE-6229650.mp3", "ballad", user1);


            tracks.add(track1);
            tracks.add(track2);

            tracks.add(track3);
            tracks.add(track4);
            tracks.add(track5);
            tracks.add(track6);
            tracks.add(track7);
            tracks.add(track8);
            tracks.add(track9);
            tracks.add(track10);
            tracks.add(track11);
            tracks.add(track12);
            tracks.add(track13);
            tracks.add(track14);
            tracks.add(track15);
            tracks.add(track16);
            tracks.add(track17);
            tracks.add(track18);
            tracks.add(track19);
            tracks.add(track20);
            tracks.add(track21);
            tracks.add(track22);
            tracks.add(track23);
            tracks.add(track24);
            tracks.add(track25);
            tracks.add(track26);
            tracks.add(track27);
            tracks.add(track28);
            tracks.add(track29);
            tracks.add(track30);

        }


        return tracks;
    }
}
