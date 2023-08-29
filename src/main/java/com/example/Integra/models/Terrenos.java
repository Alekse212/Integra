package com.example.Integra.models;


import com.example.Integra.models.enumeration.Urbanizable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Terrenos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Terrenos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ref;
    private String title;
    private Date date;
    @Column(name = "descripcion", columnDefinition = "LONGTEXT")
    private String descripcion;
    private Integer precio;
    private String cuidad;
    private Integer tamano;
    private Integer ibi;
    private Urbanizable urbanizable;
    private String previewImage;
    private Integer introduce;
    private Boolean visibility;
}
