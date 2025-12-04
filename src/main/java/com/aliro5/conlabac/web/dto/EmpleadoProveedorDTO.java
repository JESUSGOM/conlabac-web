package com.aliro5.conlabac.web.dto;

import java.time.LocalDate;

public class EmpleadoProveedorDTO {
    private Integer id;
    private String cifProveedor; // Enlace con la empresa
    private Integer idCentro;

    private String nif;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String cargo;

    // Fechas de validez de acceso
    private LocalDate fechaAcceso;    // Inicio
    private LocalDate fechaFinAcceso; // Fin

    public EmpleadoProveedorDTO() {}

    // --- GETTERS Y SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getCifProveedor() { return cifProveedor; }
    public void setCifProveedor(String cifProveedor) { this.cifProveedor = cifProveedor; }
    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }
    public String getNif() { return nif; }
    public void setNif(String nif) { this.nif = nif; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido1() { return apellido1; }
    public void setApellido1(String apellido1) { this.apellido1 = apellido1; }
    public String getApellido2() { return apellido2; }
    public void setApellido2(String apellido2) { this.apellido2 = apellido2; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public LocalDate getFechaAcceso() { return fechaAcceso; }
    public void setFechaAcceso(LocalDate fechaAcceso) { this.fechaAcceso = fechaAcceso; }
    public LocalDate getFechaFinAcceso() { return fechaFinAcceso; }
    public void setFechaFinAcceso(LocalDate fechaFinAcceso) { this.fechaFinAcceso = fechaFinAcceso; }
}