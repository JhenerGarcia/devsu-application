package com.devsu.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
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
@Table(name = "ACCOUNTS")
public class Account implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3341862050035438575L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CLIENT_ID")
    @JsonIgnoreProperties({ "name", "gender", "age", "personalId", "address", "phone", "password", "state" })
    private Client client;

    @Column(unique = true)
    @NotNull
    private String number;

    @NotNull
    private String type;

    @NotNull
    private BigDecimal balance;

    private boolean state;

}
