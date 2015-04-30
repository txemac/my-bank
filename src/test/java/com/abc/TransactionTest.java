package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(Transaction.DEPOSIT, 5);
        assertTrue(t instanceof Transaction);
    }
}
