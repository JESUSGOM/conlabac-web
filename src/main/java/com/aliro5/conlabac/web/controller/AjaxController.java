package com.aliro5.conlabac.web.controller;

import com.aliro5.conlabac.web.dto.AlquilerDTO;
import com.aliro5.conlabac.web.dto.PlantaDTO;
import com.aliro5.conlabac.web.service.MaestrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ajax")
public class AjaxController {

    @Autowired
    private MaestrosService maestrosService;

    // Endpoint para obtener los destinos (empresas) de un centro
    // URL: http://localhost:8081/ajax/destinos?centroId=1
    @GetMapping("/destinos")
    public List<AlquilerDTO> cargarDestinos(@RequestParam("centroId") Integer centroId) {
        return maestrosService.obtenerDestinos(centroId);
    }

    // Endpoint para obtener las plantas de un centro
    // URL: http://localhost:8081/ajax/plantas?centroId=1
    @GetMapping("/plantas")
    public List<PlantaDTO> cargarPlantas(@RequestParam("centroId") Integer centroId) {
        return maestrosService.obtenerPlantas(centroId);
    }
}