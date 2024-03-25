package com.messenger.Messenger.service;

import com.messenger.Messenger.dao.PostDAO;
import com.messenger.Messenger.dao.UserDAO;
import com.messenger.Messenger.dao.UserProfileDAO;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface UserProfileService {
    UserProfileDAO create(UserDAO userDAO);

    UserProfileDAO getUser(Integer id);

    UserProfileDAO sendFriendRequest(Integer senderid, Integer receiverid);

    UserProfileDAO acceptFriendRequest(Integer receiverid, Integer senderid);

    List<UserProfileDAO> getFriends(UserDAO userDAO);

    List<UserProfileDAO> getPending(UserDAO userDAO);

    List<UserProfileDAO> getSent(UserDAO userDAO);

    UserProfileDAO updatePicture(Integer file, UserDAO userDAO);

    UserProfileDAO updateNickname(String nickname, UserDAO userDAO);

    void updateToken(String token, UserDAO userDAO);

    List<PostDAO> getFeed(Integer id, Pageable page);

}
