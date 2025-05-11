package com.example.studyBuddy.Emailhandel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {
    private String Receiver;
    private String subject;
    private String body;
}
