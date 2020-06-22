package com.example.demo.controllers;

import com.example.demo.repos.PatientDataRepo;
import com.example.demo.repos.UserRepo;
import com.example.demo.services.NurseService;
import com.example.demo.services.PatientDataService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NurseController {
    private static final Logger logger = LogManager.getLogger(NurseController.class);
    @Autowired
    UserRepo userRepo;
    @Autowired
    PatientDataRepo patientDataRepo;
    @Autowired
    NurseService nurseService;
    @Autowired
    PatientDataService patientDataService;

    @PostMapping("/nurse/findPatients")
    public String findPatients(@RequestParam("id") int id) {
        logger.info("performing findPatients procedure for nurse");
        String message = nurseService.findPatients(id);
        return "redirect:/nurse";
    }

    @PostMapping("/nurse/doPrescription")
    public String doPresctiption(@RequestParam("id") int prescriptionId, @RequestParam("patientId") int patientId) {
        logger.info("performing doPrescription procedure for nurse");
        nurseService.doPrescription(patientId, prescriptionId);
        return "redirect:/nurse";
    }
}
