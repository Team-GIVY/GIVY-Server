package com.example.givy.domain.challenge.service.query;

import com.example.givy.domain.challenge.code.StartChallengeErrorCode;
import com.example.givy.domain.challenge.converter.StartChallengeConverter;
import com.example.givy.domain.challenge.dto.res.StartChallengeResDTO;
import com.example.givy.domain.challenge.entity.StartChallenge;
import com.example.givy.domain.challenge.enums.Status;
import com.example.givy.domain.challenge.exception.StartChallengeException;
import com.example.givy.domain.challenge.repository.StartChallengeRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StartChallengeQueryServiceImpl implements StartChallengeQueryService{
    private final StartChallengeRepository startChallengeRepository;

    @Override
    public StartChallengeResDTO.StartChallengeStatusDTO getChallengeStatus(Long userId) {
        StartChallenge challenge = startChallengeRepository.findByUsers_UserId(userId)
                .orElseThrow(() -> new StartChallengeException(StartChallengeErrorCode.USER_CHALLENGE_NOT_FOUND));

        if(challenge.getStatus().equals(Status.IN_PROGRESS)){
            return StartChallengeConverter.toStartChallengeStatusDTO(challenge);
        }

        return null;
    }
}
