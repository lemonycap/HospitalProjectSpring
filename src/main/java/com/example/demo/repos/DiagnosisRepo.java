package com.example.demo.repos;

import com.example.demo.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiagnosisRepo extends JpaRepository<Diagnosis,Integer> {

    @Query ("Select d from Diagnosis d")
    List<Diagnosis> allDiagnoses();
}
