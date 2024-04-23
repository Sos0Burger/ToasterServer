package com.sosoburger.toaster.mapper;

import com.sosoburger.toaster.dao.CommentDAO;
import com.sosoburger.toaster.dao.PostDAO;
import com.sosoburger.toaster.dao.UserProfileDAO;
import com.sosoburger.toaster.dto.rs.FriendDTO;
import com.sosoburger.toaster.dto.rs.ResponseChatDTO;
import com.sosoburger.toaster.dto.rs.ResponseCommentDTO;
import com.sosoburger.toaster.dto.rs.ResponsePostDTO;
import com.sosoburger.toaster.service.MessageService;

import java.util.ArrayList;
import java.util.List;

public class Mapper {
    public static List<ResponseCommentDTO> commentsToDTOList(List<CommentDAO> comments, Integer userProfile){
        List<ResponseCommentDTO> response = new ArrayList<>();
        comments.forEach(item->{response.add(item.toDTO(userProfile));});
        return response;
    }
    public static List<FriendDTO> friendsToDTOList(List<UserProfileDAO> userProfiles){
        List<FriendDTO> response = new ArrayList<>();
        for (var item : userProfiles)
         {
            response.add(item.toFriendDTO());
        }
        return response;
    }
    public static List<ResponsePostDTO> postsToDTOList(List<PostDAO> posts, UserProfileDAO userProfileDAO){
        List<ResponsePostDTO> response = new ArrayList<>();
        posts.forEach(item->response.add(item.toDTO(userProfileDAO.getId())));
        return response;
    }
    public static List<ResponseChatDTO> usersToChatDTOList(List<UserProfileDAO> users, UserProfileDAO user, MessageService messageService){
        List<ResponseChatDTO> response = new ArrayList<>();
        users.forEach(item->response.add(item.toResponseChat(messageService, user)));
        return response;
    }
}
