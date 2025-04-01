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

    @Column(name = "main_image",columnDefinition = "TEXT")
    private String mainImage;

    @Column(name = "qr_code",columnDefinition = "TEXT")
    private String qrCode;

    @Column(name = "diagnosis", columnDefinition = "TEXT")
    private String diagnosis;

    @Column(name = "microscopic_dc",columnDefinition = "TEXT")
    private String microscopicDc;

    @Column(name ="ai_insights",columnDefinition = "TEXT")
    private String aiInsights;

    @Column(name = "specimen_type",nullable = false)
    private String specimenType;

    @Column(name = "collection_site", nullable = false)
    private String collectionSite;

    @Column(name = "clinical_data",nullable = false)
    private String clinicalData;

    @Column(name = "report_id")
    private String reportId;

}
