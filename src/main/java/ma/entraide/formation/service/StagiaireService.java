package ma.entraide.formation.service;

import ma.entraide.formation.entity.Province;
import ma.entraide.formation.entity.Stagiaire;
import ma.entraide.formation.repository.StagiaireRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class StagiaireService {

    @Autowired
    private StagiaireRepo stagiaireRepo;

    @Autowired
    private ProvinceService provinceService;

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
        Province province1 = provinceService.getProvinceById(stagiaire.getProvince1().getId());
        Province province2 = provinceService.getProvinceById(stagiaire.getProvince2().getId());
        Province province3 = provinceService.getProvinceById(stagiaire.getProvince3().getId());
        stagiaire.setProvince1(province1);
        stagiaire.setProvince2(province2);
        stagiaire.setProvince3(province3);
        stagiaireRepo.save(stagiaire);
        return "Stagiaire added";
    }

    public String updateStagiairePersonel(Long id,Stagiaire stagiaire) {
        Stagiaire newStagiaire = getStagiaireById(id);
        newStagiaire.setNom(stagiaire.getNom());
        newStagiaire.setPrenom(stagiaire.getPrenom());
        newStagiaire.setEmail(stagiaire.getEmail());
        newStagiaire.setAdresse(stagiaire.getAdresse());
        newStagiaire.setDateNaissance(stagiaire.getDateNaissance());
        newStagiaire.setSituationFamilial(stagiaire.getSituationFamilial());
        newStagiaire.setCin(stagiaire.getCin());
        stagiaireRepo.save(newStagiaire);
        return "Stagiaire updated";
    }


    public Stagiaire uploadFileAssurance(Long id , MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try{
            if(fileName.contains("..")){
                throw new Exception("Filemane contains invalid path sequence"
                        + fileName);
            }
            Stagiaire stagiaire = getStagiaireById(id);
            stagiaire.setFileNameAssurance(fileName);
            stagiaire.setFileTypeAssurance(file.getContentType());
            stagiaire.setAttestationAssurance(file.getBytes());
            return stagiaireRepo.save(stagiaire);
        }catch (Exception e){
            throw new ResourceNotFoundException("File not uploaded"+fileName);
        }
    }

    public Stagiaire uploadFileDemande(Long id , MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try{
            if(fileName.contains("..")){
                throw new Exception("Filemane contains invalid path sequence"
                        + fileName);
            }
            Stagiaire stagiaire = getStagiaireById(id);
            stagiaire.setFileNameDemande(fileName);
            stagiaire.setFileTypeDemande(file.getContentType());
            stagiaire.setDemande(file.getBytes());
            return stagiaireRepo.save(stagiaire);
        }catch (Exception e){
            throw new ResourceNotFoundException("File not uploaded"+fileName);
        }
    }

    public Stagiaire updateStagiaireAcademic(Long id , Stagiaire stagiaire){
        Stagiaire newStagiaire = getStagiaireById(id);
        try{
            newStagiaire.setNomUniversite(stagiaire.getNomUniversite());
            newStagiaire.setAdresseUniversite(stagiaire.getAdresseUniversite());
            newStagiaire.setSpecialite(stagiaire.getSpecialite());
            newStagiaire.setAssurance(stagiaire.isAssurance());

            return stagiaireRepo.save(newStagiaire);
        }catch (Exception e){
            throw new ResourceNotFoundException("Not updated");
        }
    }

    public Stagiaire updateStagiaireFormation(Long id , Stagiaire stagiaire){
        Stagiaire newStagiaire = getStagiaireById(id);
        try {
            Province province1 = provinceService.getProvinceById(stagiaire.getProvince1().getId());
            Province province2 = provinceService.getProvinceById(stagiaire.getProvince2().getId());
            Province province3 = provinceService.getProvinceById(stagiaire.getProvince3().getId());
            newStagiaire.setDateDebut1(stagiaire.getDateDebut1());
            newStagiaire.setDateFin1(stagiaire.getDateFin1());
            newStagiaire.setDateDebut2(stagiaire.getDateDebut2());
            newStagiaire.setDateFin2(stagiaire.getDateFin2());
            newStagiaire.setDateDebut3(stagiaire.getDateDebut3());
            newStagiaire.setDateFin3(stagiaire.getDateFin3());
            newStagiaire.setProvince1(province1);
            newStagiaire.setProvince2(province2);
            newStagiaire.setProvince3(province3);
            return stagiaireRepo.save(newStagiaire);
        }catch (Exception e){
            throw new ResourceNotFoundException("Not updated");
        }
    }

    public String deleteStagiaire(Long id) {
        Stagiaire stagiaire = getStagiaireById(id);
        stagiaireRepo.delete(stagiaire);
        return "Stagiaire deleted";
    }

}
