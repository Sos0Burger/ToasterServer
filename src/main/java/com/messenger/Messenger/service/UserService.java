package com.messenger.Messenger.service;

import com.messenger.Messenger.dto.rq.RequestAuth;
import com.messenger.Messenger.dto.rq.RequestUserDTO;
import com.messenger.Messenger.dto.rs.ResponseUserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface UserService{
    ResponseEntity<?> create(RequestUserDTO requestUserDTO);

    ResponseEntity<?> getUser(Integer id);

    ResponseEntity<List<ResponseUserDTO>> getAll();

    ResponseEntity<?> delete(Integer id);

    ResponseEntity<?> auth(RequestAuth auth);
    ResponseEntity<?> findByIds(List<Integer> ids);

    ResponseEntity<?> sendFriendRequest(Integer senderid, Integer receiverid);

    ResponseEntity<?> acceptFriendRequest(Integer receiverid, Integer senderid);

    ResponseEntity<?> getFriends(Integer id);

    ResponseEntity<?> getPending(Integer id);

    ResponseEntity<?> getSent(Integer id);

    ResponseEntity<?> updatePicture(Integer id, RequestAuth auth, String url);

    ResponseEntity<?> updateNickname(Integer id, RequestAuth auth, String nickname);

    ResponseEntity<?> getSettings(Integer id);
}
