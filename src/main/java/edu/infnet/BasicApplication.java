package edu.infnet;

import edu.infnet.model.Usuario;
import edu.infnet.persistence.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class BasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicApplication.class, args);
    }

    private static final Logger log = LoggerFactory.getLogger(BasicApplication.class);

//    @Bean
//    CommandLineRunner initDatabase(UserRepository userRepository) {
//        return args -> {
//            log.info("Teste User: " + userRepository.save(new Usuario("Marcelo ", "marcelo@gmail.com", "2124665585", "21940500", "Rua das Acássias, 90", "Ipanema", "Rio", "RJ", "Fotito")));
//
//        };
//    }
}
