package com.sosoburger.toaster.service;

import com.sosoburger.toaster.SortEnum;
import com.sosoburger.toaster.dao.CommentDAO;
import com.sosoburger.toaster.dao.PostDAO;
import com.sosoburger.toaster.dao.UserDAO;
import com.sosoburger.toaster.dao.UserProfileDAO;
import com.sosoburger.toaster.dto.rq.RequestEditPost;
import com.sosoburger.toaster.dto.rq.RequestPostDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    List<PostDAO> getPosts(Integer userid, Pageable pageable);

    PostDAO createPost(RequestPostDTO postDTO, UserDAO user);

    PostDAO get(Integer id);

    PostDAO smashLike(Integer id, UserProfileDAO userProfileDAO);

    List<CommentDAO> getCommentsWithSorting(Integer id, SortEnum sorting);

    PostDAO updatePost(Integer id, RequestEditPost post);
 }
