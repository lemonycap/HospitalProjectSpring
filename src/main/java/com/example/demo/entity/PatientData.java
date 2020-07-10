package com.example.demo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "patient_data")
public class PatientData implements Serializable {
    @Id
    @GeneratedValue
    @Column (name = "id")
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", referencedColumnName = "user_id")
    private AppUser patient;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id", referencedColumnName = "user_id")
    private AppUser doctor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nurse_id", referencedColumnName = "user_id")
    private AppUser nurse;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "diagnosis_id", referencedColumnName = "id_diagnosis")
    private Diagnosis diagnosis;

    @Column(name ="patient_status")
    private int patientStatus;

    @ManyToMany
    @JoinTable(name = "prescription_history",
            joinColumns = { @JoinColumn(name = "patient_id") },
            inverseJoinColumns = { @JoinColumn(name = "prescription_id") })
    Set<Prescription> prescriptionHistory;

    @ManyToMany
    @JoinTable(name = "current_prescriptions",
            joinColumns = { @JoinColumn(name = "patient_id") },
            inverseJoinColumns = { @JoinColumn(name = "prescription_id") })
    Set<Prescription> currentPrescriptionsList;

    public Set<Prescription> getCurrentPrescriptionsList() {
        return currentPrescriptionsList;
    }

    public void setCurrentPrescriptionsList(Set<Prescription> currentPrescriptionsList) {
        this.currentPrescriptionsList = currentPrescriptionsList;
    }

    public PatientData() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Prescription> getPrescriptionHistory() {
        return prescriptionHistory;
    }

    public void setPrescriptionHistory(Set<Prescription> prescriptionHistory) {
        this.prescriptionHistory = prescriptionHistory;
    }

    public int getPatientStatus() {
        return patientStatus;
    }

    public void setPatientStatus(int patientStatus) {
        this.patientStatus = patientStatus;
    }

    public AppUser getPatient() {
        return patient;
    }

    public void setPatient(AppUser patient) {
        this.patient = patient;
    }

    public AppUser getDoctor() {
        return doctor;
    }

    public void setDoctor(AppUser doctor) {
        this.doctor = doctor;
    }

    public AppUser getNurse() {
        return nurse;
    }

    public void setNurse(AppUser nurse) {
        this.nurse = nurse;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }
}
