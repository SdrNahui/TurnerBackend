package com.example.turner.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Historial_medico")
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Positive
    private int age;
    @NotBlank
    private String gender;
    private LocalDate bornDate;
    @DecimalMin("0.0")
    private BigDecimal height;
    @DecimalMin("0.0")
    private BigDecimal weight;
    @NotBlank
    private String occupation;
    private boolean hasPhysicalActivity;
    private boolean hasAllergy;
    @ElementCollection
    @CollectionTable(name = "alergias", joinColumns = @JoinColumn(name = "historial_id"))
    @Column(name = "alergia")
    private List<String> allergies;
    private boolean hasContagiousDisease;
    @ElementCollection
    @CollectionTable(name = "enfermedadesContagiosas", joinColumns = @JoinColumn(name = "historial_id"))
    @Column(name = "EnfermedadContagiosa")
    private List<String> contagiousDiseases;
    private String other;
    private boolean hasMedicament;
    @ElementCollection
    @CollectionTable(name = "medicamentos", joinColumns = @JoinColumn(name = "historial_id"))
    @Column(name = "medicamento")
    private List<String> medicaments;
    private boolean hasSurgery;
    @ElementCollection
    @CollectionTable(name = "cirugias", joinColumns = @JoinColumn(name = "historial_id"))
    @Column(name = "cirugia")
    private List<String> surgeries;
    private String notes;
    @OneToOne
    @JoinColumn(name = "Cliente_id", nullable = false)
    private Client client;
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
    public MedicalHistory(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBornDate() {
        return bornDate;
    }

    public void setBornDate(LocalDate bornDate) {
        this.bornDate = bornDate;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }


    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public boolean isHasPhysicalActivity() {
        return hasPhysicalActivity;
    }

    public void setHasPhysicalActivity(boolean hasPhysicalActivity) {
        this.hasPhysicalActivity = hasPhysicalActivity;
    }

    public boolean isHasAllergy() {
        return hasAllergy;
    }

    public void setHasAllergy(boolean hasAllergy) {
        this.hasAllergy = hasAllergy;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public boolean isHasContagiousDisease() {
        return hasContagiousDisease;
    }

    public void setHasContagiousDisease(boolean hasContagiousDisease) {
        this.hasContagiousDisease = hasContagiousDisease;
    }

    public List<String> getContagiousDiseases() {
        return contagiousDiseases;
    }

    public void setContagiousDiseases(List<String> contagiousDiseases) {
        this.contagiousDiseases = contagiousDiseases;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public boolean isHasMedicament() {
        return hasMedicament;
    }

    public void setHasMedicament(boolean hasMedicament) {
        this.hasMedicament = hasMedicament;
    }

    public List<String> getMedicaments() {
        return medicaments;
    }

    public void setMedicaments(List<String> medicaments) {
        this.medicaments = medicaments;
    }

    public boolean isHasSurgery() {
        return hasSurgery;
    }

    public void setHasSurgery(boolean hasSurgery) {
        this.hasSurgery = hasSurgery;
    }

    public List<String> getSurgeries() {
        return surgeries;
    }

    public void setSurgeries(List<String> surgeries) {
        this.surgeries = surgeries;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
