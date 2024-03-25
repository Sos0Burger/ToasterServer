package com.messenger.Messenger.repository;

import com.messenger.Messenger.dao.MessageDAO;
import com.messenger.Messenger.dao.UserProfileDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<MessageDAO, Integer> {
    @Query(value = "SELECT m FROM MessageDAO m WHERE (m.sender = :sender AND m.receiver = :receiver) OR (m.sender = :receiver AND m.receiver = :sender)")
    Page<MessageDAO> findBySenderAndReceiver(@Param("sender") UserProfileDAO sender, @Param("receiver") UserProfileDAO receiver, Pageable pageable);
}
