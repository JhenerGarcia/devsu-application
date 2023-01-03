package com.devsu.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "CLIENTS")
public class Client extends Person implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1677507566239597067L;

    @NotNull
    @NotBlank
    private String password;
    private boolean state;
}
