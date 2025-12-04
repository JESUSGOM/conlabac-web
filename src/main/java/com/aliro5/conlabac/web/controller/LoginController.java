package com.aliro5.conlabac.web.controller;

import com.aliro5.conlabac.web.dto.UsuarioDTO;
import com.aliro5.conlabac.web.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    // 1. Mostrar la pantalla de Login (Es la página de inicio al entrar en la raíz)
    @GetMapping("/")
    public String mostrarLogin() {
        return "login";
    }

    // 2. Procesar el formulario de Login
    @PostMapping("/login")
    public String procesarLogin(@RequestParam("dni") String dni,
                                @RequestParam("clave") String clave,
                                Model model,
                                HttpSession session) {

        // Llamamos al servicio que consulta a la API
        UsuarioDTO usuario = loginService.login(dni, clave);

        if (usuario != null) {
            // ¡ÉXITO! Guardamos al usuario en la sesión para recordarlo
            session.setAttribute("usuarioLogueado", usuario);

            // CAMBIO IMPORTANTE: Redirigimos a la nueva pantalla de Inicio (Home)
            return "redirect:/home";

        } else {
            // ERROR: Volvemos al login mostrando un mensaje de error
            model.addAttribute("error", "Credenciales incorrectas");
            return "login";
        }
    }

    // 3. Cerrar sesión
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Borra la sesión y los datos del usuario logueado
        return "redirect:/";  // Vuelve a la pantalla de login
    }
}