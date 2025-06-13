package com.example.studyBuddy.Services;


import com.example.studyBuddy.DTO.ScheduleofTimeDTO;
import com.example.studyBuddy.Models.ScheduleOfTime;
import com.example.studyBuddy.Models.StudentDetails;
import com.example.studyBuddy.Repo.StudentDetailsRepo;
import com.example.studyBuddy.Repo.StudentTimescheduleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
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


    public ScheduleofTimeDTO AddTimeSchedule(ScheduleofTimeDTO scheduleDTO, String email) {

        StudentDetails student = studentDetailsRepo.findByStudentEmail(email)
                .orElseThrow(() -> new IllegalStateException("Student not found"));

        // 2. Convert date & time
        LocalDate inputDate = scheduleDTO.getScheduleDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
        LocalTime inputTime = scheduleDTO.getScheduleStartTime().toLocalTime().withNano(0);

        // 3. Check for duplicates
        List<ScheduleOfTime> existingSchedules = studentTimescheduleRepo.findByStudentDetails_StudentDetailsId(student.getStudentDetailsId());

        boolean isDuplicate = existingSchedules.stream().anyMatch(existing -> {
            if (existing.getScheduleDate() == null || existing.getScheduleStartTime() == null) return false;

            LocalDate existingDate = existing.getScheduleDate().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();
            LocalTime existingTime = existing.getScheduleStartTime().toLocalTime().withNano(0);

            return existingDate.equals(inputDate) && existingTime.equals(inputTime);
        });

        if (isDuplicate) {
            throw new IllegalStateException("Schedule already exists for this date and time");
        }

        LocalTime endTime = inputTime.plusHours(scheduleDTO.getHourCount());

        boolean isOverlapping = existingSchedules.stream()
                .anyMatch(existing -> {
                    if (existing.getScheduleDate() == null || existing.getScheduleStartTime() == null) return false;

                    LocalDate existingDate = existing.getScheduleDate().toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDate();

                    if (!existingDate.equals(inputDate)) return false;

                    LocalTime existingStart = existing.getScheduleStartTime().toLocalTime().withNano(0);
                    LocalTime existingEnd = existingStart.plusHours(existing.getHourCount());

                    // Check for overlap
                    return (inputTime.isBefore(existingEnd) && endTime.isAfter(existingStart));
                });

        if (isOverlapping) {
            throw new IllegalStateException("Schedule overlaps with an existing schedule on the same date");
        }

        // 4. Convert DTO to entity
        ScheduleOfTime newSchedule = new ScheduleOfTime();
        newSchedule.setScheduleDate(scheduleDTO.getScheduleDate());
        newSchedule.setScheduleStartTime(scheduleDTO.getScheduleStartTime());
        newSchedule.setHourCount(scheduleDTO.getHourCount());
        newSchedule.setScheduleTopic(scheduleDTO.getScheduleTopic());
        newSchedule.setStudentDetails(student);

        // 5. Save and convert back to DTO
        ScheduleOfTime saved = studentTimescheduleRepo.save(newSchedule);

        // Optional: if you’re not using ModelMapper
        ScheduleofTimeDTO resultDTO = new ScheduleofTimeDTO();
        resultDTO.setScheduleId(saved.getScheduleId());
        resultDTO.setScheduleDate(saved.getScheduleDate());
        resultDTO.setScheduleStartTime(saved.getScheduleStartTime());
        resultDTO.setHourCount(saved.getHourCount());
        resultDTO.setScheduleTopic(saved.getScheduleTopic());
        resultDTO.setStudentDetailsId(saved.getStudentDetails().getStudentDetailsId());

        return resultDTO;
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
