package com.devsu.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    private ClientReport client;
    private BigDecimal totalDeposits = new BigDecimal(0d);
    private BigDecimal totalWithdraws = new BigDecimal(0d);
    private BigDecimal currentBalance = new BigDecimal(0d);
    private List<TransactionReport> transactions = new ArrayList<TransactionReport>();
}


