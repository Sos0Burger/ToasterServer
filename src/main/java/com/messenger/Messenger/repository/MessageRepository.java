package com.messenger.Messenger.repository;

import com.messenger.Messenger.dao.MessageDAO;
import com.messenger.Messenger.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageDAO, Integer> {
    List<MessageDAO> findBySenderAndReceiver(UserDAO sender, UserDAO receiver);
}
