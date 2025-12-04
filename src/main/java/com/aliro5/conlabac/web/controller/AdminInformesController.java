package com.aliro5.conlabac.web.controller;

import com.aliro5.conlabac.web.dto.CentroDTO;
import com.aliro5.conlabac.web.dto.UsuarioDTO;
import com.aliro5.conlabac.web.service.CentroService;
import com.aliro5.conlabac.web.service.ReportService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/admin/informes")
public class AdminInformesController {

    @Autowired private ReportService reportService;
    @Autowired private CentroService centroService;

    // Seguridad: Permitimos 'Z' (Admin Global) y 'A' (Admin Informes)
    private boolean esAutorizado(HttpSession session) {
        UsuarioDTO u = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        return u != null && ("Z".equalsIgnoreCase(u.getTipo()) || "A".equalsIgnoreCase(u.getTipo()));
    }

    @GetMapping
    public String panel(Model model, HttpSession session) {
        if (!esAutorizado(session)) return "redirect:/home";
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");

        // 1. Generar lista de Años (Año actual y 2 anteriores)
        int anioActual = LocalDate.now().getYear();
        List<Integer> anios = new ArrayList<>();
        for (int i = 0; i < 3; i++) anios.add(anioActual - i);

        // 2. Generar lista de Meses (Nombre en Español)
        Map<Integer, String> meses = new LinkedHashMap<>();
        for (int i = 1; i <= 12; i++) {
            String nombre = Month.of(i).getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
            // Capitalizar primera letra (enero -> Enero)
            nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1);
            meses.put(i, nombre);
        }

        model.addAttribute("anios", anios);
        model.addAttribute("meses", meses);
        model.addAttribute("anioSeleccionado", anioActual);
        model.addAttribute("mesSeleccionado", LocalDate.now().getMonthValue());

        CentroDTO centro = centroService.obtenerPorId(usuario.getIdCentro());
        model.addAttribute("nombreCentro", centro.getDenominacion());
        model.addAttribute("usuario", usuario);

        return "informes-panel";
    }

    @PostMapping("/descargar")
    public ResponseEntity<ByteArrayResource> descargar(
            @RequestParam int mes,
            @RequestParam int anio,
            HttpSession session) {

        if (!esAutorizado(session)) return ResponseEntity.status(403).build();
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");

        // Llamada a la API
        byte[] data = reportService.descargarInformeMensual(mes, anio, usuario.getIdCentro());
        ByteArrayResource resource = new ByteArrayResource(data);

        String nombreFichero = "Informe_Mensual_" + mes + "_" + anio + ".pdf";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreFichero)
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(data.length)
                .body(resource);
    }
}