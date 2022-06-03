package org.sid;

import entities.AppRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import services.AccountService;
import services.AccountServiceImpl;

import java.util.stream.Stream;
@ComponentScan(basePackages = {"services"})
@SpringBootApplication
public class SecServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(AccountService accountService){
        return args -> {
            accountService.save(new AppRole(null,"USER"));
            accountService.save(new AppRole(null,"ADMIN"));
            Stream.of("user1","user2","user3","admin").forEach(un ->{
                accountService.saveUser(un,"1234","1234");
            });
        };
    }

    @Bean
    BCryptPasswordEncoder getBCPE(){
        return new BCryptPasswordEncoder();
    }
}
