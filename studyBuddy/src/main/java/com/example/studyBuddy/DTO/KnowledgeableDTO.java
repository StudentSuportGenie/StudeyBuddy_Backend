package com.example.studyBuddy.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KnowledgeableDTO {
    private int knowdgleItemId;
    private String knowdgleItemTitle;
    private String knowdgleItemDescription;
    private String knowdgleitemLink;
    private String knowdgleItemtype;
    private String AddedEmail;
}
