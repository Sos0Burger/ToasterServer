package com.messenger.Messenger.rest.apiImpl;

import com.messenger.Messenger.dto.rq.RequestAuth;
import com.messenger.Messenger.dto.rq.RequestUserDTO;
import com.messenger.Messenger.rest.api.UserApi;
import com.messenger.Messenger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements UserApi {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @Override
    public ResponseEntity<?> create(RequestUserDTO requestUserDTO) {
        return userService.create(requestUserDTO);
    }

    @Override
    public ResponseEntity<?> getUser(Integer id) {
        return userService.getUser(id);
    }

    @Override
    public ResponseEntity<?> getAll() {
        return userService.getAll();
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
        return userService.delete(id);
    }

    @Override
    public ResponseEntity<?> auth(String email, String hash) {
        return userService.auth(new RequestAuth(email, hash));
    }

    @Override
    public ResponseEntity<?> getByIds(List<Integer> ids) {
        return userService.findByIds(ids);
    }

    @Override
    public ResponseEntity<?> sendFriendRequest(Integer senderid, Integer receiverid) {
        return userService.sendFriendRequest(senderid, receiverid);
    }

    @Override
    public ResponseEntity<?> acceptFriendRequest(Integer receiverid, Integer senderid) {
        return userService.acceptFriendRequest(receiverid, senderid);
    }

    @Override
    public ResponseEntity<?> getFriends(Integer id) {
        return userService.getFriends(id);
    }

    @Override
    public ResponseEntity<?> getPending(Integer id) {
        return userService.getPending(id);
    }

    @Override
    public ResponseEntity<?> getSent(Integer id) {
        return userService.getSent(id);
    }

}
