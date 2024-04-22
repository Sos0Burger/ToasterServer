package com.sosoburger.toaster.repository;

import com.sosoburger.toaster.dao.UserProfileDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileDAO, Integer> {
    List<UserProfileDAO> findByNickname(String nickname);

    List<UserProfileDAO> findByNicknameContainingIgnoreCase(String nickname);
}
