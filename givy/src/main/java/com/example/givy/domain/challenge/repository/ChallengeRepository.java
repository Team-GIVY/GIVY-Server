package com.example.givy.domain.challenge.repository;

import com.example.givy.domain.challenge.entity.StartChallenge;
import com.example.givy.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<StartChallenge,Long> {
    Optional<StartChallenge> findByUser(Users user);
}
