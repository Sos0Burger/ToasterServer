package com.messenger.Messenger.service;

import com.messenger.Messenger.dao.PostDAO;
import com.messenger.Messenger.dao.UserDAO;
import com.messenger.Messenger.dto.rq.RequestAuth;
import com.messenger.Messenger.dto.rq.RequestUserDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface UserService{
    UserDAO create(RequestUserDTO requestUserDTO);

    UserDAO getUser(Integer id);

    Integer auth(RequestAuth auth);

    UserDAO sendFriendRequest(Integer senderid, Integer receiverid);

    UserDAO acceptFriendRequest(Integer receiverid, Integer senderid);

    List<UserDAO> getFriends(Integer id);

    List<UserDAO> getPending(Integer id);

    List<UserDAO> getSent(Integer id);

    void updatePicture(Integer id, RequestAuth auth, String url);

    void updateNickname(Integer id, RequestAuth auth, String nickname);

    void updateToken(Integer id, String token);

    List<PostDAO> getFeed(Integer id, Pageable page);

}
