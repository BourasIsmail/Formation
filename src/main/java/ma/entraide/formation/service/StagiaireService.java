package ma.entraide.formation.service;

import ma.entraide.formation.entity.Stagiaire;
import ma.entraide.formation.repository.StagiaireRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StagiaireService {

    @Autowired
    private StagiaireRepo stagiaireRepo;

    public Stagiaire getStagiaireById(Long id) {
        Optional<Stagiaire> stagiaire = stagiaireRepo.findById(id);
        if (stagiaire.isPresent()) {
            return stagiaire.get();
        }
        else {
            throw new ResourceNotFoundException("Stagiaire not found");
        }
    }

    public List<Stagiaire> getStagiaires() {
        return stagiaireRepo.findAll();
    }

    public Page<Stagiaire> getAllStagiaires(Pageable pageable) {
        return stagiaireRepo.findAll(pageable);
    }

    public String addStagiaire(Stagiaire stagiaire) {
        stagiaireRepo.save(stagiaire);
        return "Stagiaire added";
    }

    public String updateStagiaire(Stagiaire stagiaire) {
        return "à faire";
    }

}
