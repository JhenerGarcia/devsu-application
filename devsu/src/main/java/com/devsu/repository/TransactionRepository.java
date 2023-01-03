package com.devsu.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devsu.entity.Account;
import com.devsu.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    public List<Transaction> findByAccount(Account account);

    public List<Transaction> findByAccountAndDateTimeGreaterThanEqualAndDateTimeLessThanEqual(Account account,
            LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT ABS(SUM(AMOUNT)) FROM TRANSACTIONS WHERE ACCOUNT_ID = ? AND AMOUNT<0 AND DATE_TIME >= DATE_TRUNC(DAY, CURRENT_TIMESTAMP()) AND DATE_TIME <  (DATE_TRUNC(DAY, CURRENT_TIMESTAMP()) +1);", nativeQuery = true)
    public BigDecimal totalAmountByCurrentDay(Long accountId);
    
}
