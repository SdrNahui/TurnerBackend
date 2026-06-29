package com.example.turner.Controller;

import com.example.turner.DTO.AvailabilityRequest;
import com.example.turner.DTO.AvailabilityResponse;
import com.example.turner.DTO.AvailabilityUpdateResult;
import com.example.turner.DTO.TimeRequest;
import com.example.turner.Model.AvailabilityBlock;
import com.example.turner.Service.AvailabilityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/turner/availabilities")
public class AvailabilityController {
    private final AvailabilityService availabilityService;
    public AvailabilityController(AvailabilityService availabilityService){
        this.availabilityService = availabilityService;
    }
    @GetMapping
    public ResponseEntity<List<AvailabilityResponse>>getAll(){
        List<AvailabilityResponse> availabilityResponses = availabilityService.getAllAvailability()
                .stream().map(AvailabilityResponse::new).toList();
        return ResponseEntity.ok(availabilityResponses);
    }
    @PostMapping("/create")
    public ResponseEntity<AvailabilityResponse> createAvailability(@Valid @RequestBody AvailabilityRequest
                                                                            availabilityRequest){
        AvailabilityBlock ab = availabilityService.createAvailability(availabilityRequest.getStartDate(),
                availabilityRequest.getEndDate());
        return ResponseEntity.status(HttpStatus.OK).body(new AvailabilityResponse(ab));
    }
    @PutMapping("/{id}")
    public ResponseEntity<AvailabilityUpdateResult> updateAvailability(@PathVariable Long id,
                                                                       @RequestBody TimeRequest timeRequest){
        AvailabilityUpdateResult result = availabilityService.updateAvailability(id, timeRequest);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvailability(@PathVariable Long id){
        availabilityService.deleteAvailability(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/force")
    public ResponseEntity<AvailabilityUpdateResult> forceUpdateAvailability(@PathVariable Long id,
                                                                            @RequestBody TimeRequest timeRequest){
        AvailabilityUpdateResult result = availabilityService.forceUpdateAvailability(id, timeRequest);
        return ResponseEntity.ok(result);
    }
}
