package com.sosoburger.toaster.repository;

import com.sosoburger.toaster.dao.PostDAO;
import com.sosoburger.toaster.dao.UserProfileDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostDAO, Integer> {
    Page<PostDAO> findByCreator(UserProfileDAO creator, Pageable pageable);

    List<PostDAO> findByCreatorAndTextContainingIgnoreCase(UserProfileDAO creator, String text, Pageable pageable);
}
