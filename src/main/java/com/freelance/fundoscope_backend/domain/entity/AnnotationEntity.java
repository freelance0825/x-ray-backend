package com.freelance.fundoscope_backend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "annotation")
public class AnnotationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "annotation_sequence")
    @SequenceGenerator(name = "annotation_sequence", sequenceName = "annotation_sequence", allocationSize = 1, initialValue = 1000)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "slides_id", nullable = false)
    private SlideEntity slide;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private String time;

    @Column(nullable = false)
    private String year;

    @Column(name = "image_base64", columnDefinition = "TEXT")
    private String imageBase64;

    @Column(columnDefinition = "TEXT")
    private String coordinates;

}
