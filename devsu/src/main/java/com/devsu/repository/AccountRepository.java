package com.devsu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsu.entity.Account;
import com.devsu.entity.Client;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    public Optional<Account> findByNumber(String number);

    public List<Account> findByClient(Client client);

}
