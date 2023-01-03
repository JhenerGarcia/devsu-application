package com.devsu.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.configuration.ApiException;
import com.devsu.dto.ApiData;
import com.devsu.dto.ApiResult;
import com.devsu.entity.Account;
import com.devsu.entity.Client;
import com.devsu.entity.Transaction;
import com.devsu.service.AccountService;
import com.devsu.service.ClientService;
import com.devsu.service.TransactionService;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {

    private TransactionService transactionService;
    private AccountService accountService;
    private ClientService clientService;

    @Autowired
    public TransactionController(TransactionService transactionService, AccountService accountService,
            ClientService clientService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult> getById(@PathVariable(name = "id") Long id) throws ApiException {
        Transaction transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(new ApiResult(String.valueOf(HttpStatus.OK.value()), "Success", LocalDateTime.now(),
                new ApiData("transaction", transaction)));
    }

    @GetMapping()
    public ResponseEntity<ApiResult> getAll() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(new ApiResult(String.valueOf(HttpStatus.OK.value()), "Success", LocalDateTime.now(),
                new ApiData("transactions", transactions)));
    }

    @GetMapping("/by-account/{number}")
    public ResponseEntity<ApiResult> getByAccountNumber(@PathVariable(name = "number") String number)
            throws ApiException {
        Account account = accountService.getAccountByNumber(number);
        return ResponseEntity.ok(new ApiResult(String.valueOf(HttpStatus.OK.value()), "Success", LocalDateTime.now(),
                new ApiData("transactions", transactionService.getTransactions(account))));
    }

    @GetMapping("/by-personal-id/{personalId}")
    public ResponseEntity<ApiResult> getByPersonalId(@PathVariable(name = "personalId") String personalId)
            throws ApiException {
        Client client = clientService.getClientByPersonalId(personalId);
        List<Account> accountList = accountService.getAccountsByClient(client);
        List<Transaction> transactionList = new ArrayList<Transaction>();
        for (Account account : accountList) {
            transactionList.addAll(transactionService.getTransactions(account));
        }

        return ResponseEntity.ok(new ApiResult(String.valueOf(HttpStatus.OK.value()), "Success", LocalDateTime.now(),
                new ApiData("transactions", transactionList)));
    }

    @PostMapping()
    public ResponseEntity<ApiResult> createTransaction(
            @RequestParam(name = "accountNumber", required = true) String accountNumber,
            @RequestParam(name = "amount", required = true) BigDecimal amount) throws ApiException {
        Transaction newTransaction = transactionService.createTransaction(accountNumber, amount);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResult(HttpStatus.OK.toString(), "Success",
                LocalDateTime.now(), new ApiData("transaction", newTransaction)));
    }

}
