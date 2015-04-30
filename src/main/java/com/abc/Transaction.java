package com.abc;

import java.util.Date;

public class Transaction {
	
    public final double amount;
    
    public static final int DEPOSIT = 0;
    public static final int WITHDRAW = 1;
    public static final int INTEREST = 2;
    public static final int RATES = 3;

    private Date transactionDate;
    private int type;

    public Transaction(int type, double amount) {
    	this.type = type;
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        
        // add a pause for problems with Date.now()
        try {
            Thread.sleep(100);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    
    public Transaction(int type, double amount, Date date) {
    	this.type = type;
        this.amount = amount;
    	this.transactionDate = date;
    	
    	// add a pause for problems with Date.now()
        try {
            Thread.sleep(100);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    
    public Date getTransactionDate() {
		return transactionDate;
	}

	public double getAmount() {
		return amount;
	}
	
	public int getType() {
		return type;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	public String getTypeText() {
		
		switch(type){
        	case DEPOSIT:
        		return "deposit";
        	case WITHDRAW:
        		return "withdrawal";
        	case INTEREST:
        		return "interests";
        	case RATES:
        		return "rates";
        	default:
        		return "other";
		}
	}
}
