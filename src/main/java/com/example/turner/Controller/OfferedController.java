package com.example.turner.Controller;

import com.example.turner.DTO.OfferedServiceRequest;
import com.example.turner.DTO.OfferedServiceResponse;
import com.example.turner.Model.OfferedService;
import com.example.turner.Service.OfferedServicedService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turner/offered")
@CrossOrigin(origins = "*")
public class OfferedController {
    private final OfferedServicedService offeredServicedService;

    public OfferedController(OfferedServicedService offeredServicedService){
        this.offeredServicedService = offeredServicedService;
    }
    @GetMapping
    public ResponseEntity<List<OfferedServiceResponse>> getAll(){
        List<OfferedServiceResponse> offeredServiceResponses = offeredServicedService.getAll()
                .stream().map(OfferedServiceResponse ::new).toList();
        return ResponseEntity.ok(offeredServiceResponses);
    }
    @GetMapping("/{id}")
    public ResponseEntity<OfferedServiceResponse> getServiceById(@PathVariable long id){
        return offeredServicedService.getOfferedById(id).map(OfferedServiceResponse::new).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/create")
    public ResponseEntity<OfferedServiceResponse> createOffered(@Valid @RequestBody OfferedServiceRequest
                                                                            offeredServiceRequest){
        OfferedService of = offeredServicedService.createOffered(offeredServiceRequest.getServiceName(),
                offeredServiceRequest.getPrice(), offeredServiceRequest.getEstimatedDuration());
        return ResponseEntity.status(HttpStatus.OK).body(new OfferedServiceResponse(of));
    }
    @PutMapping("/{id}")
    public ResponseEntity<OfferedServiceResponse> updateOffered(@PathVariable Long id,
                                                                @RequestBody OfferedServiceRequest request){
        OfferedService of = offeredServicedService.updateOfferedService(id, request);
        return ResponseEntity.ok(new OfferedServiceResponse(of));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffered(@PathVariable Long id){
        offeredServicedService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
