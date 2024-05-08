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
            "WHERE p.creator.id IN :friendIds " +
            "AND LOWER(p.text) LIKE LOWER(concat('%', concat(:query, '%')))")
    List<PostDAO> getFriendFeed(@Param("friendIds") List<Integer> friendIds, @Param("query") String query, Pageable pageable);
    @Query("SELECT DISTINCT p " +
            "FROM PostDAO p " +
            "LEFT JOIN p.attachments f " +
            "LEFT JOIN f.tags t " +
            "WHERE LOWER(p.text) LIKE LOWER(concat('%', concat(:query, '%'))) "
    )
    List<PostDAO> getFeed(@Param("query")String query, Pageable pageable);
}
