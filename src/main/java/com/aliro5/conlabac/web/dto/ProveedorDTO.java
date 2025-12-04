package com.aliro5.conlabac.web.dto;

import java.time.LocalDate;

public class ProveedorDTO {
    private String cif;
    private Integer idCentro;
    private String denominacion; // Nombre Empresa
    private String contacto;
    private String telefono;
    private String email;
    private String provincia;
    private LocalDate fechaAlta;
    private LocalDate fechaExpiracion;

    public ProveedorDTO() {}

    // --- GETTERS Y SETTERS ---
    public String getCif() { return cif; }
    public void setCif(String cif) { this.cif = cif; }
    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }
    public String getDenominacion() { return denominacion; }
    public void setDenominacion(String denominacion) { this.denominacion = denominacion; }
    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }
    public LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }
    public LocalDate getFechaExpiracion() { return fechaExpiracion; }
    public void setFechaExpiracion(LocalDate fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }
}