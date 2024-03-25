package com.messenger.Messenger.repository;

import com.messenger.Messenger.dao.UserProfileDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileDAO, Integer> {
    List<UserProfileDAO> findByNickname(String nickname);
}
