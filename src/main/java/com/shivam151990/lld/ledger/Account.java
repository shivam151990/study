package com.shivam151990.lld.ledger;

import lombok.Getter;

@Getter
public class Account {
    private String acctId;
    private double balance;

    public Account(String acctId, double balance) {
        this.acctId = acctId;
        this.balance = balance;
    }

    public boolean creditAmount(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot be negative");
        }
        balance += amount;
        return true;
    }

    public boolean debitAmount(int amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient Balance");
        }
        balance -= amount;
        return true;
    }

}
