package hu.bme.pgaserver.controller;

import hu.bme.pgaserver.dto.PhotoDTO;
import hu.bme.pgaserver.mapper.PhotoMapper;
import hu.bme.pgaserver.model.Photo;
import hu.bme.pgaserver.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200/")
@Validated
@RequestMapping("/api/pictures")
@RestController
public class PictureController {
    private static final String UPLOAD_DIR = "static/uploads/";

    @Autowired
    private PhotoRepository photoRepo;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "name", required = false) String name) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("No file selected", HttpStatus.BAD_REQUEST);
        }

        String fileName;
        try {
            fileName = StringUtils.hasText(name) ? name : generateUniqueFileName(file.getOriginalFilename());
            Path uploadDir = Paths.get(UPLOAD_DIR);
            Files.createDirectories(uploadDir);
            Path filePath = uploadDir.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Photo photo = new Photo();
            photo.setFilename(fileName);
            photo.setName(StringUtils.hasText(name) ? name : generateUniqueFileName(fileName));
            photo.setCreationDate(LocalDateTime.now());
            photoRepo.save(photo);

            return new ResponseEntity<>("Photo uploaded successfully: " + fileName, HttpStatus.OK);

        } catch (IOException ex) {
            return new ResponseEntity<>("Error occurred while uploading photo", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String generateUniqueFileName(String originalFilename) {
        String name = StringUtils.hasText(originalFilename) ? originalFilename : UUID.randomUUID().toString();
        if (name.length() < 40) {
            name = String.format("%040d", new BigInteger(name.getBytes()));
        }
        return name;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePhoto(@PathVariable Long id) {
        try {
            Photo photo = photoRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid photo Id:" + id));
            photoRepo.delete(photo);
            return new ResponseEntity<>("Photo deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting photo", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<PhotoDTO>> getAllPhotos() {
        List<Photo> photos = photoRepo.findAll();
        List<PhotoDTO> photoDTOs = photos.stream()
                .map(PhotoMapper.INSTANCE::photoToPhotoDto)
                .toList();
        return new ResponseEntity<>(photoDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getPhoto(@PathVariable Long id) throws MalformedURLException {
        Photo photo = photoRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid photo Id:" + id));
        Path filePath = Paths.get(UPLOAD_DIR).resolve(photo.getFilename());
        Resource resource = new UrlResource(filePath.toUri());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/jpeg"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
