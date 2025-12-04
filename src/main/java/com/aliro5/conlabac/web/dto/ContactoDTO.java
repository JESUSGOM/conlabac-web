package com.aliro5.conlabac.web.dto;

public class ContactoDTO {
    private Integer id;
    private Integer idCentro;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String email;

    public ContactoDTO() {}

    // --- GETTERS Y SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido1() { return apellido1; }
    public void setApellido1(String apellido1) { this.apellido1 = apellido1; }
    public String getApellido2() { return apellido2; }
    public void setApellido2(String apellido2) { this.apellido2 = apellido2; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}