package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.MockCard;
import com.example.demo.model.MockTransaction;

@Repository
public interface MockTransactionRepository extends JpaRepository<MockTransaction,Long>{

    List<MockTransaction> findByCard(MockCard card);

}