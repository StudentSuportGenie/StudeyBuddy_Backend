package com.example.studyBuddy.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class studentdetailsDTO {

    private int studentDetailsId;
    private String studentProfile;
    private String studentGender;
    private String studentEmail;
    private Date studentBirthday;
}
