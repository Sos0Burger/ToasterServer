package com.sosoburger.toaster.rest.controller;

import com.sosoburger.toaster.dao.UserDAO;
import com.sosoburger.toaster.dao.UserProfileDAO;
import com.sosoburger.toaster.dto.rq.RequestUserDTO;
import com.sosoburger.toaster.dto.rs.*;
import com.sosoburger.toaster.mapper.Mapper;
import com.sosoburger.toaster.rest.api.UserApi;
import com.sosoburger.toaster.service.MessageService;
import com.sosoburger.toaster.service.TokenService;
import com.sosoburger.toaster.service.UserProfileService;
import com.sosoburger.toaster.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    private MessageService messageService;

    @Autowired
    public UserController(UserProfileService userProfileService){
        this.userProfileService = userProfileService;
    }

    @Override
    public ResponseEntity<ResponseUserDTO> create(String token, RequestUserDTO requestUserDTO) {
        var verificationToken = tokenService.findByEmailAndToken(requestUserDTO.getEmail(), token);

        var user = userService.create(requestUserDTO);

        var profile = userProfileService.create(user);

        return new ResponseEntity<>(profile.toDTO(new ArrayList<>(), profile), HttpStatus.CREATED);
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

        var response = user.getUserProfileDAO().toDTO(friendList, getUserDetails().getUserProfileDAO());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Integer> auth() {
        userProfileService.updateStatus(getUserDetails().getUserProfileDAO(), true);
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
    public ResponseEntity<List<ResponsePostDTO>> getFriendFeed(String query, Integer page) {
        List<ResponsePostDTO> feed =
                Mapper
                        .postsToDTOList(
                                userProfileService
                                        .getFriendFeed(
                                                getUserDetails().getUserProfileDAO(),
                                                query,
                                                page
                                        ),
                                getUserDetails().getUserProfileDAO()
                        );
        return new ResponseEntity<>(feed, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ResponsePostDTO>> getFeed(String query, Integer page) {
        var response = Mapper.postsToDTOList(userProfileService.getFeed(query, page), getUserDetails().getUserProfileDAO());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteRequest(Integer id) {
        userProfileService.deleteFriendRequest(id, getUserDetails().getUserProfileDAO());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteFriend(Integer id) {
        userProfileService.deleteFriend(id, getUserDetails().getUserProfileDAO());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<FriendDTO>> searchUsers(String query) {
        List<FriendDTO> response =new ArrayList<>();
        try {
            response.add(userProfileService.getUser(Integer.parseInt(query)).toFriendDTO());
        } catch (NumberFormatException ignored){

        }

        response.addAll(Mapper.friendsToDTOList(userProfileService.search(query)));
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<ResponseUserDTO> getUser(Integer id) {
        var user = userProfileService.getUser(id);
        var response = user.toDTO(
                Mapper.friendsToDTOList(userProfileService.findAllByIds(user.getFriends())),
                getUserDetails().getUserProfileDAO()
        );
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<List<ResponsePostDTO>> getUserPosts(Integer id, String query, Integer page) {
        var response = Mapper.postsToDTOList(
                userProfileService.getUserPosts(id, query, page),
                getUserDetails().getUserProfileDAO()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ResponseChatDTO>> getUserChats() {
        var users = userProfileService.getChats(getUserDetails().getUserProfileDAO());

        var response = Mapper.usersToChatDTOList(users, getUserDetails().getUserProfileDAO(), messageService);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> logout() {
        userProfileService.updateStatus(getUserDetails().getUserProfileDAO(), false);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public UserDAO getUserDetails(){
        return userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }


}
