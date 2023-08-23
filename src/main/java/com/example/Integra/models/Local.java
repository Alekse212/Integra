package com.example.Integra.models;


import com.example.Integra.models.enumeration.Estado;
import com.example.Integra.models.enumeration.Opcion;
import com.example.Integra.models.enumeration.TipoLocal;
import com.example.Integra.models.enumeration.Typee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "locales")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @Column(name = "descripcion", columnDefinition = "LONGTEXT")
    private String description;
    private Integer precio;
    private Integer tamano;
    private String cuidad;
    private String plano;
    private Opcion opcion;
    private String previewImage;
    private Date fechadeintroduccion;
    private TipoLocal tipo;
    private List<String> imageViviendas;
    private Integer bano;
    private Integer introduce;
}
