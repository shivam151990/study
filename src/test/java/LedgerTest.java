import com.shivam151990.lld.ledger.Account;
import com.shivam151990.lld.ledger.RecordService;
import com.shivam151990.lld.ledger.Transaction;
import com.shivam151990.lld.ledger.TransactionService;
import com.shivam151990.lld.ledger.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LedgerTest {

    private Account account1;
    private Account account2;
    private TransactionService transactionService;

    @BeforeEach
    public void before() {
        account1 = new Account("a1", 10000);
        account2 = new Account("a2", 5000);
        transactionService = new TransactionService(new RecordService());
        transactionService.addAccount(account1);
        transactionService.addAccount(account2);
    }

    // ---- 1. opening balance ----

    @Test
    public void accountsStartWithOpeningBalance() {
        assertEquals(10000, transactionService.getBalance("a1"));
        assertEquals(5000, transactionService.getBalance("a2"));
    }

    @Test
    public void duplicateAccountRejected() {
        assertThrows(IllegalArgumentException.class, () -> transactionService.addAccount(new Account("a1", 1)));
    }

    @Test
    public void balanceOfUnknownAccountFails() {
        assertThrows(IllegalArgumentException.class, () -> transactionService.getBalance("nope"));
    }

    // ---- 2. transfer ----

    @Test
    public void transferMovesMoney() {
        transactionService.transfer("a1", "a2", 3000);
        assertEquals(7000, transactionService.getBalance("a1"));
        assertEquals(8000, transactionService.getBalance("a2"));
    }

    @Test
    public void transferOfEntireBalanceAllowed() {
        transactionService.transfer("a2", "a1", 5000);
        assertEquals(0, transactionService.getBalance("a2"));
        assertEquals(15000, transactionService.getBalance("a1"));
    }

    @Test
    public void transferFailsWithInsufficientFunds() {
        assertThrows(IllegalArgumentException.class, () -> transactionService.transfer("a2", "a1", 5001));
        assertBalancesUnchanged();
    }

    @Test
    public void transferFailsForUnknownAccounts() {
        assertThrows(IllegalArgumentException.class, () -> transactionService.transfer("nope", "a1", 100));
        assertThrows(IllegalArgumentException.class, () -> transactionService.transfer("a1", "nope", 100));
        assertBalancesUnchanged();
    }

    @Test
    public void transferToSameAccountFails() {
        assertThrows(IllegalArgumentException.class, () -> transactionService.transfer("a1", "a1", 100));
        assertBalancesUnchanged();
    }

    @Test
    public void transferOfNonPositiveAmountFails() {
        assertThrows(IllegalArgumentException.class, () -> transactionService.transfer("a1", "a2", -500));
        assertThrows(IllegalArgumentException.class, () -> transactionService.transfer("a1", "a2", 0));
        assertBalancesUnchanged();
    }

    @Test
    public void failedTransferIsNotRecorded() {
        assertThrows(IllegalArgumentException.class, () -> transactionService.transfer("a2", "a1", 999999));
        assertTrue(statement("a1").isEmpty());
        assertTrue(statement("a2").isEmpty());
    }

    // ---- 3. recording ----

    @Test
    public void transferRecordedOnBothAccountsWithTimestamp() {
        LocalDateTime before = LocalDateTime.now();
        transactionService.transfer("a1", "a2", 3000);
        LocalDateTime after = LocalDateTime.now();

        Transaction debit = statement("a1").get(0);
        Transaction credit = statement("a2").get(0);
        assertEquals(Type.DEBIT, debit.getCredit());
        assertEquals(3000, debit.getAmount());
        assertEquals(Type.CREDIT, credit.getCredit());
        assertEquals(3000, credit.getAmount());
        assertTrue(!debit.getTxTime().isBefore(before) && !debit.getTxTime().isAfter(after));
        assertEquals(debit.getTxTime(), credit.getTxTime());
    }

    @Test
    public void statementCannotBeModified() {
        transactionService.transfer("a1", "a2", 3000);
        List<Transaction> statement = statement("a1");
        assertThrows(UnsupportedOperationException.class, () -> statement.remove(0));
        assertEquals(1, statement("a1").size());
    }

    // ---- 4. balance and statement ----

    @Test
    public void statementHasRunningBalance() {
        transactionService.transfer("a1", "a2", 1000);
        transactionService.transfer("a2", "a1", 500);
        transactionService.transfer("a1", "a2", 2000);

        List<Transaction> a1 = statement("a1");
        assertEquals(List.of(Type.DEBIT, Type.CREDIT, Type.DEBIT), a1.stream().map(Transaction::getCredit).toList());
        assertEquals(List.of(9000.0, 9500.0, 7500.0), a1.stream().map(Transaction::getBalance).toList());

        List<Transaction> a2 = statement("a2");
        assertEquals(List.of(6000.0, 5500.0, 7500.0), a2.stream().map(Transaction::getBalance).toList());
    }

    @Test
    public void statementOnlyIncludesDateRange() {
        transactionService.transfer("a1", "a2", 1000);
        LocalDateTime now = LocalDateTime.now();
        assertTrue(transactionService.getStatement("a1", now.minusDays(10), now.minusDays(1)).isEmpty());
        assertTrue(transactionService.getStatement("a1", now.plusDays(1), now.plusDays(10)).isEmpty());
    }

    @Test
    public void statementRangeIsInclusive() {
        transactionService.transfer("a1", "a2", 1000);
        LocalDateTime txTime = statement("a1").get(0).getTxTime();
        assertEquals(1, transactionService.getStatement("a1", txTime, txTime).size());
    }

    @Test
    public void statementForAccountWithoutTransactionsIsEmpty() {
        transactionService.addAccount(new Account("a3", 100));
        assertTrue(statement("a3").isEmpty());
    }

    @Test
    public void statementForUnknownAccountFails() {
        assertThrows(IllegalArgumentException.class, () -> statement("nope"));
    }

    private List<Transaction> statement(String acctId) {
        LocalDateTime now = LocalDateTime.now();
        return transactionService.getStatement(acctId, now.minusDays(1), now.plusDays(1));
    }

    private void assertBalancesUnchanged() {
        assertEquals(10000, transactionService.getBalance("a1"));
        assertEquals(5000, transactionService.getBalance("a2"));
    }
}
