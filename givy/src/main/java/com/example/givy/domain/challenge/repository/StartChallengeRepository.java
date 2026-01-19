package com.example.givy.domain.challenge.repository;

import com.example.givy.domain.challenge.entity.StartChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StartChallengeRepository extends JpaRepository<StartChallenge, Long> {
    Optional<StartChallenge> findByUsers_UserId(Long userId);
}
