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


import java.sql.Time;
import java.time.LocalTime;
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

        // chaek the added schedule inside

//        LocalTime newStartTime = schedule.getScheduleStartTime().toLocalTime(); // e.g. 22:00
//        LocalTime newEndTime = newStartTime.plusMinutes(schedule.getHourCount()); // e.g. 01:00 (next day)
//
//       // Handle overnight case (if end time is before start time)
//        if (newEndTime.isBefore(newStartTime)) {
//            newEndTime = newEndTime.plusHours(24); // normalize to next day
//        }
//
//        List<ScheduleOfTime> previousSchedules = studentTimescheduleRepo.findByStudentDetails(findstudent);
//
//        for (ScheduleOfTime existingSchedule : previousSchedules) {
//            LocalTime existingStart = existingSchedule.getScheduleStartTime().toLocalTime();
//            LocalTime existingEnd = existingStart.plusMinutes(existingSchedule.getHourCount());
//
//            // Handle overnight case
//            if (existingEnd.isBefore(existingStart)) {
//                existingEnd = existingEnd.plusHours(24);
//            }
//
//            // Normalize comparison times to 24-hour span
//            LocalTime compareNewStart = newStartTime;
//            LocalTime compareNewEnd = newEndTime;
//            if (newEndTime.isBefore(newStartTime)) {
//                compareNewEnd = compareNewEnd.plusHours(24);
//            }
//
//            // Check if times overlap
//            boolean isOverlapping = !(compareNewEnd.isBefore(existingStart) || compareNewStart.isAfter(existingEnd));
//
//            if (isOverlapping) {
//                throw new RuntimeException("Schedule time overlaps with an existing schedule from "
//                        + existingStart + " to " + existingEnd);
//            }
//        }

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
                        schedule.getScheduleId(),
                        schedule.getScheduleDate(),
                        schedule.getScheduleStartTime(),
                        schedule.getHourCount(),
                        schedule.getScheduleTopic(),
                        schedule.getStudentDetails().getStudentDetailsId()
                ))
                .collect(Collectors.toList());
    }

    public ScheduleofTimeDTO updateTimeSchedule(ScheduleofTimeDTO schedule, String Email) {
        StudentDetails student = studentDetailsRepo.findByStudentEmail(Email).orElse(null);

        if(student == null){
            throw new IllegalStateException("Student details not found");
        }

        ScheduleOfTime findSchedule = studentTimescheduleRepo.findById(schedule.getScheduleId()).orElse(null);
        if(findSchedule == null){
            throw new IllegalStateException("Schedule not found");
        }
        findSchedule.setScheduleId(schedule.getScheduleId());
        findSchedule.setScheduleDate(schedule.getScheduleDate());
        findSchedule.setScheduleStartTime(schedule.getScheduleStartTime());
        findSchedule.setHourCount(schedule.getHourCount());
        findSchedule.setStudentDetails(student);

        studentTimescheduleRepo.save(findSchedule);
        return modelMapper.map(findSchedule, ScheduleofTimeDTO.class);

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
