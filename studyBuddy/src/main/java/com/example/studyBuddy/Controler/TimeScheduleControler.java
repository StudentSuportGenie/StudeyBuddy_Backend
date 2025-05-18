package com.example.studyBuddy.Controler;

import com.example.studyBuddy.Config.CustomFilter.TokenDecodeServices;
import com.example.studyBuddy.DTO.ScheduleofTimeDTO;
import com.example.studyBuddy.Services.TimeScheduleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/API/V1/")
public class TimeScheduleControler {

   @Autowired
   private TimeScheduleServices timeScheduleServices;

   @Autowired
   private TokenDecodeServices tokenDecodeServices;

   @PostMapping("Addtimeschedule")
    public ResponseEntity<?> addTimeSchedule(@RequestHeader("Authorization") String authHeader, @RequestBody ScheduleofTimeDTO schedule) {
        String token = authHeader.replace("Bearer ", "").trim();
       try{
          List<String> studentEmail = tokenDecodeServices.getEmail(token);
           String ScheduleofTimeDTO = timeScheduleServices.AddTimeSchedule(schedule,studentEmail.get(0));
           return ResponseEntity.ok(ScheduleofTimeDTO);
       } catch (Exception e) {
           return ResponseEntity.status(500).body(e.getMessage());
       }
   }
}
