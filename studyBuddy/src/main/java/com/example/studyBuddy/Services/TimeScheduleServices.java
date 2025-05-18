package com.example.studyBuddy.Services;

import com.example.studyBuddy.DTO.ScheduleofTimeDTO;
import com.example.studyBuddy.Models.ScheduleOfTime;
import com.example.studyBuddy.Models.StudentDetails;
import com.example.studyBuddy.Repo.StudentDetailsRepo;
import com.example.studyBuddy.Repo.StudentTimescheduleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        ScheduleOfTime scheduleOfTime = modelMapper.map(schedule, ScheduleOfTime.class);
        studentTimescheduleRepo.save(scheduleOfTime);
        return "Success Fully Saved Your Schedule";
    }
}
