package com.aliro5.conlabac.web.dto;

import java.io.Serializable;

public class EmailRequestDTO implements Serializable {
    private String destinatario;
    private String asunto;
    private String mensaje;
    private Integer idCentro;
    private String dniEmisor;

    public EmailRequestDTO() {}

    // Getters y Setters
    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }
    public String getDniEmisor() { return dniEmisor; }
    public void setDniEmisor(String dniEmisor) { this.dniEmisor = dniEmisor; }
}