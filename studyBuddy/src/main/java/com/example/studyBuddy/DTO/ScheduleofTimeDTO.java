package com.example.studyBuddy.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleofTimeDTO {

    private Integer scheduleId;
    private Date scheduleDate;
    private Time scheduleStartTime;
    private int hourCount;
    private String scheduleTopic;
    private int studentDetailsId;
}
