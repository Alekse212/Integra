package com.example.Integra.controller;


import com.example.Integra.models.Local;
import com.example.Integra.models.Terrenos;
import com.example.Integra.models.User;
import com.example.Integra.models.Vivienda;
import com.example.Integra.service.ServicioLocal;
import com.example.Integra.service.TerrenosService;
import com.example.Integra.service.ViviendaService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@org.springframework.stereotype.Controller
@AllArgsConstructor
public class Controller {
    private ViviendaService viviendaService;
    private TerrenosService terrenosService;
    private ServicioLocal servicioLocal;

    @GetMapping("/")
    public String Principal(Model model) {
        List<Vivienda> vivienda = viviendaService.listVivienda();
        List<Vivienda> filteredList = vivienda.stream()
                .sorted(Comparator.comparing(Vivienda::getFechadeintroduccion).reversed())
                .limit(10)
                .collect(Collectors.toList());
        filteredList = filteredList.stream()
                .filter(viviendas -> !viviendas.getVisibility())
                .collect(Collectors.toList());
        List<Terrenos> terrenos = terrenosService.listTerrenos();
        List<Terrenos> filteredListT = terrenos.stream()
                .sorted(Comparator.comparing(Terrenos::getDate).reversed())
                .limit(10)
                .collect(Collectors.toList());
        filteredListT = filteredListT.stream()
                .filter(viviendas -> !viviendas.getVisibility())
                .collect(Collectors.toList());
        List<Local> local = servicioLocal.listLocal();
        List<Local> filteredListL = local.stream()
                .sorted(Comparator.comparing(Local::getFechadeintroduccion).reversed())
                .limit(10)
                .collect(Collectors.toList());
        filteredListL = filteredListL.stream()
                .filter(viviendas -> !viviendas.getVisibility())
                .collect(Collectors.toList());
        model.addAttribute("local", filteredListL);
        model.addAttribute("vivienda", filteredList);
        model.addAttribute("terrenos", filteredListT);
        return "public/Principal";
    }
    @GetMapping("/Robots.txt")
    public String Principal() {
        return "/static/Robots.txt";
    }
    @GetMapping("/public/Contactos")
    public String showContactosPage() {
        return "public/Contactos";
    }

    @GetMapping("/private/Contactos")
    public String showPrivateContactosPage() {
        return "private/Contactos";
    }

    @GetMapping("/private/Registacion")
    public String showRegistacionPage() {
        return "private/Registacion";
    }


    @GetMapping("/private/Usuario")
    public String showUsuarioPage() {
        return "private/Usuario";
    }

    @GetMapping("/private/IntrVivienda")
    public String showIntrCasasPage() {
        return "private/IntrVivienda";
    }

    @GetMapping("/private/IntrLocal")
    public String showIntrLocalPage() {
        return "private/IntrLocal";
    }

    @GetMapping("/private/IntrTerrenos")
    public String showIntrTerrenosPage() {
        return "private/IntrTerrenos";
    }


    @GetMapping("/private/IntroductionImageVivienda/{id}")
    public String showIntroductionImageViviendaPage(@PathVariable String id, Model model) {
        model.addAttribute("id", id);
        return "private/IntroductionImageVivienda";
    }

    @GetMapping("/private/IntroductionImageTerreno/{id}")
    public String showIntroductionImageTerrenoPage(@PathVariable String id, Model model) {
        model.addAttribute("id", id);
        return "private/IntroductionImageTerreno";
    }

    @GetMapping("/private/IntroductionImageLocal/{id}")
    public String showIntroductionImageLocalPage(@PathVariable String id, Model model) {
        model.addAttribute("id", id);
        return "private/IntroductionImageLocal";
    }

    @GetMapping("/public/Servicios")
    public String showServicios() {
        return "public/Servicios";
    }

    @GetMapping("/private/Servicios")
    public String showPrivateServicios() {
        return "private/Servicios";
    }

    @GetMapping("/public/PoliticaPrivasidad")
    public String showPoliticaPrivasidad() {
        return "public/PoliticaPrivasidad";
    }
    @GetMapping("/public/CondicionesDeUso")
    public String showCondicionesDeUso() {
        return "public/CondicionesDeUso";
    }

}

