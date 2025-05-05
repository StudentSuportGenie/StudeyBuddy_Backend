package com.example.studyBuddy.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class knowdgleItems {

    private enum ItemType {
        video,
        audio,
        PDF,
    }

    @Id
    private int knowdgleItemId;
    private String knowdgleItemTitle;
    private String knowdgleItemDescription;
    private String knowdgleitemLink;
    private ItemType knowdgleItemtype;
    private String AddedEmail;
}
