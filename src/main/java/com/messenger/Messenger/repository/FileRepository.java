package com.messenger.Messenger.repository;

import com.messenger.Messenger.dao.FileDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileDAO, Integer> {
}
