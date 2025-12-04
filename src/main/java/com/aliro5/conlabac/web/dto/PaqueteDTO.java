package com.aliro5.conlabac.web.dto;

import java.time.LocalDateTime;

public class PaqueteDTO {
    private Integer id;
    private Integer idCentro;

    // Fechas
    private String fecha;
    private String hora;
    private LocalDateTime fechaHoraRecepcion;

    // Datos Paquete
    private String emisor;       // Amazon, Seur...
    private String destinatario; // Para quién es
    private String mensajeria;   // Empresa transporte
    private Integer bultos;
    private String tipo;         // Caja, Sobre...

    // Estado
    private String comunicado;   // "SI" (Entregado) o "NO" (Pendiente)
    private String operario;     // Vigilante que recepciona

    public PaqueteDTO() {}

    // --- Getters y Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }
    public LocalDateTime getFechaHoraRecepcion() { return fechaHoraRecepcion; }
    public void setFechaHoraRecepcion(LocalDateTime fechaHoraRecepcion) { this.fechaHoraRecepcion = fechaHoraRecepcion; }
    public String getEmisor() { return emisor; }
    public void setEmisor(String emisor) { this.emisor = emisor; }
    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
    public String getMensajeria() { return mensajeria; }
    public void setMensajeria(String mensajeria) { this.mensajeria = mensajeria; }
    public Integer getBultos() { return bultos; }
    public void setBultos(Integer bultos) { this.bultos = bultos; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getComunicado() { return comunicado; }
    public void setComunicado(String comunicado) { this.comunicado = comunicado; }
    public String getOperario() { return operario; }
    public void setOperario(String operario) { this.operario = operario; }
}