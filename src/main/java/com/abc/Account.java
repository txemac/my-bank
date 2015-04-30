package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    // days without withdrawals
    public static final int MAXI_SAVINGS_DAYS = 10;

    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(Transaction.DEPOSIT, amount));
        }
    }

	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else {
	        transactions.add(new Transaction(Transaction.WITHDRAW, -amount));
	    }
	}
	
	public void interest(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(Transaction.INTEREST, amount));
        }
    }
	
	public void rates(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(Transaction.RATES, amount));
        }
    }

    public double interestEarned() {
//        double amount = sumTransactions();
//        switch(accountType){
//            case SAVINGS:
//                if (amount <= 1000)
//                    return amount * 0.001;
//                else
//                    return 1 + (amount-1000) * 0.002;
////            case SUPER_SAVINGS:
////                if (amount <= 4000)
////                    return 20;
//            case MAXI_SAVINGS:
//                if (amount <= 1000)
//                    return amount * 0.02;
//                if (amount <= 2000)
//                    return 20 + (amount-1000) * 0.05;
//                return 70 + (amount-2000) * 0.1;
//            default:
//                return amount * 0.001;
//        }
        return interestEarnedDate(DateProvider.getInstance().now());
    }
    
    /**
     * Calculate interest with amount before a date
     * @param date
     * @return double
     */
    public double interestEarnedDate(Date date) {
        double amount = sumTransactionsDate(date);
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
//            	if (amount <= 1000)
//                    return amount * 0.02;
//                if (amount <= 2000)
//                    return 20 + (amount-1000) * 0.05;
//                return 70 + (amount-2000) * 0.1;
              // task 2
              if (noWithdrawalsDays(MAXI_SAVINGS_DAYS))
                return amount * 0.05;
              else
              	return amount * 0.001;
            default:
                return amount * 0.001;
        }
    }
    
    /**
     * Return true if no withdrawals in the past days
     * @param days
     * @return boolean
     */
    private boolean noWithdrawalsDays(int days) {
    	boolean result = true;
    	
    	for (Transaction t: transactions)
    		// if t is a withdraw and date after 'Days' days ago
    		if ((t.getType() == Transaction.WITHDRAW) &&
    			(t.getTransactionDate().after(DateProvider.getInstance().beforeDays(DateProvider.getInstance().now(), days))))
            	result = false;
    	
    	return result;
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }
    
    /**
     * Calculate amount in this account before the a date
     * @param date
     * @return double
     */
    private double sumTransactionsDate(Date date) {
        double amount = 0.0;
        for (Transaction t: transactions)
            if (t.getTransactionDate().before(date)) amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }
    
    /**
     * Update interest.
     * Select date of last update and calculate per day
     */
    public void updateInterest() {
    	Date dateNow = DateProvider.getInstance().now();
    	
    	// calculate last update
    	Transaction lastInterest = getLastTransaction(Transaction.INTEREST);
    	
    	// if never calculate interests
    	if (lastInterest == null) lastInterest = getFirstTransaction();
    	
    	Date dateAux = lastInterest.getTransactionDate();
    	dateAux = DateProvider.getInstance().afterDays(dateAux, 1);
    	
    	while (dateNow.after(dateAux)) {
    		transactions.add(new Transaction(Transaction.INTEREST, interestEarnedDate(dateAux), dateAux));
    		dateAux = DateProvider.getInstance().afterDays(dateAux, 1);
    	}
    }
    
    /**
     * Update rates.
     * 
     */
    public void updateRates() {
    	Date dateNow = DateProvider.getInstance().now();
    	Transaction lastRate = getLastTransaction(Transaction.RATES);
    	
    	if (lastRate == null) lastRate = getFirstTransaction();
    	
    	Date dateAux = lastRate.getTransactionDate();
    	dateAux = DateProvider.getInstance().afterDays(dateAux, 365);
    	
    	while (dateNow.after(dateAux)) {
    		transactions.add(new Transaction(Transaction.INTEREST, interestEarnedDate(dateAux), dateAux));
    		dateAux = DateProvider.getInstance().afterDays(dateAux, 365);
    	}
    }
    
    /**
     * Return the last transaction
     * @param type - type of transaction
     * @return Transaction
     */
    public Transaction getLastTransaction(int type) {
    	Transaction result = null;
    	
    	for (Transaction transaction : transactions) {
			if ((transaction.getType() == type) &&
				((result == null) || (transaction.getTransactionDate().after(result.getTransactionDate()))))
				result = transaction;
		}
    	
    	return result;
    }
    
    /**
     * Get the first transaction
     * @return Transaction
     */
    public Transaction getFirstTransaction() {
    	Transaction result = null;
    	
    	for (Transaction transaction : transactions) {
			if ((result == null) || (transaction.getTransactionDate().before(result.getTransactionDate())))
				result = transaction;
		}
    	
    	return result;
    } 
}
