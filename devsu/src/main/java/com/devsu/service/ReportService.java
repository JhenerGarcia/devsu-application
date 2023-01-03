package com.devsu.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsu.configuration.ApiException;
import com.devsu.dto.ClientReport;
import com.devsu.dto.Report;
import com.devsu.dto.TransactionReport;
import com.devsu.entity.Account;
import com.devsu.entity.Transaction;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReportService {

    private AccountService accountService;
    private TransactionService transactionService;

    @Autowired
    public ReportService(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    public Report generateReport(String accountNumber, LocalDateTime dateFrom, LocalDateTime dateTo)
            throws ApiException {
        log.info("Generating report...");
        Account account = accountService.getAccountByNumber(accountNumber);
        log.info("Account -> Number: {}. Current Balance: {}. State: {}", account.getNumber(), account.getBalance(),
                account.isState());
        List<Transaction> transactionList = transactionService.getTransactions(accountNumber, dateFrom, dateTo);
        log.info("Total transactions: {}", transactionList.size());
        Report report = new Report();
        report.setClient(new ClientReport(account.getClient().getName(), account.getNumber(), account.getType()));
        report.setCurrentBalance(account.getBalance());
        transactionList.forEach(transaction -> {
            log.info("Transaction -> Date: {}. Initial Balance: {}. Amount: {}. Final Balance: {}",
                    transaction.getDateTime(), transaction.getInitialBalance(), transaction.getAmount(),
                    transaction.getFinalBalance());
            report.getTransactions()
                    .add(new TransactionReport(transaction.getDateTime(), account.isState(),
                            transaction.getInitialBalance().doubleValue(), transaction.getAmount().doubleValue(),
                            transaction.getFinalBalance().doubleValue()));
            if (transaction.getAmount().doubleValue() > 0)
                report.setTotalDeposits(report.getTotalDeposits().add(transaction.getAmount()));
            if (transaction.getAmount().doubleValue() < 0)
                report.setTotalWithdraws(report.getTotalWithdraws().add(transaction.getAmount()));
        });

        return report;
    }

}
