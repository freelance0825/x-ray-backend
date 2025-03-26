package com.freelance.fundoscope_backend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "patient")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_sequence")
    @SequenceGenerator(name = "patient_sequence", sequenceName = "patient_sequence", allocationSize = 1, initialValue = 1000)
    private Long id;

    private String name;

    private String age;

    private String gender;

    private String address;

    private String email;

    private String state;

    private String dateOfBirth;

    private String phoneNumber;

    @Column(columnDefinition = "TEXT")
    private String imageBase64;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<CaseRecordEntity> caseRecords;

}
