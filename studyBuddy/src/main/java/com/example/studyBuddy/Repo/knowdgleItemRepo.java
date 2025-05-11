package com.example.studyBuddy.Repo;

import com.example.studyBuddy.Models.knowdgleItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface knowdgleItemRepo extends JpaRepository<knowdgleItems,Integer> {
}
