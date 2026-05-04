package com.tuempresa.gestoracademia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(
    name = "asistencia",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_alumno", "id_curso", "fecha"})
)
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asistencia")
    private Long idAsistencia;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_alumno", nullable = false)
    @JsonIgnoreProperties({"matriculas", "asistencias", "pagos"})
    private Alumno alumno;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso", nullable = false)
    @JsonIgnoreProperties({"matriculas", "asistencias", "pagos"})
    private Curso curso;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private boolean presente;

    public Asistencia() {}

    // getters y setters
    // ...
}