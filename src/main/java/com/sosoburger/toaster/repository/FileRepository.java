package com.sosoburger.toaster.repository;

import com.sosoburger.toaster.dao.FileDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileDAO, Integer> {
    FileDAO findByHash(String hash);
}
