package com.example.Integra.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "servicio")
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Servicios {
    @Id
    @GeneratedValue
    private Long id;
    private String nombre;
    @Column(name = "descripcion", columnDefinition = "LONGTEXT")
    private String Descripcion;
    private Integer precio;
}
