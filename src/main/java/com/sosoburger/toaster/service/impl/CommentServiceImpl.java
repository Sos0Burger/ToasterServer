package com.sosoburger.toaster.service.impl;

import com.sosoburger.toaster.dao.CommentDAO;
import com.sosoburger.toaster.dao.UserDAO;
import com.sosoburger.toaster.dao.UserProfileDAO;
import com.sosoburger.toaster.dto.rq.RequestCommentDTO;
import com.sosoburger.toaster.exception.NotFoundException;
import com.sosoburger.toaster.repository.CommentRepository;
import com.sosoburger.toaster.service.CommentService;
import com.sosoburger.toaster.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostService postService;

    @Override
    public CommentDAO save(Integer post, UserDAO userDAO, RequestCommentDTO commentDTO) {
        CommentDAO comment = new CommentDAO(
                null,
                commentDTO.getText(),
                new Date(commentDTO.getDate()),
                postService.get(post),
                userDAO.getUserProfileDAO(),
                new ArrayList<>());
        return commentRepository.save(comment);
    }

    @Override
    public CommentDAO update(Integer id, RequestCommentDTO commentDTO) {
        var comment = get(id);
        comment.setText(commentDTO.getText());
        return commentRepository.save(comment);
    }

    @Override
    public CommentDAO get(Integer id) {
        if (commentRepository.existsById(id)){
            return commentRepository.findById(id).get();
        }
        throw new NotFoundException("Комментария с таким ID не существует");
    }

    @Override
    public void delete(Integer id) {
        commentRepository.delete(get(id));
    }

    @Override
    public CommentDAO smashLike(Integer id, UserProfileDAO userProfileDAO) {
        var comment = get(id);
        if (!comment.getUsers().remove(userProfileDAO)){
            comment.getUsers().add(userProfileDAO);
        }
        return commentRepository.save(comment);
    }
}
