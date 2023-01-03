package com.devsu.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.devsu.configuration.ApiException;
import com.devsu.dto.ApiResult;
import com.devsu.entity.Account;
import com.devsu.entity.Client;
import com.devsu.repository.AccountRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountService {

    private AccountRepository accountRepo;
    private ClientService clientService;

    @Autowired
    public AccountService(AccountRepository accountRepo, ClientService clientService) {
        this.accountRepo = accountRepo;
        this.clientService = clientService;
    }

    public Account getAccountById(Long id) throws ApiException {
        log.info("Retrieving account with id: {}", id);
        return accountRepo.findById(id)
                .orElseThrow(() -> new ApiException(new ApiResult(String.valueOf(HttpStatus.NOT_FOUND.value()),
                        "Account doesn't exists.", LocalDateTime.now(), null)));
    }

    public List<Account> getAllAccounts() {
        log.info("Retrieving all accounts.");
        return accountRepo.findAll();
    }

    public Account createAccount(Account account) {
        log.info("Creating account. Account number: {}. Client Name: {}.", account.getNumber(),
                account.getClient().getName());
        return accountRepo.save(account);
    }

    public Account updateAccount(Long id, Account account) throws ApiException {
        log.info("Updating account. ID: {}. Account number: {}. Client Name: {}.", account.getId(), account.getNumber(),
                account.getClient().getName());
        if (!accountRepo.existsById(id)) {
            log.error("Account with id {} doesn't exists.", id);
            throw new ApiException(new ApiResult(String.valueOf(HttpStatus.NOT_FOUND.value()),
                    "Account doesn't exists.", LocalDateTime.now(), null));
        }

        account.setId(id);
        return accountRepo.save(account);
    }

    public void deleteAccount(Long id) throws ApiException {
        if (!accountRepo.existsById(id)) {
            log.error("Account with id {} doesn't exists.", id);
            throw new ApiException(new ApiResult(String.valueOf(HttpStatus.NOT_FOUND.value()),
                    "Account doesn't exists.", LocalDateTime.now(), null));
        }
        log.info("Deleting account with ID: {}.", id);
        accountRepo.deleteById(id);
    }

    public Account getAccountByNumber(String number) throws ApiException {
        log.info("Retrieving account with number: {}", number);
        return accountRepo.findByNumber(number)
                .orElseThrow(() -> new ApiException(new ApiResult(String.valueOf(HttpStatus.NOT_FOUND.value()),
                        "Account number doesn't exists.", LocalDateTime.now(), null)));
    }

    public List<Account> getAccountsByPersonalId(String personalId) throws ApiException {
        log.info("Retrieving account with client personal id: {}", personalId);
        Client client = clientService.getClientByPersonalId(personalId);
        return getAccountsByClient(client);
    }

    public List<Account> getAccountsByClient(Client client) {
        return accountRepo.findByClient(client);
    }

}
