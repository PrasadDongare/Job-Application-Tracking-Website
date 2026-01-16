package service.impl;

import domain.dto.job.JobRecommendationDto;
import domain.entity.Job;
import domain.entity.User;
import exception.ResourceNotFoundException;
import repository.JobRepository;
import repository.UserRepository;
import service.RecommendationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final UserRepository userRepo;
    private final JobRepository jobRepo;

    public RecommendationServiceImpl(UserRepository userRepo, JobRepository jobRepo) {
        this.userRepo = userRepo;
        this.jobRepo = jobRepo;
    }

    @Override
    @Transactional
    public List<JobRecommendationDto> recommendJobsForUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Set<String> userSkillNames = user.getSkills().stream()
                .map(s -> s.getName().toLowerCase(Locale.ROOT))
                .collect(Collectors.toSet());

        List<Job> jobs = jobRepo.findAll();

        List<JobRecommendationDto> result = new ArrayList<>();

        for (Job job : jobs) {
            Set<String> jobSkillNames = job.getRequiredSkills().stream()
                    .map(s -> s.getName().toLowerCase(Locale.ROOT))
                    .collect(Collectors.toSet());

            if (jobSkillNames.isEmpty()) {
                continue;
            }

            long matched = jobSkillNames.stream()
                    .filter(userSkillNames::contains)
                    .count();

            double match = (matched / (double) jobSkillNames.size()) * 100.0;

            if (match >= 60.0) {
                JobRecommendationDto dto = new JobRecommendationDto();
                dto.setJobId(job.getId());
                dto.setJobTitle(job.getTitle());
                dto.setLocation(job.getLocation());
                dto.setMatchPercentage(Math.round(match * 100.0) / 100.0);
                result.add(dto);
            }
        }

        result.sort(Comparator.comparingDouble(JobRecommendationDto::getMatchPercentage)
                .reversed());

        return result;
    }
}