package com.example.studyBuddy.Services;

import com.example.studyBuddy.DTO.DateReminderDTO;
import com.example.studyBuddy.Models.DateReminder;
import com.example.studyBuddy.Models.StudentDetails;
import com.example.studyBuddy.Repo.DataReminderRepo;
import com.example.studyBuddy.Repo.StudentDetailsRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class DateReminderService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DataReminderRepo dataReminderRepo;

    @Autowired
    private StudentDetailsRepo studentDetailsRepo;

    public DateReminderDTO AddDateReminder(DateReminderDTO dateReminderDTO) {
        int studentDetailsId = dateReminderDTO.getStudentDetailsId();
        Date inputDate = dateReminderDTO.getReminderDate();
        Time inputTime = dateReminderDTO.getReminderTime();

        // Convert input date and time to LocalDate and LocalTime
        LocalDate inputLocalDate = inputDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalTime inputLocalTime = inputTime.toLocalTime().withNano(0); // Normalize time

        // Fetch existing reminders for the student
        List<DateReminder> existingReminders = dataReminderRepo.findByStudentDetails_StudentDetailsId(studentDetailsId);

        // Check for duplicate reminder (same date and time)
        boolean duplicateExists = existingReminders.stream()
                .anyMatch(reminder -> {
                    if (reminder.getReminderDate() == null || reminder.getReminderTime() == null) return false;

                    LocalDate existingDate = reminder.getReminderDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    LocalTime existingTime = reminder.getReminderTime().toLocalTime().withNano(0); // Normalize time

                    return existingDate.equals(inputLocalDate) && existingTime.equals(inputLocalTime);
                });

        if (duplicateExists) {
            throw new IllegalStateException("Reminder already exists for this date and time.");
        }

        // Map DTO to entity and save new reminder
        DateReminder newReminder = modelMapper.map(dateReminderDTO, DateReminder.class);
        DateReminder savedReminder = dataReminderRepo.save(newReminder);

        // Map saved entity back to DTO and return
        return modelMapper.map(savedReminder, DateReminderDTO.class);
    }



    public List<DateReminderDTO> GetDateReminder(String email) {
         StudentDetails findStudentID = studentDetailsRepo.findByStudentEmail(email).orElse(null);

         if (findStudentID == null) {
             throw new IllegalStateException("Student doesn't exist.");
         }

        List<DateReminder> find_unique_details = dataReminderRepo.findByStudentDetails_StudentDetailsId(findStudentID.getStudentDetailsId());

         if (find_unique_details.isEmpty()) {
             throw new IllegalStateException("Date reminder doesn't exist.");
         }
        Type listType = new TypeToken<List<DateReminderDTO>>() {}.getType();
        return modelMapper.map(find_unique_details, listType);
    }

    public DateReminderDTO deleteDateReminder(String email, Integer dateReminderId) {
        DateReminder reminder = dataReminderRepo.findById(dateReminderId).orElse(null);
        if(email.equals(reminder.getStudentDetails().getStudentEmail())){
            dataReminderRepo.delete(reminder);
        }
        return modelMapper.map(reminder, DateReminderDTO.class);
    }

    public DateReminderDTO updateDateReminder(DateReminderDTO dateReminderDTO) {
        DateReminder updateReminder = dataReminderRepo.findById(dateReminderDTO.getDateReminderId()).orElse(null);
        if(updateReminder.getDateReminderId() == dateReminderDTO.getDateReminderId()){
            updateReminder.setReminderDate(dateReminderDTO.getReminderDate());
            updateReminder.setReminderTime(dateReminderDTO.getReminderTime());

            dataReminderRepo.save(updateReminder);
            return modelMapper.map(updateReminder, DateReminderDTO.class);
        }
        return null;
    }

    public List<DateReminderDTO> getAllDateReminders() {
        List<DateReminder> dateReminders = dataReminderRepo.findAll();
        List<DateReminderDTO> dateReminderDTOs = new ArrayList<>();
        return dateReminderDTOs;
    }
}
