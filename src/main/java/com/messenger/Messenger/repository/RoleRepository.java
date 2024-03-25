package com.messenger.Messenger.repository;

import com.messenger.Messenger.dao.RoleDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleDAO, Integer> {
    Optional<RoleDAO> findByName(String name);
}
