package com.example.Integra.controller;


import com.example.Integra.models.*;
import com.example.Integra.models.enumeration.Typee;
import com.example.Integra.models.enumeration.Urbanizable;
import com.example.Integra.service.ImageViviendaService;
import com.example.Integra.service.TerrenosImageService;
import com.example.Integra.service.TerrenosService;
import com.example.Integra.service.VivendaImageService;
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
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class TerrenosController {
    private TerrenosService terrenosService;
    private ImageViviendaService imageViviendaService;
    private TerrenosImageService terrenosImageService;

    @GetMapping("/public/Terrenos")
    public String showTerrenossPage(Model model, @RequestParam(value = "cuidad", required = false) String cuidad,
                                    @RequestParam(value = "minPrice", required = false) Double minPrice,
                                    @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                                    @RequestParam(value = "tamanoMin", required = false) Double tamanoMin,
                                    @RequestParam(value = "tamanoMax", required = false) Double tamanoMax,
                                    @RequestParam(value = "urbanizable", required = false) String urbanizable,
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
        if (urbanizable != null) {
            terrenos = terrenos.stream()
                    .filter(terrenoss -> terrenoss.getUrbanizable().equals(Urbanizable.valueOf(urbanizable)))
                    .collect(Collectors.toList());
        }
        List<Terrenos> filteredList = terrenos.stream()
                .sorted(Comparator.comparing(Terrenos::getDate).reversed())
                .collect(Collectors.toList());
        model.addAttribute("terrenos", filteredList);
        return "public/Terrenos";
    }

    @GetMapping("/private/Terrenos")
    public String showprivatTerrenossPage(Model model, @RequestParam(value = "cuidad", required = false) String cuidad,
                                          @RequestParam(value = "minPrice", required = false) Double minPrice,
                                          @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                                          @RequestParam(value = "tamanoMin", required = false) Double tamanoMin,
                                          @RequestParam(value = "tamanoMax", required = false) Double tamanoMax,
                                          @RequestParam(value = "urbanizable", required = false) String urbanizable,
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
        if (urbanizable != null) {
            terrenos = terrenos.stream()
                    .filter(terrenoss -> terrenoss.getUrbanizable().equals(Urbanizable.valueOf(urbanizable)))
                    .collect(Collectors.toList());
        }
        List<Terrenos> filteredList = terrenos.stream()
                .sorted(Comparator.comparing(Terrenos::getDate).reversed())
                .collect(Collectors.toList());
        model.addAttribute("terrenos", filteredList);
        return "private/Terrenos";
    }

    @GetMapping("/public/Terrenos/{id}")
    public String showTerrenosPage(@PathVariable String id, Model model) {
        Terrenos terrenos = terrenosService.getById(Long.valueOf(id));
        List<TerrenosImage> terrenosImages = terrenosImageService.listVivienda();
        List<TerrenosImage> terrenosImageF = terrenosImages.stream()
                .filter(viviendas -> viviendas.getTerrenos().getId().equals(terrenos.getId()))
                .collect(Collectors.toList());
        model.addAttribute("terrenos", terrenos);
        model.addAttribute("terrenosImages", terrenosImageF);
        return "public/NegocioCardTerrenos";
    }

    @GetMapping("/private/Terrenos/{id}")
    public String showPrivateTerrenosPage(@PathVariable String id, Model model) {
        Terrenos terrenos = terrenosService.getById(Long.valueOf(id));
        List<TerrenosImage> terrenosImages = terrenosImageService.listVivienda();
        List<TerrenosImage> terrenosImageF = terrenosImages.stream()
                .filter(viviendas -> viviendas.getTerrenos().getId().equals(terrenos.getId()))
                .collect(Collectors.toList());
        model.addAttribute("terrenos", terrenos);
        model.addAttribute("terrenosImages", terrenosImageF);
        return "private/NegocioCardTerrenos";
    }
    @GetMapping("/private/delete/Terrenos/{id}")
    public String deleteViviendasPage(Model model, @PathVariable String id) {
        Terrenos terrenos = terrenosService.getById(Long.valueOf(id));
        terrenosService.eliminoTerrenos(terrenos);
        return "redirect:/";
    }

    @GetMapping("/public/imagesTerrenos/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        byte[] imageData = imageViviendaService.getImageFromS3(imageName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }

    @PostMapping("/private/Terrenos/edit")
    public ResponseEntity<String> editTerrenos(@RequestBody Terrenos terrenos,
                                               @AuthenticationPrincipal User user) throws IOException {
        Terrenos terrenosold = terrenosService.getById(terrenos.getId());
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
           terrenosService.guardarTerrenos(terrenos);
        } else if (user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
        }
        if (terrenos.getTamano() != null) {
            if(terrenosold.getTamano() == null){
                terrenosold.setTamano(terrenos.getTamano());
            }else
            if (!terrenosold.getTamano().equals(terrenos.getTamano())) {
                terrenosold.setTamano(terrenos.getTamano());
            }
        }
        if (terrenos.getPrecio() != null) {
            if(terrenosold.getPrecio() == null){
                terrenosold.setPrecio(terrenos.getPrecio());
            }else
            if (!terrenosold.getPrecio().equals(terrenos.getPrecio())) {
                terrenosold.setPrecio(terrenos.getPrecio());
            }
        }
        if (terrenos.getCuidad() != null) {
            if(terrenosold.getCuidad() == null){
                terrenosold.setCuidad(terrenos.getCuidad());
            }else
            if (!terrenosold.getCuidad().equals(terrenos.getCuidad())) {
                terrenosold.setCuidad(terrenos.getCuidad());
            }
        }
        if (terrenos.getTitle() != null) {
            if(terrenosold.getTitle() == null){
                terrenosold.setTitle(terrenos.getTitle());
            }else
            if (!terrenosold.getTitle().equals(terrenos.getTitle())) {
                terrenosold.setTitle(terrenos.getTitle());
            }
        }
        if (terrenos.getIbi() != null) {
            if(terrenosold.getIbi() == null){
                terrenosold.setIbi(terrenos.getIbi());
            }else
            if (!terrenosold.getDescripcion().equals(terrenos.getDescripcion()) && terrenos.getDescripcion() != null) {
                terrenosold.setDescripcion(terrenos.getDescripcion());
            }
        }
        if (terrenos.getVisibility() != null) {
            if(terrenosold.getVisibility() == null){
                terrenosold.setVisibility(terrenos.getVisibility());
            }else
            if (!terrenosold.getVisibility().equals(terrenos.getVisibility())) {
                terrenosold.setVisibility(terrenos.getVisibility());
            }
        }
        terrenosService.guardarTerrenos(terrenosold);
        return new ResponseEntity<>(terrenos.getId().toString(), HttpStatus.OK);
    }

    @PostMapping("/private/Terrenos/create")
    public ResponseEntity<String> createTerrenos(@RequestBody Terrenos terrenos,
                                                 @AuthenticationPrincipal User user) throws IOException {
        terrenos.setDate(DateTime.now().toDate());
        terrenos.setIntroduce(user.getId().intValue());
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
            terrenosService.guardarTerrenos(terrenos);
        } else if (user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            terrenosService.guardarTerrenos(terrenos);
        }

        terrenosService.guardarTerrenos(terrenos);
        return new ResponseEntity<>(terrenos.getId().toString(),HttpStatus.OK);
    }


    @PostMapping("/private/Terrenoscreateimages/{id}")
    public ResponseEntity<String> handleFilesUpload(@PathVariable String id,
                                                    @RequestParam("images") List<MultipartFile> files,
                                                    @RequestParam("selectedImageFile") String selectedImageFile,
                                                    @AuthenticationPrincipal User user) throws IOException {
        var terrenos = terrenosService.getById(Long.valueOf(id));
        AtomicInteger counter = new AtomicInteger();

        // Сначала сохраняем выбранную фотографию
        byte[] selectedImageBytes = Base64.getDecoder().decode(selectedImageFile.split(",")[1]);
        String selectedImageName = "selectedImage" + DateTime.now().toString("EEE_MMM_dd_HH:mm:ss_zzz_yyyy") + terrenos.getId()+ ".jpg"; // Задайте имя по вашему желанию
        imageViviendaService.saveImageToS3("image/jpeg", selectedImageName, selectedImageBytes);
        terrenosImageService.guardarVivienda(new TerrenosImage(terrenos, selectedImageName));

        // Затем сохраняем остальные фотографии
        for (MultipartFile file : files) {
            if (!file.getOriginalFilename().equals(selectedImageName)) {
                try {
                    String originalFilename = file.getOriginalFilename();
                    int lastDotIndex = originalFilename.lastIndexOf(".");
                    String fileExtension = originalFilename.substring(lastDotIndex + 1);
                    String name = "img" + terrenos.getId().toString() + DateTime.now().toString("EEE_MMM_dd_HH:mm:ss_zzz_yyyy") + counter.toString() + "." + fileExtension;
                    imageViviendaService.saveImageToS3(file.getContentType(), name, file.getBytes());
                    terrenosImageService.guardarVivienda(new TerrenosImage(terrenos, name));
                    counter.getAndIncrement();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (terrenos.getPreviewImage() == null) {
            terrenos.setPreviewImage(selectedImageName);
        }

        terrenosService.guardarTerrenos(terrenos);

        return new ResponseEntity<>(terrenos.getId().toString(), HttpStatus.OK);
    }

    @PostMapping("/private/Terrenos/addImage/{id}")
    public ResponseEntity<String> addImagesToTerrenos(@PathVariable String id,
                                                      @RequestParam("images") List<MultipartFile> files,
                                                      @AuthenticationPrincipal User user) throws IOException {
        Terrenos terrenos = terrenosService.getById(Long.valueOf(id));

        for (MultipartFile file : files) {
            try {
                String originalFilename = file.getOriginalFilename();
                int lastDotIndex = originalFilename.lastIndexOf(".");
                String fileExtension = originalFilename.substring(lastDotIndex + 1);
                String name = "img" + terrenos.getId().toString() + DateTime.now().toString("EEE_MMM_dd_HH:mm:ss_zzz_yyyy") + "." + fileExtension;
                imageViviendaService.saveImageToS3(file.getContentType(), name, file.getBytes());
                terrenosImageService.guardarVivienda(new TerrenosImage(terrenos, name));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        terrenosService.guardarTerrenos(terrenos);

        return new ResponseEntity<>(terrenos.getId().toString(), HttpStatus.OK);
    }



    @PostMapping("/private/Terrenos/deleteImage/{id}/{imageId}")
    public ResponseEntity<String> deleteImageForTerrenos(@PathVariable String id,
                                                         @PathVariable String imageId,
                                                         @AuthenticationPrincipal User user) throws IOException {
        Terrenos terrenos = terrenosService.getById(Long.valueOf(id));
        TerrenosImage imageToDelete = terrenosImageService.getbyname(imageId);

        if (imageToDelete != null) {
            terrenosImageService.eliminoVivienda(imageToDelete);
            imageViviendaService.deleteImageFromS3(imageToDelete.getNameImage());
        }

        return new ResponseEntity<>(terrenos.getId().toString(), HttpStatus.OK);
    }


}


