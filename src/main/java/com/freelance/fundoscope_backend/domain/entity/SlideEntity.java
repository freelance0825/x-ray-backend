package com.freelance.fundoscope_backend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "slide")
public class SlideEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "slide_sequence")
    @SequenceGenerator(name = "slide_sequence", sequenceName = "slide_sequence", allocationSize = 1, initialValue = 1000)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "case_record_id", nullable = false)
    private CaseRecordEntity caseRecord;

    @Column(columnDefinition = "TEXT")
    private String mainImage;

    @Column(columnDefinition = "TEXT")
    private String qrCode;

    @Column(columnDefinition = "TEXT")
    private String diagnosis;

    @Column(columnDefinition = "TEXT")
    private String aiInsights;

    @Column(nullable = false)
    private String specimenType;

    @Column(nullable = false)
    private String collectionSite;

    @Column(nullable = false)
    private String clinicalData;

    private String reportId;

}
