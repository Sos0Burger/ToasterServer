package com.sosoburger.toaster.repository;

import com.sosoburger.toaster.dao.PostDAO;
import com.sosoburger.toaster.dao.UserProfileDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<PostDAO, Integer> {
    Page<PostDAO> findByCreator(UserProfileDAO creator, Pageable pageable);

    List<PostDAO> findByCreatorAndTextContainingIgnoreCase(UserProfileDAO creator, String text, Pageable pageable);


    @Query("SELECT p FROM PostDAO p " +
            "JOIN UserProfileDAO u ON p.creator.id = u.id " +
            "JOIN u.friends f " +
            "WHERE f = :user AND LOWER(p.text) LIKE LOWER(concat('%', concat(:query, '%')))")
    List<PostDAO> getFeed(@Param("user") UserProfileDAO user, @Param("query") String query, Pageable pageable);
}
