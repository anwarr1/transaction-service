package com.example.gestiontransactions.external;

import com.example.gestiontransactions.dto.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "gestionUser", url = "http://192.168.91.38:8043/api/clients")
public interface ExternalUserService {

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id);


    @GetMapping
    public ResponseEntity<List<Client>> getAllClients();
}


