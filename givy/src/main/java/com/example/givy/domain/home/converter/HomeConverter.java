package com.example.givy.domain.home.converter;

import com.example.givy.domain.commerce.entity.Product;
import com.example.givy.domain.home.dto.res.HomeResDTO;
import com.example.givy.domain.recommendation.entity.Tendency;
import com.example.givy.domain.user.entity.Users;

public class HomeConverter {

    public static HomeResDTO.HomeResponseDTO toHomeResponseDTO(Users user, String status, Object data) {
        return HomeResDTO.HomeResponseDTO.builder()
                .nickname(user.getNickname())
                .status(status)
                .data(data)
                .build();
    }

    public static HomeResDTO.BeforeHomeDTO toBeforeHomeDTO(Tendency tendency, Product bestProduct) {
        String goal;
        goal = bestProduct.getTheme();

        return HomeResDTO.BeforeHomeDTO.builder()
                .goal(goal)
                .expectedAmount(50000)
                .characterImageUrl(tendency.getImageUrl())
                .build();
    }

    public static HomeResDTO.AfterHomeDTO toAfterHomeDTO(Users user, Product product, String securitiesAccount){
        HomeResDTO.HomeTicketDTO ticket = toHomeTicketDTO(product,securitiesAccount);
        HomeResDTO.HomeFrontCardDTO frontCard = toHomeFrontCardDTO(product);
        HomeResDTO.HomeBackCardDTO backCard = toHomeBackCardDTO(product);
        return HomeResDTO.AfterHomeDTO.builder()
                .ticket(ticket)
                .frontCard(frontCard)
                .backCard(backCard)
                .build();
    }

    public static HomeResDTO.HomeTicketDTO toHomeTicketDTO(Product product, String securitiesAccount){
        return HomeResDTO.HomeTicketDTO.builder()
                .goal(product.getTheme())
                .targetItem(product.getDestination())
                .productCode(product.getCode())
                .securitiesFirm(securitiesAccount)
                .amount(50000)
                .build();
    }

    public static HomeResDTO.HomeFrontCardDTO toHomeFrontCardDTO(Product product){
        return HomeResDTO.HomeFrontCardDTO.builder()
                .exchange(product.getExchange())
                .ticker(product.getTicker())
                .productName(product.getName())
                .averageRate(product.getRateAvg())
                .rateMax(product.getRateMax())
                .rateMin(product.getRateMin())
                .build();
    }

    public static HomeResDTO.HomeBackCardDTO toHomeBackCardDTO(Product product){
        return HomeResDTO.HomeBackCardDTO.builder()
                .descriptionTitle(product.getDescriptionTitle())
                .description(product.getDescription())
                .build();
    }


}
