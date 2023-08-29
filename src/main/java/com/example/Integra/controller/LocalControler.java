package com.example.Integra.controller;


import com.example.Integra.models.*;
import com.example.Integra.models.enumeration.Opcion;
import com.example.Integra.models.enumeration.TipoLocal;
import com.example.Integra.models.enumeration.Typee;
import com.example.Integra.service.ImageViviendaService;
import com.example.Integra.service.LocalImageService;
import com.example.Integra.service.ServicioLocal;
import com.example.Integra.service.TerrenosImageService;
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
public class LocalControler {
    private ServicioLocal servicioLocal;
    private ImageViviendaService imageViviendaService;
    private LocalImageService localImageService;

    @GetMapping("/public/Local")
    public String showLocalPage(Model model, @RequestParam(value = "cuidad", required = false) String cuidad,
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
                    .filter(local1 -> local1.getBano().equals(Integer.valueOf(bano.toString())))
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
        local = local.stream()
                .filter(viviendas -> !viviendas.getVisibility())
                .collect(Collectors.toList());
        List<Local> filteredList = local.stream()
                .sorted(Comparator.comparing(Local::getFechadeintroduccion).reversed())
                .collect(Collectors.toList());
        model.addAttribute("local", filteredList);
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
                    .filter(locals -> locals.getBano().equals(Integer.valueOf(bano.toString())))
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
        List<Local> filteredList = local.stream()
                .sorted(Comparator.comparing(Local::getFechadeintroduccion).reversed())
                .collect(Collectors.toList());
        model.addAttribute("local", filteredList);
        return "private/Local";
    }

    @GetMapping("/public/Local/{id}")
    public String showLocalPage(@PathVariable String id, Model model) {
        Local local = servicioLocal.getById(Long.valueOf(id));
        List<LocalImage> localImages = localImageService.listlocal();
        List<LocalImage> localImageF = localImages.stream()
                .filter(viviendas -> viviendas.getLocal().getId().equals(local.getId()))
                .collect(Collectors.toList());
        model.addAttribute("local", local);
        model.addAttribute("localImages", localImageF);
        return "public/NegocioCardLocal";
    }

    @GetMapping("/private/Local/{id}")
    public String showPrivateLocalPage(@PathVariable String id, Model model) {
        Local local = servicioLocal.getById(Long.valueOf(id));
        List<LocalImage> localImages = localImageService.listlocal();
        List<LocalImage> localImageF = localImages.stream()
                .filter(viviendas -> viviendas.getLocal().getId().equals(local.getId()))
                .collect(Collectors.toList());
        model.addAttribute("local", local);
        model.addAttribute("localImages", localImageF);
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
        return "redirect:/";
    }

    @PostMapping("/private/Local/edit")
    public ResponseEntity<String> editLocal(@RequestBody Local local,
                                            @AuthenticationPrincipal User user) throws IOException {
        Local localold = servicioLocal.getById(local.getId());
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
            servicioLocal.guardarLocal(local);
        } else if (user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
        }
        if (local.getTamano() != null) {
            if (localold.getTamano() == null) {
                localold.setTamano(local.getTamano());
            } else if (!localold.getTamano().equals(local.getTamano())) {
                localold.setTamano(local.getTamano());
            }
        }
        if (local.getBano() != null) {
            if (localold.getBano() == null) {
                localold.setBano(local.getBano());
            } else if (!localold.getBano().equals(local.getBano())) {
                localold.setBano(local.getBano());
            }
        }
        if (local.getPrecio() != null) {
            if (localold.getPrecio() == null) {
                localold.setPrecio(local.getPrecio());
            } else if (!localold.getPrecio().equals(local.getPrecio())) {
                localold.setPrecio(local.getPrecio());
            }
        }
        if (local.getCuidad() != null) {
            if (localold.getCuidad() == null) {
                localold.setCuidad(local.getCuidad());
            } else if (!localold.getCuidad().equals(local.getCuidad())) {
                localold.setCuidad(local.getCuidad());
            }
        }
        if (local.getTitulo() != null) {
            if (localold.getTitulo() == null) {
                localold.setTitulo(local.getTitulo());
            } else if (!localold.getTitulo().equals(local.getTitulo())) {
                localold.setTitulo(local.getTitulo());
            }
        }
        if (local.getDescription() != null) {
            if (localold.getDescription() == null) {
                localold.setDescription(local.getDescription());
            } else if (!localold.getDescription().equals(local.getDescription())) {
                localold.setDescription(local.getDescription());
            }
        }
        if (local.getVisibility() != null) {
            if (localold.getVisibility() == null) {
                localold.setVisibility(local.getVisibility());
            } else if (!localold.getVisibility().equals(local.getVisibility())) {
                localold.setVisibility(local.getVisibility());
            }
        }
        if (local.getTipo() != null) {
            if (localold.getTipo() == null) {
                localold.setTipo(local.getTipo());
            } else if (!localold.getTipo().equals(local.getTipo())) {
                localold.setTipo(local.getTipo());
            }
        }
        if (local.getOpcion() != null) {
            if (localold.getOpcion() == null) {
                localold.setOpcion(local.getOpcion());
            } else if (!localold.getOpcion().equals(local.getOpcion())) {
                localold.setOpcion(local.getOpcion());
            }
        }
        servicioLocal.guardarLocal(localold);
        return new ResponseEntity<>(local.getId().toString(), HttpStatus.OK);
    }


    @PostMapping("/private/Local/create")
    public ResponseEntity<String> createLocal(@RequestBody Local local,
                                              @AuthenticationPrincipal User user) throws IOException {
        local.setFechadeintroduccion(DateTime.now().toDate());
        local.setIntroduce(Integer.valueOf(user.getId().intValue()));
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
            servicioLocal.guardarLocal(local);
        } else if (user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            servicioLocal.guardarLocal(local);
        }

        servicioLocal.guardarLocal(local);
        return new ResponseEntity<>(local.getId().toString(), HttpStatus.OK);
    }

    @PostMapping("/private/Localcreateimages/{id}")
    public ResponseEntity<String> handleFilesUpload(@PathVariable String id,
                                                    @RequestParam("images") List<MultipartFile> files,
                                                    @RequestParam("selectedImageFile") String selectedImageFile,
                                                    @AuthenticationPrincipal User user) throws IOException {
        var local = servicioLocal.getById(Long.valueOf(id));
        AtomicInteger counter = new AtomicInteger();

        // Сначала сохраняем выбранную фотографию
        byte[] selectedImageBytes = Base64.getDecoder().decode(selectedImageFile.split(",")[1]);
        String selectedImageName = "selectedImage" + DateTime.now().toDate() + local.getId() + ".jpg"; // Задайте имя по вашему желанию
        imageViviendaService.saveImageToS3("image/jpeg", selectedImageName, selectedImageBytes);
        localImageService.guardarLocal(new LocalImage(local, selectedImageName));

        // Затем сохраняем остальные фотографии
        for (MultipartFile file : files) {
            if (!file.getOriginalFilename().equals(selectedImageName)) {
                try {
                    String originalFilename = file.getOriginalFilename();
                    int lastDotIndex = originalFilename.lastIndexOf(".");
                    String fileExtension = originalFilename.substring(lastDotIndex + 1);
                    String name = "img" + local.getId().toString() + DateTime.now().toDate() + counter.toString() + "." + fileExtension;
                    imageViviendaService.saveImageToS3(file.getContentType(), name, file.getBytes());
                    localImageService.guardarLocal(new LocalImage(local, name));
                    counter.getAndIncrement();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (local.getPreviewImage() == null) {
            local.setPreviewImage(selectedImageName);
        }

        servicioLocal.guardarLocal(local);

        return new ResponseEntity<>(local.getId().toString(), HttpStatus.OK);
    }


    @PostMapping("/private/Local/addImage/{id}")
    public ResponseEntity<String> addImagesToLocal(@PathVariable String id,
                                                   @RequestParam("images") List<MultipartFile> files,
                                                   @AuthenticationPrincipal User user) throws IOException {
        Local local = servicioLocal.getById(Long.valueOf(id));

        for (MultipartFile file : files) {
            try {
                String originalFilename = file.getOriginalFilename();
                int lastDotIndex = originalFilename.lastIndexOf(".");
                String fileExtension = originalFilename.substring(lastDotIndex + 1);
                String name = "img" + local.getId().toString() + DateTime.now().toDate() + "." + fileExtension;
                imageViviendaService.saveImageToS3(file.getContentType(), name, file.getBytes());
                localImageService.guardarLocal(new LocalImage(local, name));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        servicioLocal.guardarLocal(local);

        return new ResponseEntity<>(local.getId().toString(), HttpStatus.OK);
    }


    @PostMapping("/private/Local/deleteImage/{id}/{imageId}")
    public ResponseEntity<String> deleteImageForLocal(@PathVariable String id,
                                                      @PathVariable String imageId,
                                                      @AuthenticationPrincipal User user) throws IOException {
        Local local = servicioLocal.getById(Long.valueOf(id));
        LocalImage imageToDelete = localImageService.getbyname(imageId);

        if (imageToDelete != null) {
            localImageService.eliminoVivienda(imageToDelete);
            imageViviendaService.deleteImageFromS3(imageToDelete.getNameImage());
        }

        return new ResponseEntity<>(local.getId().toString(), HttpStatus.OK);
    }
}