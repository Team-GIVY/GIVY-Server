package com.example.givy.domain.user.repository;

import com.example.givy.domain.user.entity.UserSecuritiesAccount;
import com.example.givy.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSecuritiesAccountRepository extends JpaRepository<UserSecuritiesAccount,Long> {
    Optional<UserSecuritiesAccount> findByUser(Users user);
}
