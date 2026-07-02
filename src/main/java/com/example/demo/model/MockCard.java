package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MockCard {

    private String trackingNumber;

    private String cardNumber;

    private String profileNumber;

    private int balance; // cents

    private String expiryDate;

    private List<MockTransaction> transactions = new ArrayList<>();
}