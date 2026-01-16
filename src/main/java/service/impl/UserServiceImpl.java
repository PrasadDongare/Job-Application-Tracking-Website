package service.impl;

import domain.dto.user.UserProfileDto;
import domain.entity.Skill;
import domain.entity.User;
import exception.ResourceNotFoundException;
import mapper.UserMapper;
import repository.SkillRepository;
import repository.UserRepository;
import service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final SkillRepository skillRepo;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepo,
                           SkillRepository skillRepo,
                           UserMapper userMapper) {
        this.userRepo = userRepo;
        this.skillRepo = skillRepo;
        this.userMapper = userMapper;
    }

    @Override
    public UserProfileDto getProfileByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toProfileDto(user);
    }

    @Override
    @Transactional
    public UserProfileDto updateProfile(String email, UserProfileDto dto) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setName(dto.getName());
        user.setPhone(dto.getPhone());
        user.setEducation(dto.getEducation());
        user.setExperience(dto.getExperience());
        userRepo.save(user);

        return userMapper.toProfileDto(user);
    }

    @Override
    @Transactional
    public UserProfileDto updateSkills(String email, List<String> skills) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.getSkills().clear();
        if (skills != null) {
            for (String s : skills) {
                String trimmed = s.trim();
                if (trimmed.isEmpty()) continue;
                Skill skill = skillRepo.findByNameIgnoreCase(trimmed)
                        .orElseGet(() -> skillRepo.save(new Skill(null, trimmed)));
                user.getSkills().add(skill);
            }
        }
        userRepo.save(user);
        return userMapper.toProfileDto(user);
    }

    @Override
    public long countUsers() {
        return userRepo.count();
    }
}