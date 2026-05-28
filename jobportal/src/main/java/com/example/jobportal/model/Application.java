package com.example.jobportal.model;



import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private User candidate;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    private String status;
} 