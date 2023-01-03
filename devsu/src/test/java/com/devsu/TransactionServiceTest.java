package com.devsu;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import com.devsu.configuration.ApiException;
import com.devsu.entity.Account;
import com.devsu.repository.TransactionRepository;
import com.devsu.service.AccountService;
import com.devsu.service.TransactionService;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = { "devsu.diaryAmountLimit = 1000" })
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepo;

    @Mock
    private AccountService accountService;

    private Account account;

    @BeforeEach
    public void init() {
        account = new Account();
        account.setId(1L);
        account.setBalance(new BigDecimal(100));
        account.setNumber("5487");
        account.setType("CORRIENTE");
    }

    @Test
    public void test_createNewTransaction() throws ApiException {
        TransactionService transactionService = new TransactionService(transactionRepo, accountService);

        when(accountService.getAccountByNumber(any())).thenReturn(account);
        when(accountService.updateAccount(1L, account)).thenReturn(account);

        transactionService.createTransaction("5487", new BigDecimal(100));

        Assertions.assertEquals(200.00, account.getBalance().doubleValue());
    }

    @Test
    public void test_withdrawAnEmtpyAccount() throws ApiException {
        TransactionService transactionService = new TransactionService(transactionRepo, accountService);
        account.setBalance(new BigDecimal(0));
        
        

        when(accountService.getAccountByNumber(any())).thenReturn(account);

        ApiException exceptionThrown = Assertions.assertThrows(ApiException.class, () -> {
            transactionService.createTransaction("5487", new BigDecimal(-100));
        });

        Assertions.assertEquals("Insufficient funds.", exceptionThrown.getApiResult().getMessage());
    }
    
    @Test
    public void test_withdrawLimit() throws ApiException {
        TransactionService transactionService = new TransactionService(transactionRepo, accountService);
        account.setBalance(new BigDecimal(10000));
        
        when(accountService.getAccountByNumber(any())).thenReturn(account);
        when(transactionRepo.totalAmountByCurrentDay(any())).thenReturn(new BigDecimal(500));
        ReflectionTestUtils.setField(transactionService, "diaryAmountLimit", "1000");

        ApiException exceptionThrown = Assertions.assertThrows(ApiException.class, () -> {
            transactionService.createTransaction("5487", new BigDecimal(-600));
        });

        Assertions.assertEquals("Diary amount limit exceeded.", exceptionThrown.getApiResult().getMessage());
    }

}
