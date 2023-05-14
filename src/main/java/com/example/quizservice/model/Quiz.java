package com.example.quizservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Bean;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;

@Data
@Table(name = "table_quiz")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String question;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> answerList;

    private int rightAnswer;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Transient
    private String status;

    @PostLoad
    @PostPersist
    @PostUpdate
    private void updateStatus() {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(startTime)) {
            this.status = "inactive";
        } else if (now.isAfter(endTime)) {
            this.status = "finished";
        } else {
            this.status = "active";
        }
    }
}
