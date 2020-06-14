package com.example.demo.controllers;

import com.example.demo.entity.PatientData;
import com.example.demo.repos.PatientDataRepo;
import com.example.demo.repos.RoleRepo;
import com.example.demo.repos.UserRepo;
import com.example.demo.services.DoctorService;
import com.example.demo.services.PatientDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class DoctorController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    PatientDataRepo patientDataRepo;
    @Autowired
    DoctorService doctorService;

    @Autowired
    PatientDataService patientDataService;

    @PostMapping("/doctor/findPatients")
    public String findPatients(@RequestParam("id") int id) {
        String message = doctorService.findPatients(id);
        return "redirect:/doctor";
    }
    @PostMapping ("/doctor/establishDiagnosis")
    public String establishDiagnosis(@RequestParam("id") int id) {
        doctorService.establishDiagnosis(id);
        return "redirect:/doctor";
    }
    @PostMapping ("/doctor/makePrescriptions")
    public String makePrescriptions(@RequestParam("id") int id) {
        doctorService.makePrescriptions(id);
        return "redirect:/doctor";
    }
    @PostMapping ("/doctor/doPrescription")
    public String doPresctiption(@RequestParam("id") int prescriptionId, @RequestParam("patientId") int patientId) {
        doctorService.doPrescription(patientId,prescriptionId);
        return "redirect:/doctor";
    }
    @PostMapping ("/doctor/releasePatient")
    public String releasePatient( @RequestParam("patientId") int patientId) {
        doctorService.releasePatient(patientId);
        return "redirect:/doctor";
    }
}
