package com.devsu.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@MappedSuperclass
public class Person implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4645752452501787069L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Gender is mandatory")
    private String gender;

    @Min(value = 18, message = "Must be above 18 years old.")
    private int age;

    @NotNull
    @Column(unique = true)
    private String personalId;

    @NotNull
    private String address;

    @NotNull
    private String phone;

}
