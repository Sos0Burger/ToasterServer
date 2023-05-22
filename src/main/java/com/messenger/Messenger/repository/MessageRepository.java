package com.messenger.Messenger.repository;

import com.messenger.Messenger.dao.MessageDAO;
import com.messenger.Messenger.dao.UserDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageDAO, Integer> {
    Page<MessageDAO> findBySenderAndReceiver(UserDAO sender, UserDAO receiver, Pageable pageable);
}
