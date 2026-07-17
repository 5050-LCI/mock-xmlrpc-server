package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Data
//@Getter
//@Setter
//@Entity
////@Table(name="mock_card")
//public class MockCard {
//	
//	@Id
////	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private String mockCardId;
//
//    private String trackingNumber;
//
//    private String cardNumber;
//
//    private String profileNumber;
//
//    private int balance; // cents
//
//    private String expiryDate;
//
//    private List<MockTransaction> transactions = new ArrayList<>();
//}

@Entity
@Table(name = "mock_card")
@Data
public class MockCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="tracking_number",nullable=false,unique=true)
    private String trackingNumber;

    @Column(name="card_number",nullable=false,unique=true)
    private String cardNumber;

    @Column(name="profile_number",nullable=false)
    private String profileNumber;

    @Column(name="balance",nullable=false)
    private Integer balance;

    @Column(name="expiry_date")
    private String expiryDate;

    @OneToMany(
            mappedBy="card",
            cascade=CascadeType.ALL,
            fetch=FetchType.LAZY)
    @JsonManagedReference
    private List<MockTransaction> transactions = new ArrayList<>();
}