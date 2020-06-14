package com.example.demo.repos;

import com.example.demo.entity.PatientData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PatientDataRepo extends JpaRepository<PatientData,Integer> {

    @Query("Select p from PatientData p")
    List<PatientData> allPatientData();

    @Query("SELECT u FROM PatientData u WHERE u.patient.id = :patient_id")
    public PatientData getPatientById(@Param("patient_id") int id);

    @Query("SELECT u FROM PatientData u WHERE u.doctor.id IS NULL and u.patientStatus = 1")
    public List<PatientData> findPatientWithoutDoctor();

    @Query("SELECT u FROM PatientData u WHERE u.nurse.id IS NULL and u.patientStatus = 1")
    public List<PatientData> findPatientWithoutNurse();

    @Query("SELECT u FROM PatientData u WHERE u.doctor.id = :doctor_id")
    public List<PatientData> findExistingDoctorPatients(@Param("doctor_id") int id);

    @Query("SELECT u FROM PatientData u WHERE u.nurse.id = :nurse_id")
    public List<PatientData> findExistingNursePatients(@Param("nurse_id") int id);

}
