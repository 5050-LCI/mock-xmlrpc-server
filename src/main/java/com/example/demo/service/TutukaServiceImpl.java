package com.example.demo.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.MockCard;

//@Service
//public class TutukaServiceImpl implements TutukaService {
//	
//	@Autowired 
//	MockCardStore mockCardStore;
//
//	@Override
//	public Map<String, Object> loadCardDeductProfile(String terminalId, String profileNumber, String cardIdentifier,
//			Integer amount, String hashKey, Date date, String checksum) {
//		System.out.println("inside mock server for loadCardDeductProfile()");
//		 Map<String, Object> response = new HashMap<>();
//
//	        response.put("resultCode", 1);
//	        response.put("resultText", "SUCCESS");
//
//	        response.put("profileNumber", profileNumber);
//	        MockCard card = new MockCard();
//	        card.setBalance(card.getBalance()-amount);
//	        
//	        if(card.getBalance()<amount){
//
//	            response.put("resultCode",0);
//	            response.put("resultText","INSUFFICIENT_FUNDS");
//
//	            return response;
//	        }
//
//	        response.put("balanceAmount", card.getBalance());
//	        
//	        SimpleDateFormat formatter =
//	                new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
//
//	        String expiryDate = formatter.format(
//	                new GregorianCalendar(2028, Calendar.DECEMBER, 31).getTime());
//
//	        response.put("expiryDate", expiryDate);
//
//	        return response;
//	}
//	
//	@Override
//	public Map<String, Object> balance(
//	        String terminalId,
//	        String profileNumber,
//	        String cardNumber,
//	        String transactionId,
//	        Date date,
//	        String checksum) {
//
//	    System.out.println("Mock Balance API Called");
//
//	    Map<String, Object> response = new HashMap<>();
//
//	    response.put("resultCode", 1);
//	    response.put("resultText", "SUCCESS");
//
//	    response.put("profileNumber", profileNumber);
//
//	    response.put("serverTransactionID", transactionId);
//
//	    response.put("balanceAmount", 125000);
//
//	    response.put("authNumber", "1234");
//
//	    response.put("expiryDate",
//	            "Sun Dec 31 12:00:00 IST 2028");
//
//	    return response;
//	}
//	
//	@Override
//	public Map<String, Object> linkCard(
//	        String terminalId,
//	        String profileNumber,
//	        String trackingNumber,
//	        String transactionId,
//	        Date date,
//	        String checksum) {
//
//	    System.out.println("Mock LinkCard called");
//
//	    Map<String, Object> response = new HashMap<>();
//
//	    response.put("resultCode", 1);
//	    response.put("resultText", "SUCCESS");
//	    response.put("profileNumber", profileNumber);
//	    response.put("serverTransactionID", transactionId);
//
//	    response.put("cardNumber", "1234567890123456");
//	    response.put("cardSequenceNumber", "112211");
//
//	    return response;
//	}
//	
//	@Override
//	public Map<String, Object> allocateCard(
//	        String terminalId,
//	        String profileNumber,
//	        String trackingNumber,
//	        String firstName,
//	        String lastName,
//	        String idNumber,
//	        String mobileNumber,
//	        String transactionId,
//	        Date date,
//	        String checksum) {
//
//	    System.out.println("Mock AllocateCard called");
//
//	    Map<String, Object> response = new HashMap<>();
//
//	    response.put("resultCode", 1);
//	    response.put("resultText", "SUCCESS");
//	    response.put("profileNumber", profileNumber);
//	    response.put("serverTransactionID", transactionId);
//	    response.put("cardNumber", "1234567890123456");
//
//	    return response;
//	}
//	 
//}


import com.example.demo.model.MockTransaction;

@Service
public class TutukaServiceImpl {
	
	private static final Logger log =
            LoggerFactory.getLogger(TutukaServiceImpl.class);

//    @Autowired
//    private MockCardStore store;

    @Autowired
    private TransactionGenerator generator;

    public Map<String, Object> linkCard(
            String terminalId,
            String profileNumber,
            String trackingNumber,
            String transactionId,
            Date date,
            String checksum) {

    	Map<String, Object> response = new HashMap<>();

        try {

            log.info("LinkCard Request. Tracking Number : {}", trackingNumber);

            MockCard card = MockCardStore.getInstance().getOrCreate(trackingNumber, profileNumber);

            response.put("resultCode", 1);
            response.put("resultText", "SUCCESS");
            response.put("profileNumber", card.getProfileNumber());
            response.put("serverTransactionID", transactionId);
            response.put("cardNumber", card.getCardNumber());
            response.put("cardSequenceNumber", "001");

            log.info("LinkCard Success. Card : {}", card.getCardNumber());

        } catch (Exception e) {

            log.error("LinkCard Failed", e);

            response.clear();
            response.put("resultCode", 0);
            response.put("resultText", "FAILED");
        }

        return response;
    }

    public Map<String, Object> allocateCard(
            String terminalId,
            String profileNumber,
            String trackingNumber,
            String firstName,
            String lastName,
            String idNumber,
            String mobileNumber,
            String transactionId,
            Date date,
            String checksum) {

        Map<String, Object> response = new HashMap<>();

        try {

            log.info("AllocateCard Request : {}", trackingNumber);

            MockCard card = MockCardStore.getInstance().getOrCreate(trackingNumber, profileNumber);

            response.put("resultCode", 1);
            response.put("resultText", "SUCCESS");
            response.put("profileNumber", card.getProfileNumber());
            response.put("serverTransactionID", transactionId);
            response.put("cardNumber", card.getCardNumber());
            
            log.info("AllocateCard Success : {}", card.getCardNumber());

            return response;

        } catch (Exception e) {

            log.error("AllocateCard Failed", e);

            response.clear();
            response.put("resultCode",0);
            response.put("resultText","FAILED");
        }
		return response;
    }

    public Map<String, Object> balance(
            String terminalId,
            String profileNumber,
            String cardNumber,
            String transactionId,
            Date date,
            String checksum) {

        Map<String, Object> response = new HashMap<>();        
        
        try {

            log.info("Balance Request Card : {}", cardNumber);

            MockCard card = findCard(cardNumber);

            if(card == null){

                log.warn("Balance Failed. Card not found.");

                response.put("resultCode",0);
                response.put("resultText","CARD_NOT_FOUND");

                return response;
            }

            response.put("resultCode", 1);
            response.put("resultText", "SUCCESS");
            response.put("profileNumber", card.getProfileNumber());
            response.put("serverTransactionID", transactionId);
            response.put("balanceAmount", card.getBalance());
            response.put("authNumber", "123456");
            response.put("expiryDate", card.getExpiryDate());

            log.info("Balance Success. Balance : {}", card.getBalance());

            return response;

        }
        catch(Exception e){

            log.error("Balance Failed",e);

            response.clear();
            response.put("resultCode",0);
            response.put("resultText","FAILED");
        }
		return response;
    }

    public Map<String, Object> loadCardDeductProfile(
            String terminalId,
            String profileNumber,
            String cardIdentifier,
            Integer amount,
            String hashKey,
            Date date,
            String checksum) {

        Map<String, Object> response = new HashMap<>();
        
        try {
        	
        	log.info("Deduct Request Profile : {} Amount : {}",profileNumber,amount);
        	
        	MockCard card = findCardByProfile(profileNumber);
        	
        	log.warn("Profile not found.");
            
            if(card == null) {
            	response.put("resultCode", 0);
                response.put("resultText", "CARD_NOT_FOUND");

                return response;
            }

            if (card.getBalance() < amount) {
            	
            	log.warn("Insufficient Balance : {}",card.getBalance());

                response.put("resultCode", 0);
                response.put("resultText", "INSUFFICIENT_FUNDS");

                return response;
            }

            card.setBalance(card.getBalance() - amount);

            response.put("resultCode", 1);
            response.put("resultText", "SUCCESS");
            response.put("profileNumber", card.getProfileNumber());
            response.put("balanceAmount", card.getBalance());
            response.put("expiryDate", card.getExpiryDate());
            
            log.info("Deduct Success. Remaining Balance : {}",card.getBalance());
            
            return response;
        	
        } catch(Exception e){

            log.error("Deduct Failed",e);

            response.clear();
            response.put("resultCode",0);
            response.put("resultText","FAILED");
        }

        

        return response;
    }

    public Map<String, Object> statementByDateRange(
            String terminalId,
            String profileNumber,
            String trackingNumber,
            Date fromDate,
            Date toDate,
            String transactionId,
            Date date,
            String checksum) {
        
        Map<String, Object> response = new HashMap<>();

        try {

            log.info("Statement Request. Tracking : {}",trackingNumber);

            MockCard card = MockCardStore.getInstance().getOrCreate(trackingNumber, profileNumber);

            List<MockTransaction> transactions;

            if (card.getTransactions().isEmpty()) {

                transactions = generator.generate(card);

            } else {

                transactions = card.getTransactions();

            }

            Object[] statement = new Object[transactions.size()];

            for (int i = 0; i < transactions.size(); i++) {

                MockTransaction tx = transactions.get(i);

                Map<String, Object> txn = new HashMap<>();

                txn.put("transactionAmount",
                        (int) (tx.getTransactionAmount() * 100));

                txn.put("transactionDate",
                        tx.getTransactionDate());

                txn.put("transactionDescription",
                        tx.getTransactionDescription());

                txn.put("transactionType",
                        tx.getTransactionType());

                statement[i] = txn;

            }

            response.put("resultCode", 1);
            response.put("resultText", "SUCCESS");
            response.put("statement", statement);

            log.info("Generated {} transactions.",transactions.size());
            
            return response;

        }
        catch(Exception e){

            log.error("Statement Failed",e);

            response.clear();
            response.put("resultCode",0);
            response.put("resultText","FAILED");
        }

        return response;
    }
    
    //getStatus
    public Map<String, Object> status(
            String terminalId,
            String profileNumber,
            String cardIdentifier,
            String hashKey,
            Date date,
            String checksum) {
    	
        Map<String, Object> response = new HashMap<>();

        
        try{

            log.info("Status Request Profile : {}",profileNumber);

            MockCard card=findCardByProfile(profileNumber);

            if(card==null){

                log.warn("Card not found.");

                response.put("resultCode",0);
                response.put("resultText","CARD_NOT_FOUND");

                return response;
            }

            response.put("resultCode", 1);
            response.put("resultText", "SUCCESS");
            response.put("serverTransactionID", UUID.randomUUID().toString());

            // Card Status
            response.put("stopped", false);
            response.put("stolen", false);
            response.put("expired", false);
            response.put("lost", false);
            response.put("retired", false);
            response.put("cancelled", false);
            
            log.info("Status Success.");

            return response;
            
        }
        catch(Exception e){

            log.error("Status Failed",e);

            response.clear();
            response.put("resultCode",0);
            response.put("resultText","FAILED");
        }

        return response;
    }

    private MockCard findCard(String cardNumber) {

        for (MockCard card : MockCardStore.getInstance().getCards().values()) {

            if (card.getCardNumber().equals(cardNumber)) {
                return card;
            }

        }
        
        log.warn("Card not found : {}",cardNumber);

        return null;
    }

    private MockCard findCardByProfile(String profileNumber) {

        for (MockCard card : MockCardStore.getInstance().getCards().values()) {

            if (card.getProfileNumber().equals(profileNumber)) {
                return card;
            }

        }
        
        log.warn("Profile not found : {}",profileNumber);
        
        return null;
    }
}
