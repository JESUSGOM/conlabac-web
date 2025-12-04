package com.aliro5.conlabac.web.dto;

import java.time.LocalDateTime;

public class IncidenciaDTO {
    private Integer id;
    private Integer idCentro;

    private String fecha;
    private String hora;
    private LocalDateTime fechaHora;

    private String texto;
    private String usuario; // Vigilante

    // Estos campos los rellenará la API automáticamente,
    // pero los mantenemos aquí por si la API nos devuelve el dato guardado.
    private String comunicadoA;
    private String modoComunica;
    private String emailComunica;

    public IncidenciaDTO() {}

    // --- Getters y Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getComunicadoA() { return comunicadoA; }
    public void setComunicadoA(String comunicadoA) { this.comunicadoA = comunicadoA; }

    public String getModoComunica() { return modoComunica; }
    public void setModoComunica(String modoComunica) { this.modoComunica = modoComunica; }

    public String getEmailComunica() { return emailComunica; }
    public void setEmailComunica(String emailComunica) { this.emailComunica = emailComunica; }
}