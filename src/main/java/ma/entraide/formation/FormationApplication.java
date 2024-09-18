package ma.entraide.formation;

import jakarta.annotation.PostConstruct;
import ma.entraide.formation.entity.UserInfo;
import ma.entraide.formation.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FormationApplication {

    @Autowired
    public UserInfoService userInfoService;

    public static Logger logger = LoggerFactory.getLogger(FormationApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(FormationApplication.class, args);
    }

    @PostConstruct
    public void init() {
        logger.info("Appilcation started ...");
    }


    @Bean
    public CommandLineRunner runner() {
        return args ->{
            logger.info("Running Spring Security Application ...");

			//UserInfo admin2 = new UserInfo("ADMIN1","admin1@gmailcom","ADMIN_ROLES","Entraide57");
			//userInfoService.addUser(admin2);


            logger.info("end");
        };
    }
}
