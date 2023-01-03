package com.devsu.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.devsu.configuration.ApiException;
import com.devsu.dto.ApiResult;
import com.devsu.entity.Account;
import com.devsu.entity.Transaction;
import com.devsu.repository.TransactionRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionService {

    private TransactionRepository transactionRepo;
    private AccountService accountService;

    @Value("${devsu.diaryAmountLimit}")
    private String diaryAmountLimit;

    @Autowired
    public TransactionService(TransactionRepository transactionRepo, AccountService accountService) {
        this.transactionRepo = transactionRepo;
        this.accountService = accountService;
    }

    public Transaction getTransactionById(Long id) throws ApiException {
        log.info("Retrieving transaction with id: {}", id);
        return transactionRepo.findById(id)
                .orElseThrow(() -> new ApiException(new ApiResult(String.valueOf(HttpStatus.NOT_FOUND.value()),
                        "Transaction doesn't exists.", LocalDateTime.now(), null)));
    }

    public List<Transaction> getAllTransactions() {
        log.info("Retrieving all transactions.");
        return transactionRepo.findAll();
    }

    public Transaction createTransaction(String accountNumber, BigDecimal amount) throws ApiException {
        log.info("Creating transaction...");
        Account account = accountService.getAccountByNumber(accountNumber);
        log.info("Account Number: {}. Current Balance: {}", account.getNumber(), account.getBalance());
        if (account.getBalance().add(amount).doubleValue() < 0) {
            log.error("Insufficient funds.");
            throw new ApiException(new ApiResult(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Insufficient funds.",
                    LocalDateTime.now(), null));
        }

        if (amount.doubleValue() < 0) {
            BigDecimal diaryAmount = transactionRepo.totalAmountByCurrentDay(account.getId());
            if (diaryAmount == null)
                diaryAmount = new BigDecimal(0d);
            log.info("Diary Amount: {}", diaryAmount.doubleValue());
            log.info("Diary Amount + amount: {}", diaryAmount.add(amount).doubleValue());
            log.info("Limit: {}", (new BigDecimal(diaryAmountLimit).doubleValue()));
            if (diaryAmount.add(amount.abs()).doubleValue() > (new BigDecimal(diaryAmountLimit).doubleValue())) {
                log.error("Diary amount limit exceeded.");
                throw new ApiException(new ApiResult(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                        "Diary amount limit exceeded.", LocalDateTime.now(), null));
            }
        }

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setDateTime(LocalDateTime.now());
        transaction.setInitialBalance(account.getBalance());
        transaction.setFinalBalance(account.getBalance().add(amount));

        log.info("Creating new transaction: {}", transaction);

        account.setBalance(transaction.getFinalBalance());
        accountService.updateAccount(account.getId(), account);

        log.info("Updating Account Balance: {}", account);

        return transactionRepo.save(transaction);
    }

    // In theory, we shouldn't have update/delete transactions.
    // --------------------------------------------------------

    public List<Transaction> getTransactions(String accountNumber, LocalDateTime dateFrom, LocalDateTime dateTo)
            throws ApiException {
        Account account = accountService.getAccountByNumber(accountNumber);
        log.info("Retrieving all transactions for account number {}", account.getNumber());
        List<Transaction> transactionList = transactionRepo
                .findByAccountAndDateTimeGreaterThanEqualAndDateTimeLessThanEqual(account, dateFrom, dateTo);
        log.info("{} transactions found for account number {}", transactionList.size(), account.getNumber());
        return transactionList;
    }

    public List<Transaction> getTransactions(Account account) throws ApiException {
        List<Transaction> transactionList = transactionRepo.findByAccount(account);
        log.info("{} transactions found for account number {}", transactionList.size(), account.getNumber());
        return transactionList;
    }

}
