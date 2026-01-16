package service;

import domain.dto.user.UserProfileDto;

import java.util.List;

public interface UserService {
    UserProfileDto getProfileByEmail(String email);
    UserProfileDto updateProfile(String email, UserProfileDto dto);
    UserProfileDto updateSkills(String email, List<String> skills);
    long countUsers();
}