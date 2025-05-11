package com.example.studyBuddy.Controler;

import com.example.studyBuddy.Config.CustomFilter.TokenDecodeServices;
import com.example.studyBuddy.DTO.studentdetailsDTO;
import com.example.studyBuddy.Services.StudentDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/API/V1/")
public class StudentdetailsController {

    @Autowired
    private StudentDetailsServices studentDetailsServices;

    @Autowired
    private TokenDecodeServices tokenDecodeServices;

    @PostMapping("Addstudetails")
    public ResponseEntity<?> AddStudentDetails(@RequestBody studentdetailsDTO studentdetailsDTO) {
        try {

            studentdetailsDTO = studentDetailsServices.AddStudentDetails(studentdetailsDTO);
            return ResponseEntity.ok().body(studentdetailsDTO);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

    @GetMapping("/studentDetailUni")
    public ResponseEntity<?> getStudentDetailUni(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim(); // Ensure it's clean

        try {
            List<String> emails = tokenDecodeServices.getEmail(token);
            studentdetailsDTO getstudentdetails = studentDetailsServices.getstudentdetails(emails.get(0));
            return ResponseEntity.ok(getstudentdetails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token: " + e.getMessage());
        }
    }

}



