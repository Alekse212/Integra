package com.example.Integra.controller;


import com.example.Integra.models.User;
import com.example.Integra.models.Vivienda;
import com.example.Integra.models.ViviendaImage;
import com.example.Integra.models.enumeration.*;
import com.example.Integra.service.ImageViviendaService;
import com.example.Integra.service.VivendaImageService;
import com.example.Integra.service.ViviendaService;
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
public class ViviendaController {
    private ViviendaService viviendaService;
    private ImageViviendaService imageViviendaService;
    private VivendaImageService vivendaImageService;

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
                                    @RequestParam(value = "planta", required = false) String planta,
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
        if (planta != null && !planta.isEmpty()) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> Plantas.valueOf(planta).equals(viviendas.getPlanta()))
                    .collect(Collectors.toList());
        }
            vivienda = vivienda.stream()
                    .filter(viviendas -> !viviendas.getVisibility())
                    .collect(Collectors.toList());
        List<Vivienda> filteredList = vivienda.stream()
                .sorted(Comparator.comparing(Vivienda::getFechadeintroduccion).reversed())
                .collect(Collectors.toList());
        model.addAttribute("vivienda", filteredList);
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
                                           @RequestParam(value = "planta", required = false) String planta,
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
        if (planta != null && !planta.isEmpty()) {
            vivienda = vivienda.stream()
                    .filter(viviendas -> Plantas.valueOf(planta).equals(viviendas.getPlanta()))
                    .collect(Collectors.toList());
        }
        List<Vivienda> filteredList = vivienda.stream()
                .sorted(Comparator.comparing(Vivienda::getFechadeintroduccion).reversed())
                .collect(Collectors.toList());
        model.addAttribute("vivienda", filteredList);
        return "private/Vivienda";
    }

    @GetMapping("/public/Vivienda/{id}")
    public String showViviendaPage(@PathVariable String id, Model model) {
        Vivienda vivienda = viviendaService.getById(Long.valueOf(id));
        List<ViviendaImage> viviendaImages = vivendaImageService.listVivienda();
        List<ViviendaImage> viviendaImageF = viviendaImages.stream()
                .filter(viviendas -> viviendas.getVivienda().getId().equals(vivienda.getId()))
                .collect(Collectors.toList());
        model.addAttribute("vivienda", vivienda);
        model.addAttribute("viviendaImages", viviendaImageF);
            return "public/NegocioCard";
    }

    @GetMapping("/private/Vivienda/{id}")
    public String showPrivateViviendaPage(@PathVariable String id, Model model) {
        Vivienda vivienda = viviendaService.getById(Long.valueOf(id));
        List<ViviendaImage> viviendaImages = vivendaImageService.listVivienda();
        List<ViviendaImage> viviendaImageF = viviendaImages.stream()
                .filter(viviendas -> viviendas.getVivienda().getId().equals(vivienda.getId()))
                .collect(Collectors.toList());
        model.addAttribute("vivienda", vivienda);
        model.addAttribute("viviendaImages", viviendaImageF);
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
                .sorted(Comparator.comparing(Vivienda::getFechadeintroduccion).reversed())
                .collect(Collectors.toList());
        Collections.reverse(filteredList);
        model.addAttribute("vivienda", filteredList);
        return "public/Vivienda";
    }

    @GetMapping("/private/delete/Vivienda/{id}")
    public String deleteViviendasPage(Model model, @PathVariable String id) {
        Vivienda vivienda = viviendaService.getById(Long.valueOf(id));
        vivendaImageService.listViviendaImagebyId(Long.valueOf(id)).stream().forEach(img -> {vivendaImageService.eliminoVivienda(img);
            imageViviendaService.deleteImageFromS3(img.getNameImage());});
        viviendaService.eliminoVivienda(vivienda);
        return "redirect:/";
    }


    @PostMapping("/private/Vivienda/create")
    public ResponseEntity<String> createVivienda(@RequestBody Vivienda vivienda,
                                                 @RequestParam("extras") List<String> selectedExtras,
                                                 @AuthenticationPrincipal User user) throws IOException {
        vivienda.setFechadeintroduccion(DateTime.now().toDate());
        vivienda.setIntroduce(user.getId().intValue());
        List<Extras> ex = new ArrayList<>();
        selectedExtras.stream().forEach(extra -> {
            // Очищаем лишние символы и преобразуем к верному имени перечисления
            String cleanedExtra = extra.replaceAll("[\\[\\]\"]", "");
            ex.add(Extras.valueOf(cleanedExtra));
        });
        vivienda.setExtras(ex);
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
            viviendaService.guardarVivienda(vivienda);
        } else if (user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            viviendaService.guardarVivienda(vivienda);
        }

        viviendaService.guardarVivienda(vivienda);
        String let = String.valueOf(vivienda.getColoborador().getDisplayName().charAt(0));
        vivienda.setRef(String.valueOf( let + vivienda.getId()));
        viviendaService.guardarVivienda(vivienda);
        return new ResponseEntity<>(vivienda.getId().toString(), HttpStatus.OK);
    }

    @PostMapping("/private/Vivienda/edit")
    public ResponseEntity<String> editVivienda(@RequestBody Vivienda vivienda,
                                               @RequestParam("extras") List<String> selectedExtras,
                                               @AuthenticationPrincipal User user) throws IOException {
        Vivienda viviendaold = viviendaService.getById(vivienda.getId());
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
            viviendaService.guardarVivienda(vivienda);
        } else if (user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
        }
        if (vivienda.getAno() != null) {
            if(viviendaold.getAno() == null){
                viviendaold.setAno(vivienda.getAno());
            }else
            if (!viviendaold.getAno().equals(vivienda.getAno())) {
                viviendaold.setAno(vivienda.getAno());
            }
        }
        if(!selectedExtras.get(0).equals("[]")) {
            List<Extras> ex = new ArrayList<>();
            selectedExtras.stream().forEach(extra -> {
                // Очищаем лишние символы и преобразуем к верному имени перечисления
                String cleanedExtra = extra.replaceAll("[\\[\\]\"]", "");
                ex.add(Extras.valueOf(cleanedExtra));
            });
            viviendaold.setExtras(ex);
        }
        if (vivienda.getTamano() != null) {
            if(viviendaold.getTamano() == null){
                viviendaold.setTamano(vivienda.getTamano());
            }else
            if (!viviendaold.getTamano().equals(vivienda.getTamano())) {
                viviendaold.setTamano(vivienda.getTamano());
            }
        }
        if (vivienda.getVisibility() != null) {
            if(viviendaold.getVisibility() == null){
                viviendaold.setVisibility(vivienda.getVisibility());
            }else
            if (!viviendaold.getVisibility().equals(vivienda.getVisibility())) {
                viviendaold.setVisibility(vivienda.getVisibility());
            }
        }
        if (vivienda.getType() != null) {
            if(viviendaold.getType() == null){
                viviendaold.setType(vivienda.getType());
            }else
            if (!viviendaold.getType().equals(vivienda.getType())) {
                viviendaold.setType(vivienda.getType());
            }
        }
        if (vivienda.getOpcion() != null) {
            if(viviendaold.getOpcion() == null){
                viviendaold.setOpcion(vivienda.getOpcion());
            }else
            if (!viviendaold.getOpcion().equals(vivienda.getOpcion())) {
                viviendaold.setOpcion(vivienda.getOpcion());
            }
        }
        if (vivienda.getOrientacion() != null) {
            if(viviendaold.getOrientacion() == null){
                viviendaold.setOrientacion(vivienda.getOrientacion());
            }else
            if (!viviendaold.getOrientacion().equals(vivienda.getOrientacion())) {
                viviendaold.setOrientacion(vivienda.getOrientacion());
            }
        }
        if (vivienda.getEstado() != null) {
            if(viviendaold.getEstado() == null){
                viviendaold.setEstado(vivienda.getEstado());
            }else
            if (!viviendaold.getEstado().equals(vivienda.getEstado())) {
                viviendaold.setEstado(vivienda.getEstado());
            }
        }
        if (vivienda.getPlanta() != null) {
            if(viviendaold.getPlanta() == null){
                viviendaold.setPlanta(vivienda.getPlanta());
            }else
            if (!viviendaold.getPlanta().equals(vivienda.getPlanta())) {
                viviendaold.setPlanta(vivienda.getPlanta());
            }
        }
        if (vivienda.getVisibility() != null) {
            if(viviendaold.getVisibility() == null){
                viviendaold.setVisibility(vivienda.getVisibility());
            }else
            if (!viviendaold.getVisibility().equals(vivienda.getVisibility())) {
                viviendaold.setVisibility(vivienda.getVisibility());
            }
        }
        if (vivienda.getBano() != null) {
            if(viviendaold.getBano() == null){
                viviendaold.setBano(vivienda.getBano());
            }else
            if (!viviendaold.getBano().equals(vivienda.getBano())) {
                viviendaold.setBano(vivienda.getBano());
            }
        }
        if (vivienda.getPrecio() != null) {
            if(viviendaold.getPrecio() == null){
                viviendaold.setPrecio(vivienda.getPrecio());
            }else
            if (!viviendaold.getPrecio().equals(vivienda.getPrecio())) {
                viviendaold.setPrecio(vivienda.getPrecio());
            }
        }
        if (vivienda.getCuidad() != null) {
            if(viviendaold.getCuidad() == null){
                viviendaold.setCuidad(vivienda.getCuidad());
            }else
            if (!viviendaold.getCuidad().equals(vivienda.getCuidad())) {
                viviendaold.setCuidad(vivienda.getCuidad());
            }
        }
        if (vivienda.getHabitaciones() != null) {
            if(viviendaold.getHabitaciones() == null){
                viviendaold.setHabitaciones(vivienda.getHabitaciones());
            }else
            if (!viviendaold.getHabitaciones().equals(vivienda.getHabitaciones())) {
                viviendaold.setHabitaciones(vivienda.getHabitaciones());
            }
        }
        if (vivienda.getTitle() != null) {
            if(viviendaold.getTitle() == null){
                viviendaold.setTitle(vivienda.getTitle());
            }else
            if (!viviendaold.getTitle().equals(vivienda.getTitle())) {
                viviendaold.setTitle(vivienda.getTitle());
            }
        }
        if (vivienda.getIbi() != null) {
            if(viviendaold.getIbi() == null){
                viviendaold.setIbi(vivienda.getIbi());
            }else
            if (!viviendaold.getDescripcion().equals(vivienda.getDescripcion()) && vivienda.getDescripcion() != null) {
                viviendaold.setDescripcion(vivienda.getDescripcion());
            }
        }
        if (vivienda.getComunidad() != null) {
            if(viviendaold.getComunidad() == null){
                viviendaold.setComunidad(vivienda.getComunidad());
            }else
            if (!viviendaold.getComunidad().equals(vivienda.getComunidad())) {
                viviendaold.setComunidad(vivienda.getComunidad());
            }
        }
        if (vivienda.getComunidad() != null) {
            if(viviendaold.getComunidad() == null){
                viviendaold.setComunidad(vivienda.getComunidad());
            }else
            if (!viviendaold.getComunidad().equals(vivienda.getComunidad())) {
                viviendaold.setComunidad(vivienda.getComunidad());
            }
        }
        viviendaService.guardarVivienda(viviendaold);
        return new ResponseEntity<>(vivienda.getId().toString(), HttpStatus.OK);
    }

    @PostMapping("/private/Viviendacreateimages/{id}")
    public ResponseEntity<String> handleFilesUpload(@PathVariable String id,
                                                    @RequestParam("images") List<MultipartFile> files,
                                                    @RequestParam("selectedImageFile") String selectedImageFile,
                                                    @AuthenticationPrincipal User user) throws IOException {


        var vivienda = viviendaService.getById(Long.valueOf(id));
        AtomicInteger counter = new AtomicInteger();


        // Сначала сохраняем выбранную фотографию
        byte[] selectedImageBytes = Base64.getDecoder().decode(selectedImageFile.split(",")[1]);
        String selectedImageName = "selectedImage" + DateTime.now().toString("EEE_MMM_dd_HH:mm:ss_zzz_yyyy") + vivienda.getId().toString()+ ".jpg"; // Задайте имя по вашему желанию
        imageViviendaService.saveImageToS3("image/jpeg", selectedImageName, selectedImageBytes);
        vivendaImageService.guardarVivienda(new ViviendaImage(vivienda, selectedImageName));

        // Затем сохраняем остальные фотографии
        for (MultipartFile file : files) {
            if (!file.getOriginalFilename().equals(selectedImageName)) {
                try {
                    String originalFilename = file.getOriginalFilename();
                    int lastDotIndex = originalFilename.lastIndexOf(".");
                    String fileExtension = originalFilename.substring(lastDotIndex + 1);
                    String name = "img" + vivienda.getId().toString() + DateTime.now().toString("EEE_MMM_dd_HH:mm:ss_zzz_yyyy") + counter.toString() + "." + fileExtension;
                    imageViviendaService.saveImageToS3(file.getContentType(), name, file.getBytes());
                    vivendaImageService.guardarVivienda(new ViviendaImage(vivienda, name));
                    counter.getAndIncrement();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (vivienda.getPreviewImage() == null) {
            vivienda.setPreviewImage(selectedImageName);
        }

        viviendaService.guardarVivienda(vivienda);

        return new ResponseEntity<>(vivienda.getId().toString(), HttpStatus.OK);
    }




    @PostMapping("/private/Vivienda/addImage/{id}")
    public ResponseEntity<String> addImagesToVivienda(@PathVariable String id,
                                                      @RequestParam("images") List<MultipartFile> files,
                                                      @AuthenticationPrincipal User user) throws IOException {
        Vivienda vivienda = viviendaService.getById(Long.valueOf(id));

        for (MultipartFile file : files) {
            try {
                String originalFilename = file.getOriginalFilename();
                int lastDotIndex = originalFilename.lastIndexOf(".");
                String fileExtension = originalFilename.substring(lastDotIndex + 1);
                String name = "img" + vivienda.getId().toString() + DateTime.now().toString("EEE_MMM_dd_HH:mm:ss_zzz_yyyy") + "." + fileExtension;
                imageViviendaService.saveImageToS3(file.getContentType(), name, file.getBytes());
                vivendaImageService.guardarVivienda(new ViviendaImage(vivienda, name));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        viviendaService.guardarVivienda(vivienda);

        return new ResponseEntity<>(vivienda.getId().toString(), HttpStatus.OK);
    }



    @PostMapping("/private/Vivienda/deleteImage/{id}/{imageId}")
    public ResponseEntity<String> deleteImageForVivienda(@PathVariable String id,
                                                         @PathVariable String imageId,
                                                         @AuthenticationPrincipal User user) throws IOException {
        Vivienda vivienda = viviendaService.getById(Long.valueOf(id));
        ViviendaImage imageToDelete = vivendaImageService.getbyname(imageId);

        if (imageToDelete != null) {
            vivendaImageService.eliminoVivienda(imageToDelete);
            imageViviendaService.deleteImageFromS3(imageToDelete.getNameImage());
        }

        return new ResponseEntity<>(vivienda.getId().toString(), HttpStatus.OK);
    }


}


