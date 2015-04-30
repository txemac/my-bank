package com.abc;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

	private static final double DOUBLE_DELTA = 1e-15;
	
    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100,00\n" +
                "Total $100,00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4.000,00\n" +
                "  withdrawal $200,00\n" +
                "Total $3.800,00\n" +
                "\n" +
                "Total In All Accounts $3.900,00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testFourAccounts() {
    	Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer jose = new Customer("Jose").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        jose.transfer(savingsAccount, checkingAccount, 1000.0);

        assertEquals("Statement for Jose\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100,00\n" +
                "  deposit $1.000,00\n" +
                "Total $1.100,00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4.000,00\n" +
                "  withdrawal $1.000,00\n" +
                "Total $3.000,00\n" +
                "\n" +
                "Total In All Accounts $4.100,00", jose.getStatement());
    }
    
    @Test
    public void testFiveAccounts() {
    	Account maxiAccount = new Account(Account.MAXI_SAVINGS);

        Customer jose = new Customer("Jose").openAccount(maxiAccount);

        maxiAccount.deposit(4000.0);
        
        assertEquals(200.0, jose.totalInterestEarned(), DOUBLE_DELTA);
        
        maxiAccount.withdraw(2000.0);
        
        assertEquals(2.0, jose.totalInterestEarned(), DOUBLE_DELTA);
        
        // change day of transaction at 11 days ago
        Transaction t = maxiAccount.transactions.get(1);
        t.setTransactionDate(new Date(t.getTransactionDate().getTime() - (1000 * 60 * 60 * 24 * 11)));

        assertEquals(100.0, jose.totalInterestEarned(), DOUBLE_DELTA);
    }
    
    @Test
    public void testSixAccounts() {
        Account maxiAccount = new Account(Account.SAVINGS);

        Customer jose = new Customer("Jose").openAccount(maxiAccount);

        maxiAccount.deposit(1000.0);
        maxiAccount.transactions.get(0).setTransactionDate(DateProvider.getInstance().beforeDays(DateProvider.getInstance().now(), 5));
        
        jose.updateAccounts();
        
        assertEquals("Statement for Jose\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $1.000,00\n" +
                "  interests $1,00\n" +
                "  interests $1,00\n" +
                "  interests $1,00\n" +
                "  interests $1,01\n" +
                "Total $1.004,01\n" +
                "\n" +
                "Total In All Accounts $1.004,01", jose.getStatement());
    }
}
