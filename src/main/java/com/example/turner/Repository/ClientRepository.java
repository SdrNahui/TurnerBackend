package com.example.turner.Repository;

import com.example.turner.Model.Client;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByPhoneNumber(String phoneNumber);
    List<Client> findByActiveTrue();
}
