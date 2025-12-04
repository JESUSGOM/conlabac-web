package com.aliro5.conlabac.web.dto;

public class CentroDTO {
    private Integer id;
    private String denominacion;
    private String direccion;
    private Integer codigoPostal;
    private String provincia;
    private String telefono;
    private String fax;

    public CentroDTO() {
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDenominacion() { return denominacion; }
    public void setDenominacion(String denominacion) { this.denominacion = denominacion; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Integer getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(Integer codigoPostal) { this.codigoPostal = codigoPostal; }

    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getFax() { return fax; }
    public void setFax(String fax) { this.fax = fax; }
}