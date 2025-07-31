package com.example.studyBuddy.Models;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int knowdgleItemId;
    private String knowdgleItemTitle;
    private String knowdgleItemDescription;
    private String knowdgleitemLink;

    @Enumerated(EnumType.STRING)
    private ItemType knowdgleItemtype;
    private String addedEmail;
}
