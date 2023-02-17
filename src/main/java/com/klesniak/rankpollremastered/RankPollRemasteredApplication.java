package com.klesniak.rankpollremastered;

import com.klesniak.rankpollremastered.user.entity.Role;
import com.klesniak.rankpollremastered.user.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.util.List;

@SpringBootApplication
public class RankPollRemasteredApplication implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(RankPollRemasteredApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Role userRole = new Role("USER");
        Role adminRole = new Role("ADMIN");
        roleRepository.saveAll(List.of(userRole, adminRole));
    }
}
