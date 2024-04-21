package com.sosoburger.toaster.service.impl;

import com.sosoburger.toaster.dao.FileDAO;
import com.sosoburger.toaster.dao.PostDAO;
import com.sosoburger.toaster.dao.UserDAO;
import com.sosoburger.toaster.dao.UserProfileDAO;
import com.sosoburger.toaster.dto.rq.RequestPostDTO;
import com.sosoburger.toaster.exception.NotFoundException;
import com.sosoburger.toaster.repository.FileRepository;
import com.sosoburger.toaster.repository.PostRepository;
import com.sosoburger.toaster.repository.UserProfileRepository;
import com.sosoburger.toaster.service.PostService;
import com.sosoburger.toaster.service.UserProfileService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {

    private final UserProfileService userProfileService;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    public PostServiceImpl(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }


    @SneakyThrows
    @Override
    public List<PostDAO> getPosts(Integer userid, Pageable pageable) {
        return postRepository
                .findByCreator(userProfileService.getUser(userid), pageable)
                .getContent();
    }

    @SneakyThrows
    @Override
    public PostDAO createPost(RequestPostDTO postDTO, UserDAO user) {
        UserProfileDAO creator = user.getUserProfileDAO();
        List<FileDAO> attachments = new ArrayList<>(fileRepository.findAllById(postDTO.getAttachments()));

        PostDAO post = postRepository.save(postDTO.toDAO(creator, attachments));

        for (var item: creator.getFriends()
             ) {
            UserProfileDAO userProfileDAO = userProfileService.getUser(item);
            userProfileDAO.getFeed().add(post);
            userProfileRepository.save(userProfileDAO);
        }
        return post;
    }

    @Override
    public PostDAO get(Integer id) {
        if (postRepository.existsById(id)){
            return postRepository.findById(id).get();
        }
        throw new NotFoundException("Пост с таким ID не существует");
    }

    @Override
    public PostDAO smashLike(Integer id, UserProfileDAO userProfileDAO) {
        var post = get(id);
        if (!post.getUsers().remove(userProfileDAO)){
            post.getUsers().add(userProfileDAO);
        }
        return postRepository.save(post);
    }
}
