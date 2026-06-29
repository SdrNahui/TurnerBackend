package com.example.turner.Repository;

import com.example.turner.Model.OfferedService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferedServiceRepository extends JpaRepository<OfferedService, Long>{
    List<OfferedService> findByActiveTrue();

}
