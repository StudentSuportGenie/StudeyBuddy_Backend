package com.example.studyBuddy.Repo;

import com.example.studyBuddy.Models.ScheduleOfTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentTimescheduleRepo extends JpaRepository<ScheduleOfTime , Integer> {

}
