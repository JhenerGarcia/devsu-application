package com.devsu.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionReport {
    private LocalDateTime transactionDate;
    private boolean state;
    private double initialBalance;
    private double transactionAmount;
    private double finalBalance;
}