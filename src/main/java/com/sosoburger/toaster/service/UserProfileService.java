package com.sosoburger.toaster.service;

import com.sosoburger.toaster.dao.PostDAO;
import com.sosoburger.toaster.dao.UserDAO;
import com.sosoburger.toaster.dao.UserProfileDAO;

import java.util.List;


public interface UserProfileService {
    UserProfileDAO create(UserDAO userDAO);

    UserProfileDAO getUser(Integer id);

    UserProfileDAO sendFriendRequest(Integer senderid, Integer receiverid);

    UserProfileDAO acceptFriendRequest(Integer senderid, Integer receiverid);

    List<UserProfileDAO> getFriends(UserDAO userDAO);

    List<UserProfileDAO> getPending(UserDAO userDAO);

    List<UserProfileDAO> getSent(UserDAO userDAO);

    UserProfileDAO updatePicture(Integer file, UserDAO userDAO);

    UserProfileDAO updateNickname(String nickname, UserDAO userDAO);

    void updateToken(String token, UserDAO userDAO);

    List<PostDAO> getFriendFeed(UserProfileDAO user, String query, Integer page);

    void deleteFriendRequest(Integer id, UserProfileDAO userProfile);

    void deleteFriend(Integer id, UserProfileDAO userProfile);
    List<UserProfileDAO> search(String search);
    List<UserProfileDAO> findAllByIds(List<Integer> ids);
    List<PostDAO> getUserPosts(Integer id, String query, Integer page);

    UserProfileDAO updateStatus(UserProfileDAO user, Boolean status);

    List<UserProfileDAO> getChats(UserProfileDAO user);

    List<PostDAO> getFeed(String query, Integer page);
}
