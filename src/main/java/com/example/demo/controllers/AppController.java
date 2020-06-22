package com.example.demo.controllers;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.PatientData;
import com.example.demo.entity.Role;
import com.example.demo.repos.PatientDataRepo;
import com.example.demo.repos.RoleRepo;
import com.example.demo.repos.UserRepo;
import com.example.demo.services.PatientDataService;
import com.example.demo.utils.Container;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@Controller
public class AppController {
    private static final Logger logger = LogManager.getLogger(AppController.class);

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    PatientDataRepo patientDataRepo;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    PatientDataService patientDataService;


    @GetMapping("/login")
    public String login() {
        logger.info("redirecting to login page");
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        logger.info("redirecting to registration page");
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(AppUser user, @RequestParam ("position") String position,
                             Map<String, Object> model) {
        AppUser userFromDb = userRepo.getUserByUsername(user.getEmail());
        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "registration";
        }

        user.setEnabled(true);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepo.findByName(position.toUpperCase());
        System.out.println(userRole);
        user.setRole(userRole);
        userRepo.save(user);
        patientDataService.refreshPatients();
        return "redirect:login";
    }

    @GetMapping ("/doctor")
    public String doctor(Authentication authentication, Map<String, Object> model){
        logger.info("redirecting to doctor page");
        String name = authentication.getName();
        AppUser doctor = userRepo.getUserByUsername(name);
        model.put("doctor",doctor);
        List<PatientData> doctorsPatients = patientDataRepo.findExistingDoctorPatients(doctor.getId());
        model.put("doctorsPatients",doctorsPatients);
        return "doctorPage";
    }
    @GetMapping ("/nurse")
    public String nurse(Authentication authentication, Map<String, Object> model) {
        logger.info("redirecting to nurse page");
        String name = authentication.getName();
        AppUser nurse = userRepo.getUserByUsername(name);
        model.put("nurse",nurse);
        List<PatientData> nursePatients = patientDataRepo.findExistingNursePatients(nurse.getId());
        model.put("nursePatients",nursePatients);
        return "nursePage";
    }
    @GetMapping("/patient")
    public String patient(Authentication authentication, Map<String, Object> model) {
        logger.info("redirecting to patient page");
        String name = authentication.getName();
        AppUser patient = userRepo.getUserByUsername(name);
        model.put("patient",patient);
        PatientData patientData = patientDataRepo.getPatientById(patient.getId());
        model.put("patientData",patientData);
        return "patientPage";
    }
    @GetMapping("/403")
    public String Error() {
        logger.info("redirecting to error page");
        return "403";
    }


}
