package com.messenger.Messenger.repository;

import com.messenger.Messenger.dao.PostDAO;
import com.messenger.Messenger.dao.UserProfileDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostDAO, Integer> {
    Page<PostDAO> findByCreator(UserProfileDAO creator, Pageable pageable);
}
