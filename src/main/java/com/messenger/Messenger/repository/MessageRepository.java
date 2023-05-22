package com.messenger.Messenger.repository;

import com.messenger.Messenger.dao.MessageDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageDAO, Integer> {
}
