package com.example.studyBuddy.Repo;

import com.example.studyBuddy.Models.DateReminder;
import com.example.studyBuddy.Models.StudentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DataReminderRepo extends JpaRepository<DateReminder,Integer> {
     List<DateReminder> findByStudentDetails_StudentDetailsId(int studentDetailsId);

}
