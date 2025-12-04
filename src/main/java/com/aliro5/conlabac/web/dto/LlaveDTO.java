package com.aliro5.conlabac.web.dto;

public class LlaveDTO {
    private Integer id;
    private String codigo;      // Ej: K-101
    private Integer idCentro;
    private String puerta;      // Ej: Entrada Principal
    private String planta;
    private Integer cajetin;    // Ej: 5
    private String restriccion; // Ej: Solo Personal

    public LlaveDTO() {
    }

    // --- GETTERS Y SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }

    public String getPuerta() { return puerta; }
    public void setPuerta(String puerta) { this.puerta = puerta; }

    public String getPlanta() { return planta; }
    public void setPlanta(String planta) { this.planta = planta; }

    public Integer getCajetin() { return cajetin; }
    public void setCajetin(Integer cajetin) { this.cajetin = cajetin; }

    public String getRestriccion() { return restriccion; }
    public void setRestriccion(String restriccion) { this.restriccion = restriccion; }
}