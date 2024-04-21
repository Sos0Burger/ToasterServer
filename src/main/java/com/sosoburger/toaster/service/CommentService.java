package com.sosoburger.toaster.service;

import com.sosoburger.toaster.dao.CommentDAO;
import com.sosoburger.toaster.dao.UserDAO;
import com.sosoburger.toaster.dao.UserProfileDAO;
import com.sosoburger.toaster.dto.rq.RequestCommentDTO;

public interface CommentService {
    CommentDAO save(Integer post, UserDAO userDAO, RequestCommentDTO commentDTO);

    CommentDAO update(Integer id, RequestCommentDTO commentDTO);

    CommentDAO get(Integer id);

    void delete(Integer id);

    CommentDAO smashLike(Integer id, UserProfileDAO userProfileDAO);
}
