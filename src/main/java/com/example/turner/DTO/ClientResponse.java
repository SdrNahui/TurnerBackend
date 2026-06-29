package com.example.turner.DTO;

import com.example.turner.Model.Client;
import com.example.turner.Model.ClientType;

public class ClientResponse {
    private Long id;
    private String name;
    private String lastName;
    private String phoneNumber;
    private ClientType type;
    private boolean active;

    public ClientResponse(Client client){
        this.id = client.getId();
        this.name = client.getName();
        this.lastName = client.getLastName();
        this.phoneNumber = client.getPhoneNumber();
        this.type = client.getType();
        this.active = client.isActive();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
