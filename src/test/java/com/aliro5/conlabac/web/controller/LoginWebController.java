package com.aliro5.conlabac.web.controller;

import com.aliro5.conlabac.web.service.LoginWebService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginWebController {

    @Autowired
    private LoginWebService loginService;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // Carga login.html
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String dni,
                                @RequestParam String password,
                                HttpSession session,
                                Model model) {

        Object usuario = loginService.validarAcceso(dni, password);

        if (usuario != null) {
            // Guardamos el usuario en la sesión del navegador
            session.setAttribute("usuarioLogueado", usuario);
            return "redirect:/dashboard";
        } else {
            // Si falla, volvemos al login con un mensaje de error
            model.addAttribute("error", "DNI o Clave incorrectos");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Limpiamos la sesión
        return "redirect:/login";
    }
}