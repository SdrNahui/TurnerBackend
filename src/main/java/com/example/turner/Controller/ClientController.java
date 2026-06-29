package com.example.turner.Controller;

import com.example.turner.DTO.ClientRequest;
import com.example.turner.DTO.ClientResponse;
import com.example.turner.Model.Client;
import com.example.turner.Service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turner/clients")
@CrossOrigin(origins = "*")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }
    @GetMapping
    public ResponseEntity<List<ClientResponse>> getAll(){
        List<ClientResponse> clientResponses = clientService.getAll().stream().map(ClientResponse::new).toList();
        return ResponseEntity.ok(clientResponses);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse>getClientById(@PathVariable long id){
        return clientService.getClientById(id).map(ClientResponse::new).map(ResponseEntity::ok).orElse(
                ResponseEntity.notFound().build());
    }
    @PostMapping("/create")
    public ResponseEntity<ClientResponse> createClient(@Valid @RequestBody ClientRequest clientRequest){
        Client client = clientService.createClient(clientRequest.getClientName(), clientRequest.getLastName(),
                clientRequest.getPhoneNumber());
        return ResponseEntity.status(HttpStatus.OK).body(new ClientResponse(client));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable Long id,
                                                       @RequestBody ClientRequest request){
        System.out.println("Nombre: " + request.getClientName());
        System.out.println("Apellido: " + request.getLastName());
        System.out.println("Telefono: " + request.getPhoneNumber());
        Client client = clientService.updateClient(id, request);
        return ResponseEntity.ok(new ClientResponse(client));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id){
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
