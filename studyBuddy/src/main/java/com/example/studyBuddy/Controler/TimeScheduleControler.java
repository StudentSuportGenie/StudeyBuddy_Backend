package com.example.studyBuddy.Controler;

import com.example.studyBuddy.Config.CustomFilter.TokenDecodeServices;
import com.example.studyBuddy.DTO.ScheduleofTimeDTO;
import com.example.studyBuddy.Services.TimeScheduleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/API/V1/")
public class TimeScheduleControler {

   @Autowired
   private TimeScheduleServices timeScheduleServices;

   @Autowired
   private TokenDecodeServices tokenDecodeServices;

   @PostMapping("RadScheduler")
    public ResponseEntity<?> addTimeSchedule(@RequestHeader("Authorization") String authHeader, @RequestBody ScheduleofTimeDTO schedule) {
        String token = authHeader.replace("Bearer ", "").trim();
       try{
          List<String> studentEmail = tokenDecodeServices.getEmail(token);
           ScheduleofTimeDTO ScheduleofTimeDTO = timeScheduleServices.AddTimeSchedule(schedule,studentEmail.get(0));
           return ResponseEntity.ok(ScheduleofTimeDTO);
       } catch (Exception e) {
           Map<String, String> errorResponse = new HashMap<>();
           errorResponse.put("message", e.getMessage());
           return ResponseEntity.status(500).body(errorResponse);
       }
   }

   @GetMapping("getalldetails")
    public ResponseEntity<?> getAlldetails(@RequestHeader("Authorization") String authHeader) {
       String token = authHeader.replace("Bearer ", "").trim();
       try{
           List<String> studentEmail = tokenDecodeServices.getEmail(token);
           List<ScheduleofTimeDTO> getallSchedule = timeScheduleServices.GetTimeSchedule(studentEmail.get(0));
           return ResponseEntity.ok(getallSchedule);
       }catch (Exception e) {
           Map<String, String> errorResponse = new HashMap<>();
           errorResponse.put("message", e.getMessage());
           return ResponseEntity.status(500).body(e.getMessage());
       }
   }

   @PutMapping("UpdateDetails")
   public ResponseEntity<?> updateTimeSchedule(@RequestHeader("Authorization") String authHeader, @RequestBody ScheduleofTimeDTO schedule) {
       String token = authHeader.replace("Bearer ", "").trim();
       try{
           List<String> studentEmail = tokenDecodeServices.getEmail(token);
           ScheduleofTimeDTO updateSchedule = timeScheduleServices.updateTimeSchedule(schedule,studentEmail.get(0));
           return ResponseEntity.ok(updateSchedule);
       } catch (Exception e) {
           Map<String, String> errorResponse = new HashMap<>();
           errorResponse.put("message", e.getMessage());
           return ResponseEntity.status(500).body(e.getMessage());
       }
   }

   @DeleteMapping("deleteDetails")
    public ResponseEntity<?> deleteDetails(@RequestHeader("Authorization") String authHeader, @RequestParam Integer scheduleId) {
       String token = authHeader.replace("Bearer ", "").trim();
       try{
           List<String> studentEmail = tokenDecodeServices.getEmail(token);
           ScheduleofTimeDTO detete_Details = timeScheduleServices.deleteTimeSchedule(studentEmail.get(0), scheduleId);
           return ResponseEntity.ok(detete_Details);
       } catch (Exception e) {
           Map<String, String> errorResponse = new HashMap<>();
           errorResponse.put("message", e.getMessage());
           return ResponseEntity.status(500).body(e.getMessage());
       }
   }
}
