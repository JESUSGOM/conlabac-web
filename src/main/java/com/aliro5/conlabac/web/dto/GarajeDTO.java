package com.aliro5.conlabac.web.dto;

import java.time.LocalDate;

public class GarajeDTO {
    private Integer id;
    private Integer idCentro; // NUEVO: Campo necesario para vincular el vehículo al centro
    private String fechaTexto; // YYYYMMDD
    private LocalDate fecha;   // Fecha real

    private String nombreConductor;
    private String empresa;
    private String marca;
    private String modelo;
    private String color;
    private String matricula;

    public GarajeDTO() {}

    // --- GETTERS Y SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdCentro() { return idCentro; } // NUEVO
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; } // NUEVO

    public String getFechaTexto() { return fechaTexto; }
    public void setFechaTexto(String fechaTexto) { this.fechaTexto = fechaTexto; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getNombreConductor() { return nombreConductor; }
    public void setNombreConductor(String nombreConductor) { this.nombreConductor = nombreConductor; }

    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
}