package com.shivam151990.lld.ledger;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionService {

    private Map<String, Account> accounts;
    private RecordService service;

    public TransactionService(RecordService service) {
        this.accounts = new HashMap<>();
        this.service = service;
    }

    public void addAccount(Account account) {
        if (accounts.containsKey(account.getAcctId())) {
            throw new IllegalArgumentException(account.getAcctId() + " account already exists");
        }
        accounts.put(account.getAcctId(), account);
    }

    public void transfer(String from, String to, int amt) {
        if (amt <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        validateAccount(from, to);
        validateBalance(from, amt);
        Account fromAcct = accounts.get(from);
        Account toAcct = accounts.get(to);
        LocalDateTime now = LocalDateTime.now();
        if (fromAcct.debitAmount(amt)) {
            service.recordTransaction(from, new Transaction(amt, Type.DEBIT, now, fromAcct.getBalance()));
        }
        if (toAcct.creditAmount(amt)) {
            service.recordTransaction(to, new Transaction(amt, Type.CREDIT, now, toAcct.getBalance()));
        }
    }

    public double getBalance(String acctId) {
        if (!accounts.containsKey(acctId)) {
            throw new IllegalArgumentException(acctId + " account does not exist");
        }
        return accounts.get(acctId).getBalance();
    }

    // Each transaction's balance is the running balance right after it
    public List<Transaction> getStatement(String acctId, LocalDateTime from, LocalDateTime to) {
        if (!accounts.containsKey(acctId)) {
            throw new IllegalArgumentException(acctId + " account does not exist");
        }
        return service.search(acctId, from, to);
    }

    private void validateAccount(String from, String to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("From and to cannot be same");
        }
        if (!accounts.containsKey(from)) {
            throw new IllegalArgumentException(from + " account does not exist");
        }
        if (!accounts.containsKey(to)) {
            throw new IllegalArgumentException(to + " account does not exist");
        }
    }

    private void validateBalance(String from, int amt) {
        if (!accounts.containsKey(from) || accounts.get(from).getBalance() < amt) {
            throw new IllegalArgumentException(from + " account does not have sufficient balance");
        }
    }
}
