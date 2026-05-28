package com.example.jobportal.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="jobs")


public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String skills;
    private String company;
    private String location;


    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    private User recruiter;
    
}

