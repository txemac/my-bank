package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
        switch(a.getAccountType()){
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            //s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
        	s += "  " + t.getTypeText() + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    
    /**
     * Transfer money account from a to b 
     * @param a - account from
     * @param b - account to
     * @param amount - money
     */
    public void transfer(Account a, Account b, double amount) {
    	if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
    	} else if (a.sumTransactions() < amount) {
    		throw new IllegalArgumentException("money insufficient in origin account");    
	    } else {
	    	a.withdraw(amount);
	    	b.deposit(amount);
	        //transactions.add(new Transaction(-amount));
	    }
    }
    
    /**
     * Update accounts, interest and rates
     */
    public void updateAccounts() {
    	for (Account account : accounts) {
			account.updateInterest();
			account.updateRates();
		}
    }
}
