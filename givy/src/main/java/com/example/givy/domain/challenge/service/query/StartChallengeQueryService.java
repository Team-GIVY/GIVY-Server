package com.example.givy.domain.challenge.service.query;

import com.example.givy.domain.challenge.dto.res.StartChallengeResDTO;

public interface StartChallengeQueryService {
    StartChallengeResDTO.StartChallengeStatusDTO getChallengeStatus(Long userId);
}
