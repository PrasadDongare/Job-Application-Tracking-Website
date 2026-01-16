package service;

import domain.dto.application.ApplicationResponseDto;
import domain.entity.enums.ApplicationStatus;

import java.util.List;
import java.util.Map;

public interface ApplicationService {
    ApplicationResponseDto applyForJob(Long userId, Long jobId);
    List<ApplicationResponseDto> getUserApplications(Long userId);
    List<ApplicationResponseDto> getAllApplications();
    ApplicationResponseDto updateStatus(Long applicationId, ApplicationStatus status);
    long countApplications();
    Map<ApplicationStatus, Long> countByStatus();
}