package com.sosoburger.toaster.repository;

import com.sosoburger.toaster.dao.CommentDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentDAO, Integer> {
}
