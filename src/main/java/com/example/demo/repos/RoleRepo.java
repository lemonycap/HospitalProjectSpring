package com.example.demo.repos;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RoleRepo extends JpaRepository<Role,Integer> {

     @Query("SELECT r FROM Role r WHERE r.name = :name")
     public Role findByName(@Param("name")String name);
}
