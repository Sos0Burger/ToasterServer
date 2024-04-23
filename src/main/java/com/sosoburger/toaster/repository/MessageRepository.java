package com.sosoburger.toaster.repository;

import com.sosoburger.toaster.dao.MessageDAO;
import com.sosoburger.toaster.dao.UserProfileDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<MessageDAO, Integer> {
    @Query(value = "SELECT m FROM MessageDAO m WHERE (m.sender = :sender AND m.receiver = :receiver) OR (m.sender = :receiver AND m.receiver = :sender)")
    Page<MessageDAO> findBySenderAndReceiver(@Param("sender") UserProfileDAO sender, @Param("receiver") UserProfileDAO receiver, Pageable pageable);


    @Query("SELECT m FROM MessageDAO m " +
            "WHERE (m.sender = :sender AND m.receiver = :receiver) OR (m.sender = :receiver AND m.receiver = :sender) " +
            "ORDER BY m.date DESC")
    Page<MessageDAO> findLastMessageBetweenUsers(@Param("sender") UserProfileDAO sender, @Param("receiver") UserProfileDAO receiver, Pageable pageable);

    @Query("SELECT COUNT(m) FROM MessageDAO m WHERE m.sender = :sender AND m.receiver = :receiver AND m.read = false")
    Integer countUnreadMessages(@Param("sender") UserProfileDAO sender, @Param("receiver") UserProfileDAO receiver);



}
