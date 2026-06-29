package com.example.turner.Service;

import com.example.turner.DTO.AppointmentRequest;
import com.example.turner.DTO.CreateAppointmentRequest;
import com.example.turner.DTO.TimeRequest;
import com.example.turner.Model.*;
import com.example.turner.Repository.AppointmentRepository;
import com.example.turner.Repository.AvailabilityRepository;
import com.example.turner.Repository.ClientRepository;
import com.example.turner.Repository.OfferedServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;
    private final ClientService clientService;
    private final OfferedServiceRepository offeredRepository;
    private final AvailabilityRepository availabilityRepository;
    private static final int REDUCTION_BUFFER_MINUTES = 10;
    private static final int EXTENSION_BUFFER_MINUTES = 15;
    private static final int REST_BUFFER_MINUTES = 30;
    public AppointmentService(AppointmentRepository appointmentRepository, ClientRepository clientRepository,
                              ClientService clientService, OfferedServiceRepository offeredRepository,
                              AvailabilityRepository availabilityRepository){
        this.appointmentRepository = appointmentRepository;
        this.clientRepository = clientRepository;
        this.clientService = clientService;
        this.offeredRepository = offeredRepository;
        this.availabilityRepository = availabilityRepository;
    }
    public List<Appointment> getAll(){
        return appointmentRepository.findAll();
    }
    public Optional<Appointment> getAppointmentById(Long id){
        return appointmentRepository.findById(id);
    }
    @Transactional
    public Appointment createPendingAppointment(CreateAppointmentRequest request){
        Client client;
        if(request.getClientId() != null){
            client = clientRepository.findById(request.getClientId())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        } else {
            client = clientRepository.findByPhoneNumber(request.getPhoneNumber())
                    .orElseGet(() -> clientService.createClient(request.getClientName(), request.getClientLastName(),
                            request.getPhoneNumber()));
        }
        OfferedService of = offeredRepository.findById(request.getOfferedId())
                .orElseThrow(() -> new EntityNotFoundException());
        Appointment a1 = new Appointment();
        a1.setClient(client);
        a1.setOffered(of);
        a1.setStatus(AppointmentStatus.PENDING);
        return appointmentRepository.save(a1);
    }

    @Transactional
    public Appointment confirmAppointment(Long appointmentId, AppointmentRequest appointmentCreate){
        Appointment a1 = appointmentRepository.findById(appointmentId).orElseThrow(() -> new EntityNotFoundException());
        if (a1.getStatus() != AppointmentStatus.PENDING){
            throw new IllegalStateException("Solo turnos pendientes");
        }
        OfferedService s1 = a1.getOffered();
        LocalDateTime start = appointmentCreate.getStartDate();
        int duration = s1.getEstimatedDuration();
        LocalDateTime end = start.plusMinutes(duration);
        LocalDateTime validationStart = start.minusMinutes(REDUCTION_BUFFER_MINUTES + REST_BUFFER_MINUTES);
        LocalDateTime validationEnd = end.plusMinutes(EXTENSION_BUFFER_MINUTES + REST_BUFFER_MINUTES);
        if(start.getDayOfWeek() == DayOfWeek.SUNDAY){
            throw new IllegalStateException("No se trabaja los domingos");
        }
        System.out.println("Start: " + start);
        System.out.println("End: " + end);
        boolean available = availabilityRepository.existsByActiveTrueAndStartDateLessThanEqualAndEndDateGreaterThanEqual
                (start, end);
        if (!available){
            throw new IllegalStateException("Horario fuera de la disponibilidad");
        }

        System.out.println("Start: " + start);
        System.out.println("End: " + end);
        System.out.println("validationStart: " + validationStart);
        System.out.println("validationEnd: " + validationEnd);

        boolean existTime = appointmentRepository
                .existsByStatusAndStartDateLessThanAndEndDateGreaterThan(AppointmentStatus.RESERVED,
                        validationEnd, validationStart);

        System.out.println("Conflicto: " + existTime);

// Agregá este método en el repository temporalmente para ver cuál conflictúa
        appointmentRepository.findAll().stream()
                .filter(a -> a.getStatus() == AppointmentStatus.RESERVED)
                .forEach(a -> System.out.println("Reservado: id=" + a.getId() +
                        " start=" + a.getStartDate() + " end=" + a.getEndDate()));
        if(existTime){
            throw new IllegalStateException("Horario ocupado");
        }
        a1.setStartDate(start);
        a1.setEndDate(end);
        a1.setOffered(s1);
        a1.setPriceAtBooking(s1.getPrice());
        a1.setHomeService(appointmentCreate.isHasAtHome());
        if(appointmentCreate.isHasAtHome()){
            a1.setCommissionPercentage(BigDecimal.ZERO);
        } else {
            a1.setCommissionPercentage(new BigDecimal("0.30"));
       }
        a1.setStatus(AppointmentStatus.RESERVED);
        return appointmentRepository.save(a1);
    }
    @Transactional
    public Appointment finishedAppointment(Long appointmentId, boolean confirm){
        Appointment a1 = appointmentRepository.findById(appointmentId).orElseThrow(() -> new EntityNotFoundException());
        if(a1.getStatus() != AppointmentStatus.RESERVED){
            throw new IllegalStateException("Solo pueden finalizarse turno reservados");
        }

        if(LocalDateTime.now().isBefore(a1.getEndDate().plusMinutes(EXTENSION_BUFFER_MINUTES))){
            throw new IllegalStateException("El turno no termino");
        }
        if(!confirm){
            return a1;
        }
        a1.setStatus(AppointmentStatus.FINISHED);
        return a1;
    }
    @Transactional
    public Appointment cancelledAppointment(Long appointmentId, boolean confirm){
        Appointment a1 = appointmentRepository.findById(appointmentId).orElseThrow(() -> new EntityNotFoundException());
        if((a1.getStatus() != AppointmentStatus.RESERVED) && (a1.getStatus() != AppointmentStatus.PENDING)){
            throw new IllegalStateException("Solo se puede cancelar si esta reservado o pendiente");
        }
        if(!confirm){
            return a1;
        }
        a1.setStatus(AppointmentStatus.CANCELLED);
        return a1;
    }

    private BigDecimal calculateCommission(Appointment appointment){
        return appointment.getPriceAtBooking().multiply(appointment.getCommissionPercentage())
                .setScale(2, RoundingMode.HALF_UP);
    }
    public FinancialReport generateReport(TimeRequest timeRequest){
        List<Appointment> finished = appointmentRepository.findByStatusAndStartDateBetween(AppointmentStatus.FINISHED,
                timeRequest.getStart(),timeRequest.getEnd());
        BigDecimal totalGross = BigDecimal.ZERO;
        BigDecimal totalCommission = BigDecimal.ZERO;
        BigDecimal totalNet = BigDecimal.ZERO;
        int totalAppointment = 0;
        for(Appointment a : finished) {
            BigDecimal price = a.getPriceAtBooking();
            BigDecimal commission = calculateCommission(a);
            BigDecimal net = price.subtract(commission);
            totalGross = totalGross.add(price);
            totalCommission = totalCommission.add(commission);
            totalNet = totalNet.add(net);
            totalAppointment ++;
        }
        FinancialReport financialReport = new FinancialReport();
        financialReport.setTotalGross(totalGross.setScale(2, RoundingMode.HALF_UP));
        financialReport.setTotalCommission(totalCommission.setScale(2, RoundingMode.HALF_UP));
        financialReport.setTotalNet(totalNet.setScale(2, RoundingMode.HALF_UP));
        financialReport.setTotalAppointment(totalAppointment);
        return financialReport;
    }
    @Transactional
    public Appointment reorganizeAppointment(Long appointmentId, LocalDateTime newStart){
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException());
        if(appointment.getStatus() != AppointmentStatus.RESERVED){
            throw new IllegalStateException("Solo pueden ser turnos reservados");
        }
        Client c1 = appointment.getClient();
        if(c1.getType() == ClientType.NEW){
            if(!appointment.getStartDate().toLocalDate().equals(newStart.toLocalDate())){
                throw new IllegalStateException("Solo se puede reorganizar en el mismo dia");
            }
        }
        if(newStart.getDayOfWeek() == DayOfWeek.SUNDAY){
            throw new IllegalStateException("No se trabaja los domingos");
        }
        int duration = appointment.getOffered().getEstimatedDuration();
        LocalDateTime newEnd = newStart.plusMinutes(duration);
        boolean available = availabilityRepository
                .existsByActiveTrueAndStartDateLessThanEqualAndEndDateGreaterThanEqual(newStart, newEnd);
        if (!available){
            throw new IllegalStateException("Horario fuera de disponibilidad");
        }
        LocalDateTime validationStart = newStart.minusMinutes(REDUCTION_BUFFER_MINUTES + REST_BUFFER_MINUTES);
        LocalDateTime validationEnd = newEnd.plusMinutes(EXTENSION_BUFFER_MINUTES + REST_BUFFER_MINUTES);
        boolean conflict = appointmentRepository
                .existsByIdNotAndStatusAndStartDateLessThanAndEndDateGreaterThan(appointment.getId(),
                        AppointmentStatus.RESERVED, validationEnd, validationStart);
        if(conflict){
            throw new IllegalStateException("horario ocupado");
        }
        appointment.setStartDate(newStart);
        appointment.setEndDate(newEnd);
        return appointment;
    }

    public LocalDateTime findNextAvailableSlot(LocalDateTime desiredStart, int durationMinutes){
        LocalDateTime dayStart = desiredStart.toLocalDate().atStartOfDay();
        LocalDateTime dayEnd = desiredStart.toLocalDate().atTime(23,59);
        List<AvailabilityBlock> blocks = availabilityRepository
                .findByActiveTrueAndStartDateLessThanEqualAndEndDateGreaterThan(dayEnd, dayStart);
        List<Appointment> appointments = appointmentRepository
                .findByStatusAndStartDateBetween(AppointmentStatus.RESERVED, dayStart, dayEnd);
        appointments.sort(Comparator.comparing(Appointment::getStartDate));
        for(AvailabilityBlock block : blocks){
            LocalDateTime concurrentPointer = block.getStartDate()
                    .isAfter(desiredStart) ? block.getStartDate() : desiredStart;
            for(Appointment a : appointments){
                if(a.getEndDate().isBefore(block.getStartDate()) || a.getStartDate().isAfter(block.getEndDate())){
                    continue;
                }
                LocalDateTime validationEnd = concurrentPointer.plusMinutes(durationMinutes)
                        .plusMinutes(EXTENSION_BUFFER_MINUTES + REST_BUFFER_MINUTES);
                if(validationEnd.isBefore(a.getStartDate())){
                   return concurrentPointer;
                }
                concurrentPointer = concurrentPointer.isAfter(a.getStartDate()) ? concurrentPointer :
                        a.getEndDate().plusMinutes(EXTENSION_BUFFER_MINUTES + REST_BUFFER_MINUTES);
            }
            LocalDateTime blockEnd = block.getEndDate();
            LocalDateTime validationEnd = concurrentPointer.plusMinutes(durationMinutes)
                    .plusMinutes(EXTENSION_BUFFER_MINUTES + REST_BUFFER_MINUTES);
            if(validationEnd.isBefore(blockEnd)){
                return concurrentPointer;
            }
        }
        return null;
    }

}