package com.example.studyBuddy.Services;

import com.example.studyBuddy.DTO.DateReminderDTO;
import com.example.studyBuddy.Models.DateReminder;
import com.example.studyBuddy.Repo.DataReminderRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DateReminderService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DataReminderRepo dataReminderRepo;

    public DateReminderDTO AddDateReminder(DateReminderDTO dateReminderDTO) {
       DateReminder finddatereminder = dataReminderRepo.findByStudentDetails(dateReminderDTO.getStudentDetailsId()).orElse(null);

       if(finddatereminder.getReminderDate() == dateReminderDTO.getReminderDate() && finddatereminder.getReminderTime() == dateReminderDTO.getReminderTime() && finddatereminder.getStudentDetails ().getStudentDetailsId() ==  dateReminderDTO.getStudentDetailsId()){
           throw new IllegalStateException("Reminder already exists");
       }
       DateReminder newReminder = modelMapper.map(dateReminderDTO, DateReminder.class);
       dataReminderRepo.save(newReminder);
       return modelMapper.map(finddatereminder, DateReminderDTO.class);
    }

    public DateReminderDTO GetDateReminder(String email, Integer dateReminderId) {
        DateReminder findReminder = dataReminderRepo.findById(dateReminderId).orElse(null);

        if(email.equals(findReminder.getStudentDetails().getStudentEmail())){
            return modelMapper.map(findReminder, DateReminderDTO.class);
        }
         return null;
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
}
