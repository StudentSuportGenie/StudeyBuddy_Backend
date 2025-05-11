package com.example.studyBuddy.Repo;

import com.example.studyBuddy.Models.DateReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataReminderRepo extends JpaRepository<DateReminder,Integer> {
}
