package com.example.studyBuddy.Repo;

import com.example.studyBuddy.Models.StudentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentDetailsRepo extends JpaRepository<StudentDetails, Integer> {
    Optional<StudentDetails> findByStudentEmail(String studentEmail);
}
