package service.impl;

import domain.dto.job.JobRequestDto;
import domain.dto.job.JobResponseDto;
import domain.entity.Job;
import domain.entity.Skill;
import exception.ResourceNotFoundException;
import mapper.JobMapper;
import repository.JobRepository;
import repository.SkillRepository;
import service.JobService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepo;
    private final SkillRepository skillRepo;
    private final JobMapper jobMapper;

    public JobServiceImpl(JobRepository jobRepo,
                          SkillRepository skillRepo,
                          JobMapper jobMapper) {
        this.jobRepo = jobRepo;
        this.skillRepo = skillRepo;
        this.jobMapper = jobMapper;
    }

    @Override
    @Transactional
    public JobResponseDto createJob(JobRequestDto dto) {
        Job job = new Job();
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setExperienceLevel(dto.getExperienceLevel());
        job.setLocation(dto.getLocation());

        job.getRequiredSkills().clear();
        dto.getRequiredSkills().forEach(s -> {
            String trimmed = s.trim();
            if (trimmed.isEmpty()) return;
            Skill skill = skillRepo.findByNameIgnoreCase(trimmed)
                    .orElseGet(() -> skillRepo.save(new Skill(null, trimmed)));
            job.getRequiredSkills().add(skill);
        });

        jobRepo.save(job);
        return jobMapper.toResponseDto(job);
    }

    @Override
    @Transactional
    public JobResponseDto updateJob(Long id, JobRequestDto dto) {
        Job job = jobRepo.findWithSkillsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setExperienceLevel(dto.getExperienceLevel());
        job.setLocation(dto.getLocation());

        job.getRequiredSkills().clear();
        dto.getRequiredSkills().forEach(s -> {
            String trimmed = s.trim();
            if (trimmed.isEmpty()) return;
            Skill skill = skillRepo.findByNameIgnoreCase(trimmed)
                    .orElseGet(() -> skillRepo.save(new Skill(null, trimmed)));
            job.getRequiredSkills().add(skill);
        });

        jobRepo.save(job);
        return jobMapper.toResponseDto(job);
    }

    @Override
    @Transactional
    public void deleteJob(Long id) {
        if (!jobRepo.existsById(id)) {
            throw new ResourceNotFoundException("Job not found");
        }
        jobRepo.deleteById(id);
    }

    @Override
    public JobResponseDto getJob(Long id) {
        Job job = jobRepo.findWithSkillsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
        return jobMapper.toResponseDto(job);
    }

    @Override
    public Page<JobResponseDto> listJobs(Pageable pageable) {
        Page<Job> page = jobRepo.findAll(pageable);
        return page.map(jobMapper::toResponseDto);
    }
}