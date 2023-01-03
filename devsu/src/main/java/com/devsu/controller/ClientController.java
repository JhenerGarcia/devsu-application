package com.devsu.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.configuration.ApiException;
import com.devsu.dto.ApiData;
import com.devsu.dto.ApiResult;
import com.devsu.entity.Client;
import com.devsu.service.ClientService;

@RestController
@RequestMapping("/v1/clients")
public class ClientController {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult> getById(@PathVariable(name = "id") Long id) throws ApiException {
        Client client = clientService.getClientById(id);
        return ResponseEntity.ok(new ApiResult(String.valueOf(HttpStatus.OK.value()), "Success", LocalDateTime.now(),
                new ApiData("client", client)));
    }

    @GetMapping()
    public ResponseEntity<ApiResult> getAll() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(new ApiResult(String.valueOf(HttpStatus.OK.value()), "Success", LocalDateTime.now(),
                new ApiData("clients", clients)));
    }

    @PostMapping()
    public ResponseEntity<ApiResult> createClient(@RequestBody Client client) throws ApiException {
        Client newClient = clientService.createClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResult(String.valueOf(HttpStatus.OK.value()),
                "Success", LocalDateTime.now(), new ApiData("client", newClient)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult> updateClient(@PathVariable Long id, @RequestBody Client client)
            throws ApiException {
        clientService.updateClient(id, client);
        return ResponseEntity
                .ok(new ApiResult(String.valueOf(HttpStatus.NO_CONTENT.value()), "Success", LocalDateTime.now(), null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult> deleteClient(@PathVariable Long id) throws ApiException {
        clientService.deleteClient(id);
        return ResponseEntity
                .ok(new ApiResult(String.valueOf(HttpStatus.NO_CONTENT.value()), "Success", LocalDateTime.now(), null));
    }

}
