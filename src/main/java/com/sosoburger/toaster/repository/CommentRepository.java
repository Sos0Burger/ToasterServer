package com.sosoburger.toaster.repository;

import com.sosoburger.toaster.dao.CommentDAO;
import com.sosoburger.toaster.dao.PostDAO;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentDAO, Integer> {

    @Query("SELECT p FROM CommentDAO p WHERE p.post.id = :post ORDER BY SIZE(p.users) DESC ")
    List<CommentDAO> findAllByPopularity(@Param("post")Integer post);

    @Query("SELECT p FROM CommentDAO p WHERE p.post.id = :post")
    List<CommentDAO> findAllByIdWithSorting(@Param("post")Integer post, Sort sort);
}
