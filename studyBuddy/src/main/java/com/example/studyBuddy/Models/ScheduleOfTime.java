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
public class ScheduleOfTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int scheduleId;

    private Date scheduleDate;
    private Time scheduleStartTime;
    private int hourCount;

    @ManyToOne
    @JoinColumn(name = "studentDetails_Id", referencedColumnName = "studentDetailsId", nullable = false)
    private StudentDetails studentDetails;
}



