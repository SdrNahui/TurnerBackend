package com.example.turner.Service;

import com.example.turner.DTO.OfferedServiceRequest;
import com.example.turner.Model.OfferedService;
import com.example.turner.Repository.OfferedServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OfferedServicedService {
    private final OfferedServiceRepository offeredRepository;

    public OfferedServicedService(OfferedServiceRepository offeredRepository){
        this.offeredRepository = offeredRepository;
    }

    public OfferedService createOffered(String nameOffered, BigDecimal price, Integer estimatedDuration){
        OfferedService o1 = new OfferedService();
        o1.setServiceName(nameOffered);
        o1.setEstimatedDuration(estimatedDuration);
        o1.setPrice(price);
        return offeredRepository.save(o1);
    }
    public List<OfferedService> getAll(){
        return offeredRepository.findByActiveTrue();
    }
    public Optional<OfferedService> getOfferedById(Long id){
        return offeredRepository.findById(id);
    }
    public OfferedService updateOfferedService(long id, OfferedServiceRequest request){
        OfferedService of = offeredRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        if(request.getServiceName() == null || request.getServiceName().isBlank()){
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }
        if(request.getPrice() == null || request.getPrice().compareTo(BigDecimal.ZERO) == 0){
            throw new IllegalArgumentException("El precio no puede estar vacio.");
        }
        if(request.getEstimatedDuration() == null || request.getEstimatedDuration() == 0){
            throw new IllegalArgumentException("La duracion no puede estar vacia.");
        }         
        of.setServiceName(request.getServiceName());
        of.setEstimatedDuration(request.getEstimatedDuration());
        of.setPrice(request.getPrice());
            return offeredRepository.save(of);
    }
    @Transactional
    public void deleteService(Long id) {
        OfferedService service = offeredRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado"));
        service.setActive(false);
        offeredRepository.save(service);
    }
}
