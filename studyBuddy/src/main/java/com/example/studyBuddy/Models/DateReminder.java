package com.example.studyBuddy.Models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DateReminder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dateReminderId;
    private String reminderTopic;
    private Date reminderDate;
    private Time reminderTime = Time.valueOf("23:59:59");

    @ManyToOne
    @JoinColumn(name = "studentDetails_Id", referencedColumnName = "studentDetailsId", nullable = false)
    private StudentDetails studentDetails;
}