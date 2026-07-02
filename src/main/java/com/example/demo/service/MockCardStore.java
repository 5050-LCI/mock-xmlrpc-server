package com.example.demo.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.example.demo.model.MockCard;

@Service
public class MockCardStore {
	
	private static final MockCardStore INSTANCE = new MockCardStore();

    public static MockCardStore getInstance() {
        return INSTANCE;
    }

    private final Map<String, MockCard> cards = new ConcurrentHashMap<>();

    public MockCard getOrCreate(String trackingNumber, String profileNumber) {
        return cards.computeIfAbsent(trackingNumber, t -> {

            MockCard c = new MockCard();

            c.setTrackingNumber(t);

            c.setCardNumber(RandomCardGenerator.generateCardNumber());

            c.setProfileNumber(profileNumber);

            c.setBalance(RandomCardGenerator.randomBalance());

//            c.setBalance(100000); // cents = R1000.00

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, 4);

            c.setExpiryDate(
                    new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
                            .format(cal.getTime()));

            return c;
        });
    }
    
//    private String generateCardNumber() {
//
//        Random r = new Random();
//
//        StringBuilder sb = new StringBuilder("5"); // Mastercard
//
//        while(sb.length()<16){
//            sb.append(r.nextInt(10));
//        }
//
//        return sb.toString();
//    }
//    
//    private String generateProfileNumber(){
//
//        return String.valueOf(
//                100000 + new Random().nextInt(900000)
//        );
//
//    }
    
    public Map<String, MockCard> getCards() {
        return cards;
    }
    
}
