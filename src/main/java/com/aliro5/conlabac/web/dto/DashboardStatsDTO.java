package com.aliro5.conlabac.web.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class DashboardStatsDTO {
    private long relevosPendientes;
    private long paquetesPendientes;

    // Usamos JsonAlias por si la API envía "llavesPrestadas" o "llavesPendientes"
    @JsonAlias({"llavesPrestadas", "llavesPendientes"})
    private long llavesPendientes;

    private long visitasDentro;
    private long contratasDentro;

    public DashboardStatsDTO() {}

    // Getters y Setters corregidos
    public long getRelevosPendientes() { return relevosPendientes; }
    public void setRelevosPendientes(long relevosPendientes) { this.relevosPendientes = relevosPendientes; }

    public long getPaquetesPendientes() { return paquetesPendientes; }
    public void setPaquetesPendientes(long paquetesPendientes) { this.paquetesPendientes = paquetesPendientes; }

    public long getLlavesPendientes() { return llavesPendientes; }
    public void setLlavesPendientes(long llavesPendientes) { this.llavesPendientes = llavesPendientes; }

    public long getVisitasDentro() { return visitasDentro; }
    public void setVisitasDentro(long visitasDentro) { this.visitasDentro = visitasDentro; }

    public long getContratasDentro() { return contratasDentro; }
    public void setContratasDentro(long contratasDentro) { this.contratasDentro = contratasDentro; }
}