package com.devsu.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "TRANSACTIONS")
public class Transaction implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1329667426311794722L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACCOUNT_ID")
    @JsonIgnoreProperties({"client", "balance", "state"})
    private Account account;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    private BigDecimal initialBalance;
    
    @NotNull
    private BigDecimal amount;
    
    @NotNull
    private BigDecimal finalBalance;

}
