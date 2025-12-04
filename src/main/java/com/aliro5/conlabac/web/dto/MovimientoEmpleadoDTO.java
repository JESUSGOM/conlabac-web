package com.aliro5.conlabac.web.dto;

import java.time.LocalDateTime;

public class MovimientoEmpleadoDTO {
    private Integer id;
    private String cifProveedor;
    private Integer idCentro;
    private String nifEmpleado;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;

    // Campos auxiliares para mostrar nombres en la tabla (se rellenan en el controlador)
    private String nombreEmpleadoStr;
    private String nombreEmpresaStr;

    public MovimientoEmpleadoDTO() {}

    // --- GETTERS Y SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getCifProveedor() { return cifProveedor; }
    public void setCifProveedor(String cifProveedor) { this.cifProveedor = cifProveedor; }
    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }
    public String getNifEmpleado() { return nifEmpleado; }
    public void setNifEmpleado(String nifEmpleado) { this.nifEmpleado = nifEmpleado; }
    public LocalDateTime getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(LocalDateTime fechaEntrada) { this.fechaEntrada = fechaEntrada; }
    public LocalDateTime getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDateTime fechaSalida) { this.fechaSalida = fechaSalida; }

    public String getNombreEmpleadoStr() { return nombreEmpleadoStr; }
    public void setNombreEmpleadoStr(String nombreEmpleadoStr) { this.nombreEmpleadoStr = nombreEmpleadoStr; }
    public String getNombreEmpresaStr() { return nombreEmpresaStr; }
    public void setNombreEmpresaStr(String nombreEmpresaStr) { this.nombreEmpresaStr = nombreEmpresaStr; }
}