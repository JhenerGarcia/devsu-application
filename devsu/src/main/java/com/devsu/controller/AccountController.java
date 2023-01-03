package com.devsu.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.configuration.ApiException;
import com.devsu.dto.ApiData;
import com.devsu.dto.ApiResult;
import com.devsu.entity.Account;
import com.devsu.service.AccountService;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult> getById(@PathVariable(name = "id") Long id) throws ApiException {
        Account account = accountService.getAccountById(id);
        return ResponseEntity.ok(new ApiResult(String.valueOf(HttpStatus.OK.value()), "Success", LocalDateTime.now(),
                new ApiData("account", account)));
    }

    @GetMapping()
    public ResponseEntity<ApiResult> getAll() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(new ApiResult(String.valueOf(HttpStatus.OK.value()), "Success", LocalDateTime.now(),
                new ApiData("accounts", accounts)));
    }

    @GetMapping("/by-number/{number}")
    public ResponseEntity<ApiResult> getByNumber(@PathVariable(name = "number", required = true) String number)
            throws ApiException {
        return ResponseEntity.ok(new ApiResult(String.valueOf(HttpStatus.OK.value()), "Success", LocalDateTime.now(),
                new ApiData("account", accountService.getAccountByNumber(number))));
    }

    @GetMapping("/by-personal-id/{personalId}")
    public ResponseEntity<ApiResult> getAccountsByPersonalId(
            @PathVariable(name = "personalId", required = true) String personalId) throws ApiException {
        return ResponseEntity.ok(new ApiResult(String.valueOf(HttpStatus.OK.value()), "Success", LocalDateTime.now(),
                new ApiData("accounts", accountService.getAccountsByPersonalId(personalId))));
    }

    @PostMapping()
    public ResponseEntity<ApiResult> createAccount(@RequestBody Account account) {
        Account newAccount = accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResult(String.valueOf(HttpStatus.OK.value()), "Success",
                LocalDateTime.now(), new ApiData("account", newAccount)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult> updateAccount(@PathVariable Long id, @RequestBody Account account)
            throws ApiException {
        accountService.updateAccount(id, account);
        return ResponseEntity.ok(new ApiResult(String.valueOf(HttpStatus.NO_CONTENT.value()), "Success", LocalDateTime.now(), null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult> deleteAccount(@PathVariable Long id) throws ApiException {
        accountService.deleteAccount(id);
        return ResponseEntity.ok(new ApiResult(String.valueOf(HttpStatus.NO_CONTENT.value()), "Success", LocalDateTime.now(), null));
    }

}
