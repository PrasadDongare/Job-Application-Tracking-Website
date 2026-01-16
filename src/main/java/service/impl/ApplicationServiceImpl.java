package service.impl;

import domain.dto.application.ApplicationResponseDto;
import domain.entity.Application;
import domain.entity.Job;
import domain.entity.User;
import domain.entity.enums.ApplicationStatus;
import exception.BadRequestException;
import exception.ResourceNotFoundException;
import mapper.ApplicationMapper;
import repository.ApplicationRepository;
import repository.JobRepository;
import repository.UserRepository;
import service.ApplicationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository appRepo;
    private final UserRepository userRepo;
    private final JobRepository jobRepo;
    private final ApplicationMapper appMapper;

    public ApplicationServiceImpl(ApplicationRepository appRepo,
                                  UserRepository userRepo,
                                  JobRepository jobRepo,
                                  ApplicationMapper appMapper) {
        this.appRepo = appRepo;
        this.userRepo = userRepo;
        this.jobRepo = jobRepo;
        this.appMapper = appMapper;
    }

    @Override
    @Transactional
    public ApplicationResponseDto applyForJob(Long userId, Long jobId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Job job = jobRepo.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        // Check existing application
        List<Application> existing = appRepo.findByUserId(userId);
        boolean alreadyApplied = existing.stream()
                .anyMatch(a -> a.getJob().getId().equals(jobId));
        if (alreadyApplied) {
            throw new BadRequestException("You have already applied to this job");
        }

        Application app = new Application();
        app.setUser(user);
        app.setJob(job);
        app.setStatus(ApplicationStatus.APPLIED);

        appRepo.save(app);
        return appMapper.toResponseDto(app);
    }

    @Override
    public List<ApplicationResponseDto> getUserApplications(Long userId) {
        List<Application> apps = appRepo.findByUserId(userId);
        List<ApplicationResponseDto> dtos = new ArrayList<>();
        for (Application a : apps) {
            dtos.add(appMapper.toResponseDto(a));
        }
        return dtos;
    }

    @Override
    public List<ApplicationResponseDto> getAllApplications() {
        List<Application> apps = appRepo.findAll();
        List<ApplicationResponseDto> dtos = new ArrayList<>();
        for (Application a : apps) {
            dtos.add(appMapper.toResponseDto(a));
        }
        return dtos;
    }

    @Override
    @Transactional
    public ApplicationResponseDto updateStatus(Long applicationId, ApplicationStatus status) {
        Application app = appRepo.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        app.setStatus(status);
        appRepo.save(app);
        // optional: send email notification here
        return appMapper.toResponseDto(app);
    }

    @Override
    public long countApplications() {
        return appRepo.count();
    }

    @Override
    public Map<ApplicationStatus, Long> countByStatus() {
        Map<ApplicationStatus, Long> map = new LinkedHashMap<>();
        for (ApplicationStatus st : ApplicationStatus.values()) {
            map.put(st, appRepo.countByStatus(st));
        }
        return map;
    }
}