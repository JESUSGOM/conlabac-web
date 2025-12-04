package com.aliro5.conlabac.web.dto;

import java.time.LocalDateTime;

public class EntreTurnoDTO {
    private Integer id;
    private Integer idCentro;

    // Escritor
    private String operarioEscritor;
    private String nombreEscritorMostrar; // <--- NUEVO CAMPO
    private String fechaEscrito;
    private String horaEscrito;
    private LocalDateTime fechaHoraEscrito;
    private String texto;

    // Lector
    private String usuarioLector;
    private String nombreCompletoMostrar;
    private String fechaLeido;
    private String horaLeido;
    private LocalDateTime fechaHoraLeido;

    public EntreTurnoDTO() {}

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }

    public String getOperarioEscritor() { return operarioEscritor; }
    public void setOperarioEscritor(String operarioEscritor) { this.operarioEscritor = operarioEscritor; }
    public String getNombreEscritorMostrar() { return nombreEscritorMostrar; }
    public void setNombreEscritorMostrar(String nombreEscritorMostrar) { this.nombreEscritorMostrar = nombreEscritorMostrar; }

    public String getFechaEscrito() { return fechaEscrito; }
    public void setFechaEscrito(String fechaEscrito) { this.fechaEscrito = fechaEscrito; }
    public String getHoraEscrito() { return horaEscrito; }
    public void setHoraEscrito(String horaEscrito) { this.horaEscrito = horaEscrito; }
    public LocalDateTime getFechaHoraEscrito() { return fechaHoraEscrito; }
    public void setFechaHoraEscrito(LocalDateTime fechaHoraEscrito) { this.fechaHoraEscrito = fechaHoraEscrito; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public String getUsuarioLector() { return usuarioLector; }
    public void setUsuarioLector(String usuarioLector) { this.usuarioLector = usuarioLector; }
    public String getNombreCompletoMostrar() { return nombreCompletoMostrar; }
    public void setNombreCompletoMostrar(String nombreCompletoMostrar) { this.nombreCompletoMostrar = nombreCompletoMostrar; }

    public String getFechaLeido() { return fechaLeido; }
    public void setFechaLeido(String fechaLeido) { this.fechaLeido = fechaLeido; }
    public String getHoraLeido() { return horaLeido; }
    public void setHoraLeido(String horaLeido) { this.horaLeido = horaLeido; }
    public LocalDateTime getFechaHoraLeido() { return fechaHoraLeido; }
    public void setFechaHoraLeido(LocalDateTime fechaHoraLeido) { this.fechaHoraLeido = fechaHoraLeido; }
}