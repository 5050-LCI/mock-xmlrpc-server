package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.example.demo.model.MockCard;
import com.example.demo.model.MockTransaction;

@Component
public class TransactionGenerator {

    private final Random random = new Random();

    private static final String[] MERCHANTS = {

            "Checkers",
            "Pick n Pay",
            "Shell",
            "Engen",
            "KFC",
            "McDonalds",
            "Uber",
            "Takealot",
            "Clicks",
            "Dischem",
            "Amazon",
            "Netflix",
            "Spotify",
            "Salary",
            "Refund"

    };

    public List<MockTransaction> generate(MockCard card) {

        List<MockTransaction> list = new ArrayList<>();

        int total = 3 + random.nextInt(8);

        for (int i = 0; i < total; i++) {

            MockTransaction tx = new MockTransaction();

            tx.setTransactionDate(
                    new Date(System.currentTimeMillis()
                            - random.nextInt(24 * 60 * 60 * 1000)));

            boolean credit;

            /*
             * Never allow negative balance
             */

            if (card.getBalance() < 10000) {

                credit = true;

            } else {

                credit = random.nextBoolean();

            }

            int amount = (50 + random.nextInt(500)) * 100;

            if (credit) {

                card.setBalance(card.getBalance() + amount);

                tx.setTransactionType(1);

                tx.setTransactionDescription("Credit - "
                        + MERCHANTS[random.nextInt(MERCHANTS.length)]);

            } else {

                if (card.getBalance() < amount) {

                    amount = card.getBalance();

                }

                card.setBalance(card.getBalance() - amount);

                tx.setTransactionType(2);

                tx.setTransactionDescription("Purchase - "
                        + MERCHANTS[random.nextInt(MERCHANTS.length)]);

            }

            tx.setTransactionAmount(amount / 100.0);
            
            // This sets mock_transaction.card_id
            tx.setCard(card);

            list.add(tx);

        }

//        card.getTransactions().addAll(list);

        return list;

    }

}
