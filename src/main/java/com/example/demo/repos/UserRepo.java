package com.example.demo.repos;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepo extends JpaRepository<AppUser,Integer> {

    @Query("SELECT u FROM AppUser u WHERE u.email = :email")
    public AppUser getUserByUsername(@Param("email") String email);

    @Query("SELECT u FROM AppUser u WHERE u.role = :role")
    public List<AppUser> getUserByRole(@Param("role") Role role);

    @Query("SELECT u FROM AppUser u WHERE u.id = :id")
    public AppUser getUserById(@Param("id") int id);

}
