package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.MockCard;

@Repository
public interface MockCardRepository extends JpaRepository<MockCard,Long>{

    Optional<MockCard> findByTrackingNumber(String trackingNumber);

    Optional<MockCard> findByCardNumber(String cardNumber);

    Optional<MockCard> findByProfileNumber(String profileNumber);

}
