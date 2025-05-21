package com.example.studyBuddy.Repo;

import com.example.studyBuddy.Models.DateReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataReminderRepo extends JpaRepository<DateReminder,Integer> {
    Optional<DateReminder> findByStudentDetails(int student);
}
