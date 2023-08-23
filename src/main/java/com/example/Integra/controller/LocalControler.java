package com.example.Integra.controller;


import com.example.Integra.models.Local;
import com.example.Integra.models.User;
import com.example.Integra.models.enumeration.Opcion;
import com.example.Integra.models.enumeration.TipoLocal;
import com.example.Integra.models.enumeration.Typee;
import com.example.Integra.service.ImageViviendaService;
import com.example.Integra.service.ServicioLocal;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class LocalControler {
    private ServicioLocal servicioLocal;
    private ImageViviendaService imageViviendaService;

    @GetMapping("/public/Local")
    public String showLocalPage(Model model, @RequestParam(value = "cuidad", required = false) String cuidad,
                                    @RequestParam(value = "minPrice", required = false) Double minPrice,
                                    @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                                    @RequestParam(value = "tamanoMin", required = false) Double tamanoMin,
                                    @RequestParam(value = "tamanoMax", required = false) Double tamanoMax,
                                    @RequestParam(value = "bano", required = false) Double bano,
                                    @RequestParam(value = "habitaciones", required = false) Double habitaciones,
                                    @RequestParam(value = "type", required = false) String tipo,
                                    @RequestParam(value = "opcion", required = false) String opcion,
                                    @RequestParam(value = "fechadeintroduccion", required = false)
                                    @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechadeintroduccionFilter) {
        List<Local> local = servicioLocal.listLocal();
        if (cuidad != null && !cuidad.isEmpty()) {
            local = local.stream()
                    .filter(locals -> cuidad.equals(locals.getCuidad()))
                    .collect(Collectors.toList());
        }
        if (opcion != null && !opcion.isEmpty()) {
            local = local.stream()
                    .filter(locals -> Opcion.valueOf(opcion).equals(locals.getOpcion()))
                    .collect(Collectors.toList());
        }
        if (minPrice != null) {
            local = local.stream()
                    .filter(local1 -> local1.getPrecio() >= minPrice)
                    .collect(Collectors.toList());
        }
        if (maxPrice != null) {
            local = local.stream()
                    .filter(locals -> locals.getPrecio() <= maxPrice)
                    .collect(Collectors.toList());
        }
        if (tamanoMin != null) {
            local = local.stream()
                    .filter(local1 -> local1.getTamano() >= tamanoMin)
                    .collect(Collectors.toList());
        }
        if (tamanoMax != null) {
            local = local.stream()
                    .filter(local1 -> local1.getTamano() <= tamanoMax)
                    .collect(Collectors.toList());
        }
        if (bano != null) {
            local = local.stream()
                    .filter(local1 -> local1.getBano().equals(bano.intValue()))
                    .collect(Collectors.toList());
        }
        if (fechadeintroduccionFilter != null) {
            local = local.stream()
                    .filter(locals -> locals.getFechadeintroduccion().after(fechadeintroduccionFilter))
                    .collect(Collectors.toList());
        }
        if (tipo != null && !tipo.isEmpty()) {
            local = local.stream()
                    .filter(local1 -> TipoLocal.valueOf(tipo).equals(local1.getTipo()))
                    .collect(Collectors.toList());
        }
        model.addAttribute("local", local);
        return "public/Local";
    }

    @GetMapping("/private/Local")
    public String showPrivateLocalPage(Model model, @RequestParam(value = "cuidad", required = false) String cuidad,
                                           @RequestParam(value = "minPrice", required = false) Double minPrice,
                                           @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                                           @RequestParam(value = "tamanoMin", required = false) Double tamanoMin,
                                           @RequestParam(value = "tamanoMax", required = false) Double tamanoMax,
                                           @RequestParam(value = "bano", required = false) Double bano,
                                           @RequestParam(value = "habitaciones", required = false) Double habitaciones,
                                           @RequestParam(value = "tipo", required = false) String tipo,
                                           @RequestParam(value = "opcion", required = false) String opcion,
                                           @RequestParam(value = "fechadeintroduccion", required = false)
                                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechadeintroduccionFilter) {
        List<Local> local = servicioLocal.listLocal();
        if (cuidad != null && !cuidad.isEmpty()) {
            local = local.stream()
                    .filter(locals -> cuidad.equals(locals.getCuidad()))
                    .collect(Collectors.toList());
        }
        if (opcion != null && !opcion.isEmpty()) {
            local = local.stream()
                    .filter(locals -> Opcion.valueOf(opcion).equals(locals.getOpcion()))
                    .collect(Collectors.toList());
        }
        if (minPrice != null) {
            local = local.stream()
                    .filter(locals -> locals.getPrecio() >= minPrice)
                    .collect(Collectors.toList());
        }
        if (maxPrice != null) {
            local = local.stream()
                    .filter(locals -> locals.getPrecio() <= maxPrice)
                    .collect(Collectors.toList());
        }
        if (tamanoMin != null) {
            local = local.stream()
                    .filter(locals -> locals.getTamano() >= tamanoMin)
                    .collect(Collectors.toList());
        }
        if (tamanoMax != null) {
            local = local.stream()
                    .filter(locals -> locals.getTamano() <= tamanoMax)
                    .collect(Collectors.toList());
        }
        if (bano != null) {
            local = local.stream()
                    .filter(locals -> locals.getBano().equals(bano.intValue()))
                    .collect(Collectors.toList());
        }
        if (fechadeintroduccionFilter != null) {
            local = local.stream()
                    .filter(locals -> locals.getFechadeintroduccion().after(fechadeintroduccionFilter))
                    .collect(Collectors.toList());
        }
        if (tipo != null && !tipo.isEmpty()) {
            local = local.stream()
                    .filter(locals -> Typee.valueOf(tipo).equals(locals.getTipo()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("local", local);
        return "private/Local";
    }
    @GetMapping("/public/Local/{id}")
    public String showLocalPage(@PathVariable String id, Model model) {
        Local local = servicioLocal.getById(Long.valueOf(id));
        model.addAttribute("local", local);
        return "public/NegocioCardLocal";
    }

    @GetMapping("/private/Local/{id}")
    public String showPrivateLocalPage(@PathVariable String id, Model model) {
        Local local = servicioLocal.getById(Long.valueOf(id));
        model.addAttribute("local", local);
        return "private/NegocioCardLocal";
    }

    @GetMapping("/public/imagesLocal/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        byte[] imageData = imageViviendaService.getImageFromS3(imageName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }


    @GetMapping("/private/delete/Local/{id}")
    public String deleteLocalsPage(Model model, @PathVariable String id) {
        Local local = servicioLocal.getById(Long.valueOf(id));
        servicioLocal.eliminoLocal(local);
        return "public/Principal";
    }


    @PostMapping("/private/Local/create")
    public ResponseEntity<String> createLocal(@RequestBody Local local,
                                                 @AuthenticationPrincipal User user) throws IOException {
        local.setFechadeintroduccion(DateTime.now().toDate());
        local.setIntroduce(user.getId().intValue());
        local.setImageViviendas(new ArrayList<>());
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
            servicioLocal.guardarLocal(local);
        } else if (user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            servicioLocal.guardarLocal(local);
        }

        servicioLocal.guardarLocal(local);
        return new ResponseEntity<>(local.getId().toString(),HttpStatus.OK);
    }

    @PostMapping("/private/Localcreateimage/{id}")
    public String handleFileUpload(@PathVariable String id, @RequestParam("images") List<MultipartFile> files, @AuthenticationPrincipal User user
    ) throws IOException {
        var local = servicioLocal.getById(Long.valueOf(id));
        List<String> img = new ArrayList<>();
        files.stream().forEach(file -> {
            try {
                imageViviendaService.saveImageToS3(file.getContentType(), file.getOriginalFilename(), file.getBytes());
                img.add(file.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        if(local.getPreviewImage() == null){
            local.setPreviewImage(files.get(0).getOriginalFilename());
        }
        local.setImageViviendas(img);
        servicioLocal.modifyLocal(local);


        return "redirect:/";
    }

}

