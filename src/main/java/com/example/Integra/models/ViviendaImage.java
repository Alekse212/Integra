package com.example.Integra.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ViviendasImage")
public class ViviendaImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vivienda_id")
    private Vivienda vivienda;
    @Column(unique = true)
    private String nameImage;

    public ViviendaImage(Vivienda vivienda, String nameImage){
        this.vivienda = vivienda;
        this.nameImage = nameImage;
    }
}
