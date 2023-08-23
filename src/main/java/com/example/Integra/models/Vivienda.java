package com.example.Integra.models;


import com.example.Integra.models.enumeration.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Viviendas")
public class Vivienda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "descripcion", columnDefinition = "LONGTEXT")
    private String descripcion;
    private Integer precio;
    private String cuidad;
    private Integer tamano;
    private Integer ano;
    private Integer bano;
    private Integer habitaciones;
    private Opcion opcion;
    private Typee type;
    private Estado estado;
    private List<ExtraOpc> extraOpcs;
    private Integer ibi;
    private Integer comunidad;
    private String previewImage;
    private List<Extras> extras;
    private Date fechadeintroduccion;
    private List<String> imageViviendas;
    private Integer introduce;
    private Orientacion orientacion;
    private Coloborador coloborador;
}
