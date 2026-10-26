package com.shivam151990.lld.ledger;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Random;

@Getter
public class Transaction {

    private Random random;

    private int id;
    private int amount;
    private Type credit;
    private LocalDateTime txTime;
    private double balance;

    public Transaction(int amount, Type credit, LocalDateTime txTime, double balance) {
        random = new Random();
        this.id = random.nextInt(100000000);
        this.amount = amount;
        this.credit = credit;
        this.txTime = txTime;
        this.balance = balance;
    }
}
