package com.freelance.fundoscope_backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "doctor", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_sequence")
    @SequenceGenerator(name = "doctor_sequence", sequenceName = "doctor_sequence", allocationSize = 1, initialValue = 1000)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String name;

    @Column(nullable = false)
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String specialist;

    @Column(name = "birth_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private String birthDate;

    @Column(columnDefinition = "TEXT")
    private String signature;

}
