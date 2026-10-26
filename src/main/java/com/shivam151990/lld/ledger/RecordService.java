package com.shivam151990.lld.ledger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordService {

    // Private and append-only, so records are never edited or deleted
    private final Map<String, List<Transaction>> transactions = new HashMap<>();

    public void recordTransaction(String acct, Transaction t) {
        transactions.computeIfAbsent(acct, l -> new ArrayList<>());
        transactions.get(acct).add(t);
    }

    // from and to are inclusive
    public List<Transaction> search(String acct, LocalDateTime from, LocalDateTime to) {
        return transactions.getOrDefault(acct, List.of()).stream()
                .filter(tx -> !tx.getTxTime().isBefore(from) && !tx.getTxTime().isAfter(to))
                .toList();
    }
}
