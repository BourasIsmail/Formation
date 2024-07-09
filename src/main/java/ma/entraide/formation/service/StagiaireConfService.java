package ma.entraide.formation.service;

import ma.entraide.formation.entity.Province;
import ma.entraide.formation.entity.Stagiaire;
import ma.entraide.formation.entity.StagiaireConf;
import ma.entraide.formation.repository.StagiaireConfRepo;
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
public class StagiaireConfService {

    @Autowired
    private StagiaireConfRepo stagiaireConfRepo;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private StagiaireService stagiaireService;

    @Autowired
    private EmailSenderService emailSenderService;

    public StagiaireConf getStagiaireById(Long id) {
        Optional<StagiaireConf> stagiaire = stagiaireConfRepo.findById(id);
        if (stagiaire.isPresent()) {
            return stagiaire.get();
        }
        else {
            throw new ResourceNotFoundException("Stagiaire not found");
        }
    }

    public List<StagiaireConf> getStagiaires() {
        return stagiaireConfRepo.findAll();
    }

    public Page<StagiaireConf> getAllStagiaires(Pageable pageable) {
        return stagiaireConfRepo.findAll(pageable);
    }



    public StagiaireConf addStagiaire(Long id) {
        try {
            StagiaireConf stagiaireConf = new StagiaireConf();
            Stagiaire stagiaire = stagiaireService.getStagiaireById(id);
            stagiaireConf.setStagiaire(stagiaire);
            emailSenderService.sendSimpleEmail(stagiaireConf.getStagiaire().getEmail(),
                    "تم قبول طلب التدريب",
                    "تم قبول طلب التدريب في المراكز التابعة للتعاون الوطني");
            return stagiaireConfRepo.save(stagiaireConf);
        }catch (Exception e) {
            throw new ResourceNotFoundException("Stagiaire not found");
        }
    }

    public String updateStagiaire(Long id, StagiaireConf stagiaire) {
        StagiaireConf stagiaireConf = getStagiaireById(id);
        try {
            Province province = provinceService.getProvinceById(stagiaire.getProvince().getId());
            stagiaireConf.setProvince(province);
            stagiaireConf.setDuree(stagiaire.getDuree());
            stagiaireConf.setDateDebut(stagiaire.getDateDebut());
            stagiaireConf.setDateFin(stagiaire.getDateFin());
            stagiaireConf.setCentre(stagiaire.getCentre());
            stagiaireConf.setPresence(stagiaire.getPresence());
            stagiaireConfRepo.save(stagiaireConf);
            return "Stagiaire updated";
        }catch (Exception e) {
            throw new ResourceNotFoundException("Not updated");
        }
    }

    public StagiaireConf uploadFileRapport(Long id , MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try{
            if(fileName.contains("..")){
                throw new Exception("Filemane contains invalid path sequence"
                        + fileName);
            }
            StagiaireConf stagiaire = getStagiaireById(id);
            stagiaire.setFileNameRapport(fileName);
            stagiaire.setFileTypeRapport(file.getContentType());
            stagiaire.setRapport(file.getBytes());
            return stagiaireConfRepo.save(stagiaire);
        }catch (Exception e){
            throw new ResourceNotFoundException("File not uploaded"+fileName);
        }
    }

    public StagiaireConf uploadFileDemande(Long id , MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")){
                throw new Exception("Filemane contains invalid path sequence"
                        + fileName);
            }
            StagiaireConf stagiaire = getStagiaireById(id);
            stagiaire.getStagiaire().setFileNameDemande(fileName);
            stagiaire.getStagiaire().setFileTypeDemande(file.getContentType());
            stagiaire.getStagiaire().setDemande(file.getBytes());
            return stagiaireConfRepo.save(stagiaire);
        }catch (Exception e){
            throw new ResourceNotFoundException("File not uploaded"+fileName);
        }
    }

    public String deleteStagiaire(Long id) {
        StagiaireConf stagiaire = getStagiaireById(id);
        stagiaireConfRepo.delete(stagiaire);
        return "Stagiaire deleted";
    }

    public List<StagiaireConf> getStagiairesByProvince(String province) {
        return stagiaireConfRepo.findByProvince(province);
    }

}
