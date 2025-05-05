package com.example.studyBuddy.Models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StudentDetails {

    public enum Gender {
        MALE, FEMALE
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int studentDetailsId;
    private String studentProfile;
    private Gender studentGender;
    private String studentEmail;
    private Date studentBirthday;

    @OneToMany(mappedBy = "studentDetails", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ScheduleOfTime> scheduleOfTime;

    @OneToMany(mappedBy = "studentDetails" , cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private List<DateReminder> dateReminders;

}