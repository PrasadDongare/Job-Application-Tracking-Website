package mapper;

import domain.dto.application.ApplicationResponseDto;
import domain.entity.Application;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMapper {

    public ApplicationResponseDto toResponseDto(Application a) {
        ApplicationResponseDto dto = new ApplicationResponseDto();
        dto.setId(a.getId());
        dto.setJobId(a.getJob().getId());
        dto.setJobTitle(a.getJob().getTitle());
        dto.setStatus(a.getStatus());
        dto.setAppliedAt(a.getAppliedAt());
        return dto;
    }
}