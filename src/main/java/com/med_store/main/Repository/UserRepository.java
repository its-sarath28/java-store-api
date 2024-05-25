package com.med_store.main.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.med_store.main.Model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
