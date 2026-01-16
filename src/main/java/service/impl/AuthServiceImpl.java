package service.impl;

import config.JwtUtil;
import domain.dto.auth.JwtResponse;
import domain.dto.auth.LoginRequest;
import domain.dto.auth.RegisterRequest;
import domain.entity.Role;
import domain.entity.Skill;
import domain.entity.User;
import exception.BadRequestException;
import repository.RoleRepository;
import repository.SkillRepository;
import repository.UserRepository;
import service.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final SkillRepository skillRepo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepo,
                           RoleRepository roleRepo,
                           SkillRepository skillRepo,
                           PasswordEncoder encoder,
                           AuthenticationManager authManager,
                           JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.skillRepo = skillRepo;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setEducation(request.getEducation());
        user.setExperience(request.getExperience());

        Role userRole = roleRepo.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Role ROLE_USER not found"));

        user.getRoles().add(userRole);

        if (request.getSkills() != null) {
            for (String s : request.getSkills()) {
                String trimmed = s.trim();
                if (trimmed.isEmpty()) continue;
                Skill skill = skillRepo.findByNameIgnoreCase(trimmed)
                        .orElseGet(() -> skillRepo.save(new Skill(null, trimmed)));
                user.getSkills().add(skill);
            }
        }

        userRepo.save(user);
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserDetails principal = (UserDetails) auth.getPrincipal();
        String token = jwtUtil.generateToken(principal);

        JwtResponse res = new JwtResponse();
        res.setToken(token);
        res.setEmail(principal.getUsername());
        res.setRoles(principal.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).toList());
        return res;
    }
}