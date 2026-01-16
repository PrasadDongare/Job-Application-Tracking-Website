package mapper;

import domain.dto.user.UserProfileDto;
import domain.entity.Skill;
import domain.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserProfileDto toProfileDto(User user) {
        UserProfileDto dto = new UserProfileDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setEducation(user.getEducation());
        dto.setExperience(user.getExperience());
        dto.setSkills(user.getSkills().stream()
                .map(Skill::getName)
                .collect(Collectors.toList()));
        return dto;
    }
}