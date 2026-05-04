package com.tuempresa.gestoracademia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(
    name = "matricula",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_alumno", "id_curso"})
)
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matricula")
    private Long idMatricula;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_alumno", nullable = false)
    @JsonIgnoreProperties({"matriculas", "asistencias", "pagos"})
    private Alumno alumno;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_curso", nullable = false)
    @JsonIgnoreProperties({"matriculas", "asistencias", "pagos"})
    private Curso curso;

    @Column(name = "fecha_matricula")
    private LocalDate fechaMatricula;

    @Column(length = 20)
    private String estado;

    public Matricula() {
    }

    public Long getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(Long idMatricula) {
        this.idMatricula = idMatricula;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public LocalDate getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(LocalDate fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}