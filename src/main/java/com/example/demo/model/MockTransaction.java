package com.example.demo.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

//@Data
//public class MockTransaction {
//
//    private Date transactionDate;
//
//    private double transactionAmount;
//
//    private int transactionType;
//
//    private String transactionDescription;
//
//}


@Entity
@Table(name="mock_transaction")
@Data
public class MockTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="transaction_date")
    private Date transactionDate;

    @Column(name="transaction_amount")
    private Double transactionAmount;

    @Column(name="transaction_type")
    private Integer transactionType;

    @Column(name="transaction_description")
    private String transactionDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="card_id",nullable=false)
    private MockCard card;
}