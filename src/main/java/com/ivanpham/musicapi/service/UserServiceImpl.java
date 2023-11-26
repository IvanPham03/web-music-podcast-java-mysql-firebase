//package com.ivanpham.musicapi.service;
//
//import com.ivanpham.musicapi.exception.UserException;
//import com.ivanpham.musicapi.hash.Hashing;
//import com.ivanpham.musicapi.model.State;
//import com.ivanpham.musicapi.model.User;
//import com.ivanpham.musicapi.repository.UserRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
////@Repository <User, Long> implements UserRepository<User, Long>
//
//@Service
//public class UserServiceImpl implements UserService {
//    private UserRepository userRepo;
//    private Hashing hash;
//    public UserServiceImpl( UserRepository userRepo, Hashing hash){
//        this.userRepo = userRepo;
//        this.hash = hash;
//    }
//    @Override
//    public User login(String email, String password) {
//        Optional<User> o_user = userRepo.findByEmail(email);
//        //Kiểm tra xem user tồn tại hay không?
//        if(!o_user.isPresent()){
//            throw new UserException("User is not found");
//        }
//        User user = o_user.get();
//        //User muốn login phải được active tài khoản
//        if(user.getState() != State.ACTIVE){
//            throw new UserException("User is not activated");
//
//        }
//        //Kiểm tra password
//        if(hash.validatePassword(password, o_user.get().getPassword())) {
//           return user;
//        }
//        throw new UserException("Password is incorrect");
//    }
//
//    @Override
//    public boolean logout(String email) {
//        return false;
//    }
//
//    @Override
//    public User addUser(String username, String email, String password) {
//        return userRepo.addUser(username, email, hash.hashPassword(password));
//    }
//    @Override
//    public User addUserThenAutoActivate(String username, String email, String password) {
//        return userRepo.addUser(username, email, hash.hashPassword(password), State.ACTIVE);
//
//    }
//    @Override
//    public Boolean activateUser(String activation_code) {
//        return null;
//    }
//
//    @Override
//    public Boolean updatePassword(String email, String password) {
//        return null;
//    }
//
//    @Override
//    public Boolean updateEmail(String email, String newEmail) {
//        return null;
//    }
//
//    @Override
//    public Optional<User> findByEmail(String email) {
//        return Optional.empty();
//    }
//
//    @Override
//    public User findById(String id) {
//        return null;
//    }
//}