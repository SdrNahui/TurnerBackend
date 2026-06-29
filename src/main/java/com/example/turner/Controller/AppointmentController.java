package com.example.turner.Controller;

import com.example.turner.DTO.*;
import com.example.turner.Model.Appointment;
import com.example.turner.Model.FinancialReport;
import com.example.turner.Service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turner/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService){
        this.appointmentService = appointmentService;
    }
    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getAll(){
        List<AppointmentResponse> appointmentResponses = appointmentService.getAll()
                .stream().map(AppointmentResponse::new).toList();
        return ResponseEntity.ok(appointmentResponses);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable Long id){
        return appointmentService.getAppointmentById(id).map(AppointmentResponse::new).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/create")
    public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody @Valid CreateAppointmentRequest
                                                                             createAppointment){
        System.out.println("Nombre: " + createAppointment.getClientName());
        System.out.println("Telefono: " + createAppointment.getPhoneNumber());
        System.out.println("Servicio" + createAppointment.getOfferedId());
        Appointment aSave = appointmentService.createPendingAppointment(createAppointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AppointmentResponse(aSave));
    }
    @PostMapping("/confirm/{id}")
    public ResponseEntity<AppointmentResponse> confirmAppointment(@PathVariable Long id,
                                                                  @RequestBody AppointmentRequest appointmentRequest){
        Appointment appointment = appointmentService.confirmAppointment(id, appointmentRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new AppointmentResponse(appointment));
    }
    @PostMapping("/finished/{id}")
    public ResponseEntity<AppointmentResponse> finishedAppointment(@PathVariable Long id,
                                                                   @RequestParam boolean confirm){
        Appointment appointment = appointmentService.finishedAppointment(id, confirm);
        return ResponseEntity.status(HttpStatus.OK).body(new AppointmentResponse(appointment));
    }
    @PostMapping("/canceled/{id}")
    public ResponseEntity<AppointmentResponse> cancelAppointment(@PathVariable Long id,
                                                                 @RequestParam boolean confirm){
        Appointment appointment = appointmentService.cancelledAppointment(id, confirm);
        return ResponseEntity.status(HttpStatus.OK).body(new AppointmentResponse(appointment));
    }
    @PostMapping("/report")
    public ResponseEntity<FinancialReportResponse> generateReport(@RequestBody TimeRequest timeRequest){
        FinancialReport fr = appointmentService.generateReport(timeRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new FinancialReportResponse(fr));
    }
    @PostMapping("/reorganize/{id}")
    public ResponseEntity<AppointmentResponse> reorganizeAppointment(@PathVariable Long id,
                                                                     @RequestBody TimeRequest timeRequest){
        System.out.println("NewStart: " + timeRequest.getStart());
        Appointment appointment = appointmentService.reorganizeAppointment(id, timeRequest.getStart());
        return ResponseEntity.ok(new AppointmentResponse(appointment));
    }
}
