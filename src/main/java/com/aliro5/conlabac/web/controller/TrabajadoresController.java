package com.aliro5.conlabac.web.controller;

import com.aliro5.conlabac.web.dto.CentroDTO;
import com.aliro5.conlabac.web.dto.ProveedorDTO; // <--- Importante
import com.aliro5.conlabac.web.dto.UsuarioDTO;
import com.aliro5.conlabac.web.service.CentroService;
import com.aliro5.conlabac.web.service.ProveedorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/trabajadores")
public class TrabajadoresController {

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private CentroService centroService;

    @GetMapping
    public String listarGlobal(Model model, HttpSession session) {
        // 1. Seguridad: Verificar usuario logueado
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        Integer idCentro = usuario.getIdCentro();

        // 2. Cargamos la lista de TODOS los trabajadores del centro
        model.addAttribute("listaTrabajadores", proveedorService.listarTodosTrabajadores(idCentro));

        // 3. NUEVO: Cargamos las empresas para poder mostrar sus Nombres en lugar de sus CIFs
        // Obtenemos la lista de proveedores del centro
        List<ProveedorDTO> empresas = proveedorService.listar(idCentro);

        // Convertimos la lista en un Mapa (Diccionario): Clave=CIF -> Valor=NombreEmpresa
        Map<String, String> mapaEmpresas = empresas.stream()
                .collect(Collectors.toMap(
                        ProveedorDTO::getCif,           // La clave del mapa será el CIF
                        ProveedorDTO::getDenominacion,  // El valor será el Nombre
                        (existente, reemplazo) -> existente // En caso de duplicados, mantenemos el primero
                ));

        // Pasamos el mapa a la vista para usarlo en el HTML
        model.addAttribute("mapaEmpresas", mapaEmpresas);

        // 4. Datos del Centro y Usuario para la cabecera
        CentroDTO centro = centroService.obtenerPorId(idCentro);
        model.addAttribute("nombreCentro", centro.getDenominacion());
        model.addAttribute("usuario", usuario);

        return "trabajadores-list";
    }
}