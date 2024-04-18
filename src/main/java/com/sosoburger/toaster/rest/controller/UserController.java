package com.sosoburger.toaster.rest.controller;

import com.sosoburger.toaster.dao.UserDAO;
import com.sosoburger.toaster.dao.UserProfileDAO;
import com.sosoburger.toaster.dto.rq.RequestUserDTO;
import com.sosoburger.toaster.dto.rs.FriendDTO;
import com.sosoburger.toaster.dto.rs.ResponsePostDTO;
import com.sosoburger.toaster.dto.rs.ResponseUserDTO;
import com.sosoburger.toaster.dto.rs.UserSettingsDTO;
import com.sosoburger.toaster.rest.api.UserApi;
import com.sosoburger.toaster.service.TokenService;
import com.sosoburger.toaster.service.UserProfileService;
import com.sosoburger.toaster.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController implements UserApi {
    @Autowired
    private final UserProfileService userProfileService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    public UserController(UserProfileService userProfileService){
        this.userProfileService = userProfileService;
    }

    @Override
    public ResponseEntity<ResponseUserDTO> create(String token, RequestUserDTO requestUserDTO) {
        var verificationToken = tokenService.findByEmailAndToken(requestUserDTO.getEmail(), token);

        var user = userService.create(requestUserDTO);

        var profile = userProfileService.create(user);

        return new ResponseEntity<>(profile.toDTO(new ArrayList<>()), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HttpStatus> sendCode(String email) {
        var code = tokenService.create(email);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("Код подтверждения Toaster");
        mailMessage.setTo(email);
        mailMessage.setText("Код подтверждения:\n" + code.getToken());
        mailSender.send(mailMessage);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseUserDTO> getUser() {
        UserDAO user = getUserDetails();
        List<FriendDTO> friendList = new ArrayList<>();

        for (var item: userProfileService.getFriends(user)
             ) {
            friendList.add(item.toFriendDTO());
        }

        return new ResponseEntity<>(user.getUserProfileDAO().toDTO(friendList), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Integer> auth() {
        return new ResponseEntity<>(getUserDetails().getId(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<FriendDTO> sendFriendRequest(Integer receiver) {
        return new ResponseEntity<>(userProfileService.sendFriendRequest(getUserDetails().getId(), receiver).toFriendDTO(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<FriendDTO> acceptFriendRequest(Integer sender) {
        return new ResponseEntity<>(userProfileService.acceptFriendRequest(getUserDetails().getId(), sender).toFriendDTO(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<FriendDTO>> getFriends() {

        List<UserProfileDAO> friendList = userProfileService.getFriends(getUserDetails());

        List<FriendDTO> friendDTOList = new ArrayList<>();

        for (var item : friendList
        ) {
            friendDTOList.add(item.toFriendDTO());
        }
        return new ResponseEntity<>(friendDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<FriendDTO>> getPending() {

        List<FriendDTO> pendingList = new ArrayList<>();
        for (var item : userProfileService.getPending(getUserDetails())
        ) {
            pendingList.add(item.toFriendDTO());
        }
        return new ResponseEntity<>(pendingList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<FriendDTO>> getSent() {
        List<FriendDTO> sentList = new ArrayList<>();
        for (var item : userProfileService.getSent(getUserDetails())
        ) {
            sentList.add(item.toFriendDTO());
        }
        return new ResponseEntity<>(sentList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseUserDTO> updatePicture(Integer file) {
        userProfileService.updatePicture(file, getUserDetails());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateNickname(String nickname) {
        userProfileService.updateNickname(nickname, getUserDetails());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserSettingsDTO> getSettings() {
        return new ResponseEntity<>(userProfileService.getUser(getUserDetails().getId()).toUserSettingsDTO(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateFirebaseToken(String token) {
        userProfileService.updateToken(token, getUserDetails());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ResponsePostDTO>> getFeed(Integer page) {
        List<ResponsePostDTO> feed = new ArrayList<>();
        for (var item: userProfileService.getFeed(getUserDetails().getId(), PageRequest.of(page, 15, Sort.by(Sort.Direction.DESC, "date")))
             ) {
            feed.add(item.toDTO());
        }
        return new ResponseEntity<>(feed, HttpStatus.OK);
    }

    public UserDAO getUserDetails(){
        return userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }


}