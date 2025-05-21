package com.example.studyBuddy.Services;

import com.example.studyBuddy.Config.CustomFilter.TokenDecodeServices;
import com.example.studyBuddy.DTO.ScheduleofTimeDTO;
import com.example.studyBuddy.Models.ScheduleOfTime;
import com.example.studyBuddy.Models.StudentDetails;
import com.example.studyBuddy.Repo.StudentDetailsRepo;
import com.example.studyBuddy.Repo.StudentTimescheduleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeScheduleServices {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StudentTimescheduleRepo studentTimescheduleRepo;

    @Autowired
    private StudentDetailsRepo studentDetailsRepo;

    public String AddTimeSchedule(ScheduleofTimeDTO schedule,String Email) {
        StudentDetails findstudent = studentDetailsRepo.findByStudentEmail(Email).orElse(null);
        assert findstudent != null;
        if(findstudent.getStudentDetailsId() != schedule.getStudentDetailsId()){
            throw new IllegalStateException("Student details not found");
        }
        if(findstudent.getScheduleOfTime() ==  schedule.getScheduleStartTime() && findstudent.getStudentEmail().equals(Email)){
            throw new IllegalStateException("Schedule of time already exists");
        }
        ScheduleOfTime scheduleOfTime = modelMapper.map(schedule, ScheduleOfTime.class);
        studentTimescheduleRepo.save(scheduleOfTime);
        return "Success Fully Saved Your Schedule";
    }

    public List<ScheduleofTimeDTO> GetTimeSchedule(String email) {
        StudentDetails student = studentDetailsRepo.findByStudentEmail(email)
                .orElseThrow(() -> new IllegalStateException("Student details not found"));

        // Directly fetch schedules related to this student
        List<ScheduleOfTime> schedules = studentTimescheduleRepo.findByStudentDetails(student);

        // Map to DTOs
        return schedules.stream()
                .map(schedule -> new ScheduleofTimeDTO(
                        schedule.getScheduleDate(),
                        schedule.getScheduleStartTime(),
                        schedule.getHourCount(),
                        schedule.getStudentDetails().getStudentDetailsId()
                ))
                .collect(Collectors.toList());
    }

    public ScheduleofTimeDTO deleteTimeSchedule(String email,Integer scheduleId) {
        ScheduleOfTime findData = studentTimescheduleRepo.findById(scheduleId).orElse(null);

        if(findData == null){
            throw new IllegalStateException("Schedule details not found");
        }
        StudentDetails student = studentDetailsRepo.findByStudentEmail(email).orElse(null);
        if(student == null){
            throw new IllegalStateException("Student details not found");
        }
        if(findData.getStudentDetails().getStudentEmail().equals(email)){
            studentTimescheduleRepo.delete(findData);
            return modelMapper.map(findData, ScheduleofTimeDTO.class);
        }
        throw new IllegalStateException("Student details not found");
    }

}
