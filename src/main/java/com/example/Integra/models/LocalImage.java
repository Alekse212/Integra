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
@Table(name = "LocalImage")
public class LocalImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_id")
    private Local local;
    @Column(unique = true)
    private String nameImage;

    public LocalImage(Local local, String nameImage){
        this.local = local;
        this.nameImage = nameImage;
    }
}
