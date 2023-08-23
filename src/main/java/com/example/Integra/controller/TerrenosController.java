package com.example.Integra.controller;


import com.example.Integra.models.Terrenos;
import com.example.Integra.models.User;
import com.example.Integra.models.Vivienda;
import com.example.Integra.models.enumeration.Typee;
import com.example.Integra.service.ImageViviendaService;
import com.example.Integra.service.TerrenosService;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class TerrenosController {
    private TerrenosService terrenosService;
    private ImageViviendaService imageTerrenosService;

    @GetMapping("/public/Terrenos")
    public String showTerrenossPage(Model model, @RequestParam(value = "cuidad", required = false) String cuidad,
                                    @RequestParam(value = "minPrice", required = false) Double minPrice,
                                    @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                                    @RequestParam(value = "tamanoMin", required = false) Double tamanoMin,
                                    @RequestParam(value = "tamanoMax", required = false) Double tamanoMax,
                                    @RequestParam(value = "fechadeintroduccion", required = false)
                                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechadeintroduccionFilter) {
        List<Terrenos> terrenos = terrenosService.listTerrenos();
        if (cuidad != null && !cuidad.isEmpty()) {
            terrenos = terrenos.stream()
                    .filter(terrenoss -> cuidad.equals(terrenoss.getCuidad()))
                    .collect(Collectors.toList());
        }
        if (minPrice != null) {
            terrenos = terrenos.stream()
                    .filter(terrenoss -> terrenoss.getPrecio() >= minPrice)
                    .collect(Collectors.toList());
        }
        if (maxPrice != null) {
            terrenos = terrenos.stream()
                    .filter(terrenoss -> terrenoss.getPrecio() <= maxPrice)
                    .collect(Collectors.toList());
        }
        if (tamanoMin != null) {
            terrenos = terrenos.stream()
                    .filter(terrenoss -> terrenoss.getTamano() >= tamanoMin)
                    .collect(Collectors.toList());
        }
        if (tamanoMax != null) {
            terrenos = terrenos.stream()
                    .filter(terrenoss -> terrenoss.getTamano() <= tamanoMax)
                    .collect(Collectors.toList());
        }
        if (fechadeintroduccionFilter != null) {
            terrenos = terrenos.stream()
                    .filter(terrenoss -> terrenoss.getDate().after(fechadeintroduccionFilter))
                    .collect(Collectors.toList());
        }
        model.addAttribute("terrenos", terrenos);
        return "public/Terrenos";
    }

    @GetMapping("/private/Terrenos")
    public String showprivatTerrenossPage(Model model, @RequestParam(value = "cuidad", required = false) String cuidad,
                                          @RequestParam(value = "minPrice", required = false) Double minPrice,
                                          @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                                          @RequestParam(value = "tamanoMin", required = false) Double tamanoMin,
                                          @RequestParam(value = "tamanoMax", required = false) Double tamanoMax,
                                          @RequestParam(value = "fechadeintroduccion", required = false)
                                              @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechadeintroduccionFilter) {
        List<Terrenos> terrenos = terrenosService.listTerrenos();
        if (cuidad != null && !cuidad.isEmpty()) {
            terrenos = terrenos.stream()
                    .filter(terrenoss -> cuidad.equals(terrenoss.getCuidad()))
                    .collect(Collectors.toList());
        }
        if (minPrice != null) {
            terrenos = terrenos.stream()
                    .filter(terrenoss -> terrenoss.getPrecio() >= minPrice)
                    .collect(Collectors.toList());
        }
        if (maxPrice != null) {
            terrenos = terrenos.stream()
                    .filter(terrenoss -> terrenoss.getPrecio() <= maxPrice)
                    .collect(Collectors.toList());
        }
        if (tamanoMin != null) {
            terrenos = terrenos.stream()
                    .filter(terrenoss -> terrenoss.getTamano() >= tamanoMin)
                    .collect(Collectors.toList());
        }
        if (tamanoMax != null) {
            terrenos = terrenos.stream()
                    .filter(terrenoss -> terrenoss.getTamano() <= tamanoMax)
                    .collect(Collectors.toList());
        }
        if (fechadeintroduccionFilter != null) {
            terrenos = terrenos.stream()
                    .filter(terrenoss -> terrenoss.getDate().after(fechadeintroduccionFilter))
                    .collect(Collectors.toList());
        }
        model.addAttribute("terrenos", terrenos);
        return "private/Terrenos";
    }

    @GetMapping("/public/Terrenos/{id}")
    public String showTerrenosPage(@PathVariable String id, Model model) {
        Terrenos terrenos = terrenosService.getById(Long.valueOf(id));
        model.addAttribute("terrenos", terrenos);
        return "public/NegocioCardTerrenos";
    }

    @GetMapping("/private/Terrenos/{id}")
    public String showPrivateTerrenosPage(@PathVariable String id, Model model) {
        Terrenos terrenos = terrenosService.getById(Long.valueOf(id));
        model.addAttribute("terrenos", terrenos);
        return "private/NegocioCardTerrenos";
    }
    @GetMapping("/private/delete/Terrenos/{id}")
    public String deleteViviendasPage(Model model, @PathVariable String id) {
        Terrenos terrenos = terrenosService.getById(Long.valueOf(id));
        terrenosService.eliminoTerrenos(terrenos);
        return "public/Principal";
    }

    @GetMapping("/public/imagesTerrenos/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        byte[] imageData = imageTerrenosService.getImageFromS3(imageName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }


    @PostMapping("/private/Terrenos/create")
    public ResponseEntity<String> createTerrenos(@RequestBody Terrenos terrenos,
                                                 @AuthenticationPrincipal User user) throws IOException {
        terrenos.setDate(DateTime.now().toDate());
        terrenos.setIntroduce(user.getId().intValue());
        terrenos.setImageViviendas(new ArrayList<>());
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
            terrenosService.guardarTerrenos(terrenos);
        } else if (user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            terrenosService.guardarTerrenos(terrenos);
        }

        terrenosService.guardarTerrenos(terrenos);
        return new ResponseEntity<>(terrenos.getId().toString(),HttpStatus.OK);
    }

    @PostMapping("/private/Terrenoscreateimage/{id}")
    public String handleFileUpload(@PathVariable String id, @RequestParam("images") List<MultipartFile> files, @AuthenticationPrincipal User user
    ) throws IOException {
        var Terrenos = terrenosService.getById(Long.valueOf(id));
        List<String> img = new ArrayList<>();
        files.stream().forEach(file -> {
            try {
                imageTerrenosService.saveImageToS3(file.getContentType(), file.getOriginalFilename(), file.getBytes());
                img.add(file.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        if(Terrenos.getPreviewImage() == null){
            Terrenos.setPreviewImage(files.get(0).getOriginalFilename());
        }
        Terrenos.setImageViviendas(img);
        terrenosService.modifyTerrenos(Terrenos);


        return "redirect:/";
    }

}


