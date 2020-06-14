package com.example.demo.repos;

import com.example.demo.entity.MedicineList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MedicineListRepo  extends JpaRepository<MedicineList,Integer> {

    @Query("Select m from MedicineList m where m.name =:name")
    MedicineList medicine(@Param("name") String name);
}
