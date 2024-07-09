package ma.entraide.formation.controller;

import ma.entraide.formation.entity.Stagiaire;
import ma.entraide.formation.entity.StagiaireConf;
import ma.entraide.formation.service.StagiaireConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@CrossOrigin("*")
@RequestMapping("/stagiaireConf")
public class StagiaireConfController {
    @Autowired
    private StagiaireConfService stagiaireConfService;

    @GetMapping
    public ResponseEntity<Page<StagiaireConf>> getStagiaire(@RequestParam(defaultValue = "0") Integer page,
                                                            @RequestParam(defaultValue = "10") Integer size,
                                                            @RequestParam(defaultValue = "nom") String sortField,
                                                            @RequestParam(defaultValue = "asc") String sortDirection) {
        Sort.Direction direction = Sort.Direction.valueOf(sortDirection.toUpperCase()); // Ensure uppercase for case-insensitive matching
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        Page<StagiaireConf> pages = stagiaireConfService.getAllStagiaires(pageable);
        return ResponseEntity.ok(pages);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StagiaireConf>> getAllStagiaires() {
        List<StagiaireConf> stagiaires =  stagiaireConfService.getStagiaires();
        return ResponseEntity.ok(stagiaires);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StagiaireConf> getStagiaire(@PathVariable Long id) {
        try {
            StagiaireConf stagiaire = stagiaireConfService.getStagiaireById(id);
            return ResponseEntity.ok(stagiaire);
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/addStagiaire/{id}")
    public ResponseEntity<StagiaireConf> addStagiaire(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(stagiaireConfService.addStagiaire(id));
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateInfo/{id}")
    public ResponseEntity<String> updateInfo(@PathVariable Long id ,@RequestBody StagiaireConf stagiaire) {
        try {
            String resultat = stagiaireConfService.updateStagiaire(id, stagiaire);
            return ResponseEntity.ok(resultat);
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/uploadFiles/{id}", consumes = {MULTIPART_FORM_DATA_VALUE})
    public String uploadFile(@PathVariable Long id,
                             @RequestParam(required = false) MultipartFile file1,
                             @RequestParam(required = false) MultipartFile file2) {
        if (file1 != null) {
            stagiaireConfService.uploadFileDemande(id, file1);
        }
        if (file2 != null) {
            stagiaireConfService.uploadFileRapport(id, file2);
        }
        return "uploaded";
    }

    @PutMapping(value = "/upload/{id}" , consumes = {MULTIPART_FORM_DATA_VALUE})
    public String upload(@PathVariable Long id,
                         @RequestParam MultipartFile file) {
        try {
            if (file != null) {
                stagiaireConfService.uploadFileDemande(id, file);
            }
            return "uploaded";
        }catch (Exception e) {
            return e.getMessage();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStagiaire(@PathVariable Long id) {
        try {
            String resultat = stagiaireConfService.deleteStagiaire(id);
            return ResponseEntity.ok(resultat);
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/downloadRapport/{id}")
    public ResponseEntity<Resource> downloadRapport(@PathVariable Long id) {
        StagiaireConf stagiaire = null;
        stagiaire = stagiaireConfService.getStagiaireById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(stagiaire.getFileTypeRapport()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + stagiaire.getFileNameRapport() + "\"")
                .body(new ByteArrayResource(stagiaire.getRapport()));
    }

    @GetMapping("/byProvince/{province}")
    public ResponseEntity<List<StagiaireConf>> getByProvince(@PathVariable String province) {
        List<StagiaireConf> stagiaires = stagiaireConfService.getStagiairesByProvince(province);
        return ResponseEntity.ok(stagiaires);
    }


}
