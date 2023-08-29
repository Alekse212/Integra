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
@Table(name = "TerrenosImage")
public class TerrenosImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "terrenos_id")
    private Terrenos terrenos;
    @Column(unique = true)
    private String nameImage;

    public TerrenosImage(Terrenos terrenos, String nameImage){
        this.terrenos = terrenos;
        this.nameImage = nameImage;
    }
}
