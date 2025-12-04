package com.aliro5.conlabac.web.dto;

import java.time.LocalDateTime;

public class TelefonoDTO {
    private Integer id;
    private Integer idCentro;

    // Fechas Registro
    private String fecha;
    private String hora;
    private LocalDateTime fechaHoraRegistro;

    // Datos del Mensaje
    private String emisor;       // Quién llama
    private String destinatario; // Para quién
    private String mensaje;

    // Estado
    private Integer comunicado; // 0 = Pendiente, 1 = Comunicado

    // Fechas Entrega
    private String fechaEntrega;
    private String horaEntrega;
    private LocalDateTime fechaHoraEntrega;

    public TelefonoDTO() {}

    // --- Getters y Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }
    public LocalDateTime getFechaHoraRegistro() { return fechaHoraRegistro; }
    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) { this.fechaHoraRegistro = fechaHoraRegistro; }
    public String getEmisor() { return emisor; }
    public void setEmisor(String emisor) { this.emisor = emisor; }
    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public Integer getComunicado() { return comunicado; }
    public void setComunicado(Integer comunicado) { this.comunicado = comunicado; }
    public String getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(String fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    public String getHoraEntrega() { return horaEntrega; }
    public void setHoraEntrega(String horaEntrega) { this.horaEntrega = horaEntrega; }
    public LocalDateTime getFechaHoraEntrega() { return fechaHoraEntrega; }
    public void setFechaHoraEntrega(LocalDateTime fechaHoraEntrega) { this.fechaHoraEntrega = fechaHoraEntrega; }
}