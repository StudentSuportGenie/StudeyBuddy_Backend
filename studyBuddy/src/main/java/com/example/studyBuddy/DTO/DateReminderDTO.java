package com.example.studyBuddy.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateReminderDTO {

    private int dateReminderId;
    private Date reminderDate;
    private String reminderTopic;
    private Time reminderTime = Time.valueOf("23:59:59");
    private int studentDetailsId;
}
