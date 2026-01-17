package com.example.givy.domain.recommendation.service.command;

import com.example.givy.domain.recommendation.dto.req.TendencyReqDTO;
import com.example.givy.domain.recommendation.dto.res.TendencyResDTO;
import com.example.givy.domain.user.entity.Users;

public interface TendencyCommandService {
    TendencyResDTO.TendencyResultDTO submitTendency(Users user, TendencyReqDTO.TendencySurveyDTO request);
}
