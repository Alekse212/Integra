package com.example.Integra.controller;



import com.example.Integra.models.User;
import com.example.Integra.models.Vivienda;
import com.example.Integra.models.enumeration.Estado;
import com.example.Integra.models.enumeration.Extras;
import com.example.Integra.models.enumeration.Opcion;
import com.example.Integra.models.enumeration.Typee;
import com.example.Integra.service.ImageViviendaService;
import com.example.Integra.service.ViviendaService;
import lombok.AllArgsConstructor;
import lombok.Data;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class ViviendaController {
    private ViviendaService viviendaService;
    private ImageViviendaService imageViviendaService;

    @GetMapping("/public/Vivienda")
    public String showViviendasPage(Model model, @RequestParam(value = "cuidad", required = false) String cuidad,
                                    @RequestParam(value = "minPrice", required = false) Double minPrice,
                                    @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                                    @RequestParam(value = "tamanoMin", required = false) Double tamanoMin,
                                    @RequestParam(value = "tamanoMax", required = false) Double tamanoMax,
                                    @RequestParam(value = "bano", required = false) Double bano,
                                    @RequestParam(value = "habitaciones", required = false) Double habitaciones,
                                    @RequestParam(value = "type", required = false) String type,
                                    @RequestParam(value = "estado", required = false) String estado,
                                    @RequestParam(value = "opcion", required = false) String opcion,
                                    @RequestParam(value = "fechadeintroduccion", required = false)
                                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechadeintroduccionFilter) {
        List<Vivienda> vivienda = viviendaService.listVivienda();
        if (cuidad != null && !cuidad.isEmpty()) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> cuidad.equals(viviendas.getCuidad()))
                    .collect(Collectors.toList());
        }
        if (opcion != null && !opcion.isEmpty()) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> Opcion.valueOf(opcion).equals(viviendas.getOpcion()))
                    .collect(Collectors.toList());
        }
        if (minPrice != null) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> viviendas.getPrecio() >= minPrice)
                    .collect(Collectors.toList());
        }
        if (maxPrice != null) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> viviendas.getPrecio() <= maxPrice)
                    .collect(Collectors.toList());
        }
        if (tamanoMin != null) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> viviendas.getTamano() >= tamanoMin)
                    .collect(Collectors.toList());
        }
        if (tamanoMax != null) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> viviendas.getTamano() <= tamanoMax)
                    .collect(Collectors.toList());
        }
        if (bano != null) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> viviendas.getBano().equals(bano.intValue()))
                    .collect(Collectors.toList());
        }
        if (habitaciones != null) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> viviendas.getHabitaciones().equals(habitaciones.intValue()))
                    .collect(Collectors.toList());
        }
        if (fechadeintroduccionFilter != null) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> viviendas.getFechadeintroduccion().after(fechadeintroduccionFilter))
                    .collect(Collectors.toList());
        }
        if (type != null && !type.isEmpty()) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> Typee.valueOf(type).equals(viviendas.getType()))
                    .collect(Collectors.toList());
        }
        if (estado != null && !estado.isEmpty()) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> Estado.valueOf(estado).equals(viviendas.getEstado()))
                    .collect(Collectors.toList());
        }
        vivienda.stream().sorted(Comparator.comparing(Vivienda::getFechadeintroduccion).reversed());
        model.addAttribute("vivienda", vivienda);
        return "public/Vivienda";
    }

    @GetMapping("/private/Vivienda")
    public String showPrivateViviendasPage(Model model, @RequestParam(value = "cuidad", required = false) String cuidad,
                                           @RequestParam(value = "minPrice", required = false) Double minPrice,
                                           @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                                           @RequestParam(value = "tamanoMin", required = false) Double tamanoMin,
                                           @RequestParam(value = "tamanoMax", required = false) Double tamanoMax,
                                           @RequestParam(value = "bano", required = false) Double bano,
                                           @RequestParam(value = "habitaciones", required = false) Double habitaciones,
                                           @RequestParam(value = "type", required = false) String type,
                                           @RequestParam(value = "estado", required = false) String estado,
                                           @RequestParam(value = "opcion", required = false) String opcion,
                                           @RequestParam(value = "fechadeintroduccion", required = false)
                                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechadeintroduccionFilter) {
        List<Vivienda> vivienda = viviendaService.listVivienda();

        // Применяем фильтры только если они заданы
        if (cuidad != null && !cuidad.isEmpty()) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> cuidad.equals(viviendas.getCuidad()))
                    .collect(Collectors.toList());
        }
        if (opcion != null && !opcion.isEmpty()) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> Opcion.valueOf(opcion).equals(viviendas.getOpcion()))
                    .collect(Collectors.toList());
        }
        if (minPrice != null) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> viviendas.getPrecio() >= minPrice)
                    .collect(Collectors.toList());
        }
        if (maxPrice != null) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> viviendas.getPrecio() <= maxPrice)
                    .collect(Collectors.toList());
        }
        if (tamanoMin != null) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> viviendas.getTamano() >= tamanoMin)
                    .collect(Collectors.toList());
        }
        if (tamanoMax != null) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> viviendas.getTamano() <= tamanoMax)
                    .collect(Collectors.toList());
        }
        if (bano != null) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> viviendas.getBano().equals(bano.intValue()))
                    .collect(Collectors.toList());
        }
        if (habitaciones != null) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> viviendas.getHabitaciones().equals(habitaciones.intValue()))
                    .collect(Collectors.toList());
        }
        if (fechadeintroduccionFilter != null) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> viviendas.getFechadeintroduccion().after(fechadeintroduccionFilter))
                    .collect(Collectors.toList());
        }
        if (type != null && !type.isEmpty()) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> Typee.valueOf(type).equals(viviendas.getType()))
                    .collect(Collectors.toList());
        }
        if (estado != null && !estado.isEmpty()) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> Estado.valueOf(estado).equals(viviendas.getEstado()))
                    .collect(Collectors.toList());
        }
        vivienda.stream().sorted(Comparator.comparing(Vivienda::getFechadeintroduccion).reversed());
        model.addAttribute("vivienda", vivienda);
        return "private/Vivienda";
    }
    @GetMapping("/public/Vivienda/{id}")
    public String showViviendaPage(@PathVariable String id, Model model) {
        Vivienda vivienda = viviendaService.getById(Long.valueOf(id));
        model.addAttribute("vivienda", vivienda);
        return "public/NegocioCard";
    }

    @GetMapping("/private/Vivienda/{id}")
    public String showPrivateViviendaPage(@PathVariable String id, Model model) {
        Vivienda vivienda = viviendaService.getById(Long.valueOf(id));
        model.addAttribute("vivienda", vivienda);
        return "private/NegocioCard";
    }

    @GetMapping("/public/imagesVivienda/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        byte[] imageData = imageViviendaService.getImageFromS3(imageName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }

    @GetMapping("/public/preveiw")
    public String showpreviewViviendasPage(Model model) {
        List<Vivienda> vivienda = viviendaService.listVivienda();
        List<Vivienda> filteredList = vivienda.stream()
                .sorted(Comparator.comparing(Vivienda::getFechadeintroduccion))
                .limit(10)
                .collect(Collectors.toList());
        model.addAttribute("vivienda", vivienda);
        return "public/Vivienda";
    }

    @GetMapping("/private/delete/Vivienda/{id}")
    public String deleteViviendasPage(Model model, @PathVariable String id) {
        Vivienda vivienda = viviendaService.getById(Long.valueOf(id));
        viviendaService.eliminoVivienda(vivienda);
        return "public/Principal";
    }


    @PostMapping("/private/Vivienda/create")
    public ResponseEntity<String> createVivienda(@RequestBody Vivienda vivienda,
                                                 @RequestParam("extras") List<String> selectedExtras,
                                                 @AuthenticationPrincipal User user) throws IOException {
        vivienda.setFechadeintroduccion(DateTime.now().toDate());
        vivienda.setIntroduce(user.getId().intValue());
        List<Extras>  ex= new ArrayList<>();
        selectedExtras.stream().forEach(extra -> {
            // Очищаем лишние символы и преобразуем к верному имени перечисления
            String cleanedExtra = extra.replaceAll("[\\[\\]\"]", "");
            ex.add(Extras.valueOf(cleanedExtra));
        });
        vivienda.setExtras(ex);
        vivienda.setImageViviendas(new ArrayList<>());
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
            viviendaService.guardarVivienda(vivienda);
        } else if (user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            viviendaService.guardarVivienda(vivienda);
        }

        viviendaService.guardarVivienda(vivienda);
        return new ResponseEntity<>(vivienda.getId().toString(),HttpStatus.OK);
    }

    @PostMapping("/private/Vivienda/edit")
    public ResponseEntity<String> editVivienda(@RequestBody Vivienda vivienda,
                                               @AuthenticationPrincipal User user) throws IOException {
        Vivienda viviendaold = viviendaService.getById(vivienda.getId());
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
            viviendaService.guardarVivienda(vivienda);
        } else if (user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {}
            if(!viviendaold.getAno().equals(vivienda.getAno()) && vivienda.getAno()!=null) {
                viviendaold.setAno(vivienda.getAno());
            }
            if(!viviendaold.getTamano().equals(vivienda.getTamano()) && vivienda.getTamano()!=null) {
                viviendaold.setTamano(vivienda.getTamano());
            }
            if(!viviendaold.getBano().equals(vivienda.getBano()) && vivienda.getBano()!=null) {
                viviendaold.setBano(vivienda.getBano());
            }
            if(!viviendaold.getPrecio().equals(vivienda.getPrecio()) && vivienda.getPrecio()!=null) {
                viviendaold.setPrecio(vivienda.getPrecio());
            }
            if(!viviendaold.getCuidad().equals(vivienda.getCuidad()) && vivienda.getCuidad()!=null) {
                viviendaold.setCuidad(vivienda.getCuidad());
            }
            if(!viviendaold.getHabitaciones().equals(vivienda.getHabitaciones()) && vivienda.getHabitaciones()!=null) {
                viviendaold.setHabitaciones(vivienda.getHabitaciones());
            }
            if(!viviendaold.getTitle().equals(vivienda.getTitle()) && vivienda.getTitle()!=null) {
                viviendaold.setTitle(vivienda.getTitle());
            }
            if(!viviendaold.getDescripcion().equals(vivienda.getDescripcion()) && vivienda.getDescripcion()!=null) {
                viviendaold.setDescripcion(vivienda.getDescripcion());
            }
            if(!viviendaold.getIbi().equals(vivienda.getIbi()) && vivienda.getIbi()!=null) {
                viviendaold.setIbi(vivienda.getIbi());
            }
        viviendaService.guardarVivienda(viviendaold);
        return new ResponseEntity<>(vivienda.getId().toString(),HttpStatus.OK);
    }
    @PostMapping("/private/Viviendacreateimage/{id}")
    public String handleFileUpload(@PathVariable String id,  @RequestParam("images") List<MultipartFile> files, @AuthenticationPrincipal User user
                                   ) throws IOException {
        var vivienda = viviendaService.getById(Long.valueOf(id));
        List<String> img = new ArrayList<>();
        files.stream().forEach(file -> {
            try {
                imageViviendaService.saveImageToS3(file.getContentType(), file.getOriginalFilename(), file.getBytes());
                img.add(file.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        if(vivienda.getPreviewImage() == null){
            vivienda.setPreviewImage(files.get(0).getOriginalFilename());
        }
        vivienda.setImageViviendas(img);
        viviendaService.modifyVivienda(vivienda);


        return "redirect:/";
    }

}


