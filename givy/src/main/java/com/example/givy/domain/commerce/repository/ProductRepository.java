package com.example.givy.domain.commerce.repository;

import com.example.givy.domain.commerce.entity.Product;
import com.example.givy.domain.recommendation.enums.InvestmentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByRecommendationType(InvestmentType investmentType);
}
