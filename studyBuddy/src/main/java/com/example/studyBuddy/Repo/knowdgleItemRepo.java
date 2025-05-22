package com.example.studyBuddy.Repo;

import com.example.studyBuddy.Models.knowdgleItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface knowdgleItemRepo extends JpaRepository<knowdgleItems,Integer> {
    Optional<knowdgleItems> findByKnowdgleItemTitle(String title);
}
