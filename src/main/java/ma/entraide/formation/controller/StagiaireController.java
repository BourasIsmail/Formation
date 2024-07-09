package ma.entraide.formation.controller;

import ma.entraide.formation.entity.Stagiaire;
import ma.entraide.formation.exceptions.ErrorResponse;
import ma.entraide.formation.service.StagiaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/stagiaire")
@CrossOrigin("*")
public class StagiaireController {
    @Autowired
    private StagiaireService stagiaireService;

    @GetMapping
    public ResponseEntity<Page<Stagiaire>> getStagiaire(@RequestParam(defaultValue = "0") Integer page,
                                                        @RequestParam(defaultValue = "10") Integer size,
                                                        @RequestParam(defaultValue = "nom") String sortField,
                                                        @RequestParam(defaultValue = "asc") String sortDirection) {
        Sort.Direction direction = Sort.Direction.valueOf(sortDirection.toUpperCase()); // Ensure uppercase for case-insensitive matching
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        Page<Stagiaire> pages = stagiaireService.getAllStagiaires(pageable);
        return ResponseEntity.ok(pages);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Stagiaire>> getAllStagiaires() {
        List<Stagiaire> stagiaires =  stagiaireService.getStagiaires();
        return ResponseEntity.ok(stagiaires);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stagiaire> getStagiaire(@PathVariable Long id) {
        try {
            Stagiaire stagiaire = stagiaireService.getStagiaireById(id);
            return ResponseEntity.ok(stagiaire);
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/addStagiaire")
    public ResponseEntity<Stagiaire> addStagiaire(@RequestBody Stagiaire stagiaire) {
        try {
            Stagiaire result = stagiaireService.addStagiaire(stagiaire);
            return ResponseEntity.ok(result);
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updatePersonnel/{id}")
    public ResponseEntity<String> updateStagiairePersonnel(@PathVariable Long id, @RequestBody Stagiaire update) {
        try {
            String result = stagiaireService.updateStagiairePersonel(id , update);
            return ResponseEntity.ok(result);
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateAcademic/{id}")
    public ResponseEntity<Stagiaire> updateAcademic(@PathVariable Long id, @RequestBody Stagiaire update) {
        try {
            Stagiaire stagiaire = stagiaireService.updateStagiaireAcademic(id, update);
            return ResponseEntity.ok(stagiaire);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateFormation/{id}" )
    public ResponseEntity<Stagiaire> updateFormation(@PathVariable Long id, @RequestBody Stagiaire update) {
        try {
            Stagiaire stagiaire = stagiaireService.updateStagiaireFormation(id, update);
            return ResponseEntity.ok(stagiaire);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStagiaire(@PathVariable Long id) {
        try {
            String result = stagiaireService.deleteStagiaire(id);
            return ResponseEntity.ok(result);
        }catch (Exception e) {
            String errorMessage = "An error occurred while adding stagiaire: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(errorMessage));
        }
    }



    @GetMapping("/downloadDemande/{id}")
    public ResponseEntity<Resource> downloadFileDemande(@PathVariable Long id) {
        Stagiaire stagiaire = null;
        stagiaire = stagiaireService.getStagiaireById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(stagiaire.getFileTypeDemande()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + stagiaire.getFileNameDemande() + "\"")
                .body(new ByteArrayResource(stagiaire.getDemande()));
    }

    @PutMapping(value = "/upload/{id}", consumes = {MULTIPART_FORM_DATA_VALUE})
    public String uploadFile(@PathVariable Long id,
                             @RequestParam(required = false) MultipartFile file) {
        if (file != null) {
            stagiaireService.uploadFileDemande(id, file);
        return "uploaded";
        }else {
            return "error";
        }
    }

    @PutMapping(value = "/uploadDemande/{id}", consumes = {MULTIPART_FORM_DATA_VALUE})
    public String uploadFileDemande(@PathVariable Long id, @RequestParam MultipartFile file) {
        if (file != null) {
            stagiaireService.uploadFileDemande(id, file);
            return "uploaded";
        }else {
            return "error";
        }
    }


}
