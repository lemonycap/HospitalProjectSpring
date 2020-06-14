package com.example.demo.repos;

import com.example.demo.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrescriptionRepo extends JpaRepository<Prescription,Integer> {
    @Query("Select p from Prescription p")
    List<Prescription> allPrescriptions();

    @Query("Select p from Prescription p where p.prescriptionClass = 'medicine'")
    List<Prescription> medicine();

    @Query("Select p from Prescription p where p.prescriptionClass = 'procedures'")
    List<Prescription> procedures();

    @Query("Select p from Prescription p where p.prescriptionClass = 'operations'")
    List<Prescription> operations();

    @Query("Select p from Prescription p where p.id = :id")
    Prescription findPrescriptionById(@Param("id") int id);

}
