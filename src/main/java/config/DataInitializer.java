package config;

import domain.entity.Role;
import domain.entity.User;
import repository.RoleRepository;
import repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    public CommandLineRunner initData(UserRepository userRepo,
                                      RoleRepository roleRepo,
                                      PasswordEncoder encoder) {
        return args -> {
            log.info("Running DataInitializer...");

            // Create roles if not exist
            Role roleUser = roleRepo.findByName("ROLE_USER")
                    .orElseGet(() -> {
                        log.info("Creating role ROLE_USER");
                        return roleRepo.save(new Role(null, "ROLE_USER"));
                    });

            Role roleAdmin = roleRepo.findByName("ROLE_ADMIN")
                    .orElseGet(() -> {
                        log.info("Creating role ROLE_ADMIN");
                        return roleRepo.save(new Role(null, "ROLE_ADMIN"));
                    });

            // Create admin if not exist
            userRepo.findByEmail("admin@example.com").orElseGet(() -> {
                log.info("Creating admin user admin@example.com");
                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@example.com");
                admin.setPassword(encoder.encode("admin123"));
                admin.setEnabled(true);
                admin.setRoles(Set.of(roleAdmin));
                return userRepo.save(admin);
            });

            // Create demo user if not exist
            userRepo.findByEmail("user1@example.com").orElseGet(() -> {
                log.info("Creating demo user user1@example.com");
                User user = new User();
                user.setName("John User");
                user.setEmail("user1@example.com");
                user.setPassword(encoder.encode("user123"));
                user.setEnabled(true);
                user.setRoles(Set.of(roleUser));
                return userRepo.save(user);
            });

            log.info("DataInitializer completed.");
        };
    }
}