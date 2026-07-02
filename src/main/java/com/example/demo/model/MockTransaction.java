package com.example.demo.model;

import java.util.Date;

import lombok.Data;

@Data
public class MockTransaction {

    private Date transactionDate;

    private double transactionAmount;

    private int transactionType;

    private String transactionDescription;

}