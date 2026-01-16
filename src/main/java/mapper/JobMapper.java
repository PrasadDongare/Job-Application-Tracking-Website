package mapper;

import domain.dto.job.JobResponseDto;
import domain.entity.Job;
import domain.entity.Skill;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class JobMapper {

    public JobResponseDto toResponseDto(Job job) {
        JobResponseDto dto = new JobResponseDto();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setExperienceLevel(job.getExperienceLevel());
        dto.setLocation(job.getLocation());
        dto.setCreatedAt(job.getCreatedAt());
        dto.setRequiredSkills(job.getRequiredSkills().stream()
                .map(Skill::getName)
                .collect(Collectors.toList()));
        return dto;
    }
}