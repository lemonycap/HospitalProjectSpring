package com.example.demo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "prescription_table")
public class Prescription implements Serializable {
    @Id
    @GeneratedValue
    @Column (name = "id_prescription")
    private int id;

    @Column (name = "prescription_class")
    private String prescriptionClass;

    @Column (name = "prescription_name")
    private String name;

    @ManyToMany
    @JoinTable(name = "prescription_history",
            joinColumns = { @JoinColumn(name = "prescription_id") },
            inverseJoinColumns = { @JoinColumn(name = "patient_id") })
    Set<PatientData> prescriptions;

    @ManyToMany
    @JoinTable(name = "current_prescriptions",
            joinColumns = { @JoinColumn(name = "prescription_id") },
            inverseJoinColumns = { @JoinColumn(name = "patient_id") })
    Set<PatientData> patientCurrentPrescriptions;


    public Set<PatientData> getPatientCurrentPrescriptions() {
        return patientCurrentPrescriptions;
    }

    public void setPatientCurrentPrescriptions(Set<PatientData> patientCurrentPrescriptions) {
        this.patientCurrentPrescriptions = patientCurrentPrescriptions;
    }

    public Set<PatientData> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(Set<PatientData> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrescriptionClass() {
        return prescriptionClass;
    }

    public void setPrescriptionClass(String prescriptionClass) {
        this.prescriptionClass = prescriptionClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
