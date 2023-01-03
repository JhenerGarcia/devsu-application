package com.devsu.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.devsu.configuration.ApiException;
import com.devsu.dto.ApiResult;
import com.devsu.entity.Client;
import com.devsu.repository.ClientRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClientService {

    private ClientRepository clientRepo;

    @Autowired
    public ClientService(ClientRepository clientRepo) {
        this.clientRepo = clientRepo;
    }

    public Client getClientById(Long id) throws ApiException {
        log.info("Retrieving client with id: {}", id);
        return clientRepo.findById(id)
                .orElseThrow(() -> new ApiException(new ApiResult(String.valueOf(HttpStatus.NOT_FOUND.value()),
                        "Client doesn't exists.", LocalDateTime.now(), null)));
    }

    public List<Client> getAllClients() {
        log.info("Retrieving all clients.");
        return clientRepo.findAll();
    }

    public Client createClient(Client client) throws ApiException {
        log.info("Creating client. Personal ID: {}. Name: {}.", client.getPersonalId(), client.getName());
        Optional<Client> existingClient = clientRepo.findByPersonalId(client.getPersonalId());
        if (existingClient.isPresent()) {
            log.error("Client with personal id {} already exists.", client.getPersonalId());
            throw new ApiException(new ApiResult(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    "Client with personal id " + client.getPersonalId() + " already exists.", LocalDateTime.now(),
                    null));
        }
        return clientRepo.save(client);
    }

    public Client updateClient(Long id, Client client) throws ApiException {
        log.info("Updating client. ID: {}. Personal ID: {}. Name: {}.", client.getId(), client.getPersonalId(),
                client.getName());
        if (!clientRepo.existsById(id)) {
            log.error("Client with id {} doesn't exists.", id);
            throw new ApiException(new ApiResult(String.valueOf(HttpStatus.NOT_FOUND.value()), "Client doesn't exists.",
                    LocalDateTime.now(), null));
        }

        client.setId(id);
        return clientRepo.save(client);
    }

    public void deleteClient(Long id) throws ApiException {
        if (!clientRepo.existsById(id)) {
            log.error("Client with id {} doesn't exists.", id);
            throw new ApiException(new ApiResult(String.valueOf(HttpStatus.NOT_FOUND.value()), "Client doesn't exists.",
                    LocalDateTime.now(), null));
        }
        log.info("Deleting client with ID: {}", id);
        clientRepo.deleteById(id);
    }

    public Client getClientByPersonalId(String personalId) throws ApiException {
        log.info("Retrieving client with Personal Id: {}", personalId);
        return clientRepo.findByPersonalId(personalId)
                .orElseThrow(() -> new ApiException(new ApiResult(String.valueOf(HttpStatus.NOT_FOUND.value()),
                        "Client doesn't exists.", LocalDateTime.now(), null)));
    }

}
