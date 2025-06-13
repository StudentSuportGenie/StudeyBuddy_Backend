package com.example.studyBuddy.Repo;

import com.example.studyBuddy.Models.ScheduleOfTime;
import com.example.studyBuddy.Models.StudentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentTimescheduleRepo extends JpaRepository<ScheduleOfTime , Integer> {

    List<ScheduleOfTime> findByStudentDetails(StudentDetails student);
    List<ScheduleOfTime> findByStudentDetails_StudentDetailsId(int studentDetailsId);
}
