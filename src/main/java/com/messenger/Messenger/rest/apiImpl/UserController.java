package com.messenger.Messenger.rest.apiImpl;

import com.messenger.Messenger.dao.UserDAO;
import com.messenger.Messenger.dto.rq.RequestAuth;
import com.messenger.Messenger.dto.rq.RequestUserDTO;
import com.messenger.Messenger.dto.rs.FriendDTO;
import com.messenger.Messenger.dto.rs.ResponsePostDTO;
import com.messenger.Messenger.dto.rs.ResponseUserDTO;
import com.messenger.Messenger.dto.rs.UserSettingsDTO;
import com.messenger.Messenger.rest.api.UserApi;
import com.messenger.Messenger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController implements UserApi {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @Override
    public ResponseEntity<ResponseUserDTO> create(RequestUserDTO requestUserDTO) {
        UserDAO user = userService.create(requestUserDTO);
        return new ResponseEntity<>(user.toDTO(new ArrayList<>()), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseUserDTO> getUser(Integer id) {
        UserDAO user = userService.getUser(id);
        List<FriendDTO> friendList = new ArrayList<>();

        for (var item: userService.getFriends(id)
             ) {
            friendList.add(item.toFriendDTO());
        }

        return new ResponseEntity<>(user.toDTO(friendList), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Integer> auth(String email, String hash) {
        return new ResponseEntity<>(userService.auth(new RequestAuth(email, hash)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<FriendDTO> sendFriendRequest(Integer senderid, Integer receiverid) {
        return new ResponseEntity<>(userService.sendFriendRequest(senderid, receiverid).toFriendDTO(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<FriendDTO> acceptFriendRequest(Integer receiverid, Integer senderid) {
        return new ResponseEntity<>(userService.acceptFriendRequest(receiverid, senderid).toFriendDTO(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<FriendDTO>> getFriends(Integer id) {

        List<UserDAO> friendList = userService.getFriends(id);

        List<FriendDTO> friendDTOList = new ArrayList<>();

        for (var item : friendList
        ) {
            friendDTOList.add(item.toFriendDTO());
        }
        return new ResponseEntity<>(friendDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<FriendDTO>> getPending(Integer id) {

        List<FriendDTO> pendingList = new ArrayList<>();
        for (var item : userService.getPending(id)
        ) {
            pendingList.add(item.toFriendDTO());
        }
        return new ResponseEntity<>(pendingList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<FriendDTO>> getSent(Integer id) {
        List<FriendDTO> sentList = new ArrayList<>();
        for (var item : userService.getSent(id)
        ) {
            sentList.add(item.toFriendDTO());
        }
        return new ResponseEntity<>(sentList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updatePicture(Integer id, RequestAuth auth, String URL) {
        userService.updatePicture(id, auth, URL);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateNickname(Integer id, RequestAuth auth, String nickname) {
        userService.updateNickname(id, auth, nickname);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserSettingsDTO> getSettings(Integer id) {
        return new ResponseEntity<>(userService.getUser(id).toUserSettingsDTO(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateFirebaseToken(Integer id, String token) {
        userService.updateToken(id, token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ResponsePostDTO>> getFeed(Integer id, Integer page) {
        List<ResponsePostDTO> feed = new ArrayList<>();
        for (var item: userService.getFeed(id, PageRequest.of(page, 15, Sort.by(Sort.Direction.DESC, "date")))
             ) {
            feed.add(item.toDTO());
        }
        return new ResponseEntity<>(feed, HttpStatus.OK);
    }


}
