package com.example.studyBuddy.Controler;

import com.example.studyBuddy.Config.CustomFilter.TokenDecodeServices;
import com.example.studyBuddy.DTO.DateReminderDTO;
import com.example.studyBuddy.Services.DateReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/API/V1/")
public class DateReminderControler {

    @Autowired
    private DateReminderService dateReminderService;

    @Autowired
    private TokenDecodeServices tokenDecodeServices;

    @PostMapping("addReminder")
    public ResponseEntity<?> addDateReminder(@RequestBody DateReminderDTO dateReminderDTO) {
        try{
            DateReminderDTO addeddate = dateReminderService.AddDateReminder(dateReminderDTO);
            return ResponseEntity.ok().body(addeddate);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("getReminder")
    public ResponseEntity<?> getDateReminders(@RequestHeader("Authorization") String authHeader) {
         String token = authHeader.replace("Bearer ", "").trim();
        try{
            List<String> userEmail = tokenDecodeServices.getEmail(token);
            List < DateReminderDTO> getdatareminder = dateReminderService.GetDateReminder(userEmail.get(0));
            return ResponseEntity.ok().body(getdatareminder);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("deleteReminder")
    public ResponseEntity<?> deleteDateReminder(@RequestHeader("Authorization") String authHeader,@RequestParam int dateReminderId) {
        String token = authHeader.replace("Bearer ", "").trim();
        try{
            List<String> userEmail = tokenDecodeServices.getEmail(token);
            DateReminderDTO DeleteReminder = dateReminderService.deleteDateReminder(userEmail.get(0), dateReminderId);
            return ResponseEntity.ok().body(DeleteReminder);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("updateReminder")
    public ResponseEntity<?> updateDateReminder(@RequestBody DateReminderDTO dateReminderDTO) {
        try{
            DateReminderDTO updateReminder = dateReminderService.updateDateReminder(dateReminderDTO);
            return ResponseEntity.ok().body(updateReminder);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("AllDatareminders")
    public ResponseEntity<?> getAllDateReminders(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        try{
            String role = tokenDecodeServices.getJobTitle(token);
            if(role.equals("Admin")) {
                return ResponseEntity.ok().body(dateReminderService.getAllDateReminders());
            }
            return null;
        }catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
