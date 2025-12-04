package com.aliro5.conlabac.web.dto;

public class KeyMoveDTO {
    private Integer id;
    private String codigoLlave; // El código de la llave (Ej: K-101)
    private Integer idCentro;

    // Datos Entrega
    private String fechaEntrega;
    private String horaEntrega;

    // Datos Persona
    private String nombre;
    private String apellido1;
    private String apellido2;

    // Datos Devolución
    private String fechaDevolucion;
    private String horaDevolucion;

    public KeyMoveDTO() {
    }

    // --- GETTERS Y SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCodigoLlave() { return codigoLlave; }
    public void setCodigoLlave(String codigoLlave) { this.codigoLlave = codigoLlave; }

    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }

    public String getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(String fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public String getHoraEntrega() { return horaEntrega; }
    public void setHoraEntrega(String horaEntrega) { this.horaEntrega = horaEntrega; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido1() { return apellido1; }
    public void setApellido1(String apellido1) { this.apellido1 = apellido1; }

    public String getApellido2() { return apellido2; }
    public void setApellido2(String apellido2) { this.apellido2 = apellido2; }

    public String getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(String fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    public String getHoraDevolucion() { return horaDevolucion; }
    public void setHoraDevolucion(String horaDevolucion) { this.horaDevolucion = horaDevolucion; }
}