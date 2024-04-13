package com.sosoburger.toaster.repository;

import com.sosoburger.toaster.dao.TokenDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenDAO, Integer> {
    TokenDAO findFirstByEmailAndToken(String email, String token);
}
