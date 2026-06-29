package com.example.turner.Service;

import com.example.turner.DTO.ClientRequest;
import com.example.turner.Model.Client;
import com.example.turner.Model.ClientType;
import com.example.turner.Repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public Client createClient(String nameClient, String lastName, String phoneClient){
        if(nameClient == null || nameClient.isBlank()){
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        if(lastName == null || lastName.isBlank()){
            throw new IllegalArgumentException("El apellido no puede estar vacio");
        }
        if(phoneClient == null || phoneClient.isBlank()){
            throw new IllegalArgumentException("El numero de celular no puede estar vacio");
        }
        Client c1 = new Client();
        c1.setName(nameClient);
        c1.setLastName(lastName);
        c1.setPhoneNumber(phoneClient);
        c1.setAppointments(new ArrayList<>());
        c1.setType(ClientType.NEW);
        c1.setActive(true);
        return clientRepository.save(c1);
    }
    public List<Client> getAll(){
        return clientRepository.findByActiveTrue();
    }
    public Optional<Client> getClientById(Long id){
        return clientRepository.findById(id);
    }
    public Client updateClient(Long id, ClientRequest request){
        Client c1 = clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        if(request.getClientName() == null || request.getClientName().isBlank()){
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        if(request.getLastName() == null || request.getClientName().isBlank()){
            throw new IllegalArgumentException("El apellido no puede estar vacio");
        }
        if(request.getPhoneNumber() == null || request.getPhoneNumber().isBlank()){
            throw new IllegalArgumentException("El telefono no puede estar vacio");
        }
        c1.setName(request.getClientName());
        c1.setLastName(request.getLastName());
        c1.setPhoneNumber(request.getPhoneNumber());
        return clientRepository.save(c1);
    }
    public Client deleteClient(Long idClient){
        Client c1 = clientRepository.findById(idClient).orElseThrow(() -> new EntityNotFoundException());
        c1.setActive(false);
        return clientRepository.save(c1);
    }
}
