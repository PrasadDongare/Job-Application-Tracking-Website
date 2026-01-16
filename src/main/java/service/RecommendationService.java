package service;

import domain.dto.job.JobRecommendationDto;

import java.util.List;

public interface RecommendationService {
    List<JobRecommendationDto> recommendJobsForUser(Long userId);
}