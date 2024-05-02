package com.sosoburger.toaster.repository;

import com.sosoburger.toaster.dao.TagDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<TagDAO, Integer> {
}
