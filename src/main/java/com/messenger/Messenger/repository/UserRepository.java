package com.messenger.Messenger.repository;

import com.messenger.Messenger.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserDAO, Integer> {
    List<UserDAO> findByEmail(String email);
    List<UserDAO> findByNickname(String nickname);
}
