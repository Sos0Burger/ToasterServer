package com.sosoburger.toaster.repository;

import com.sosoburger.toaster.dao.UserProfileDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileDAO, Integer> {
    List<UserProfileDAO> findByNickname(String nickname);

    List<UserProfileDAO> findByNicknameContainingIgnoreCase(String nickname);

    @Query("SELECT DISTINCT CASE WHEN m.sender = :user THEN m.receiver.id ELSE m.sender.id END " +
            "FROM MessageDAO m " +
            "WHERE m.sender = :user OR m.receiver = :user")
    Set<Integer> getChats(@Param("user") UserProfileDAO user);
}
