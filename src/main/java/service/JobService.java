package service;

import domain.dto.job.JobRequestDto;
import domain.dto.job.JobResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobService {
    JobResponseDto createJob(JobRequestDto dto);
    JobResponseDto updateJob(Long id, JobRequestDto dto);
    void deleteJob(Long id);
    JobResponseDto getJob(Long id);
    Page<JobResponseDto> listJobs(Pageable pageable);
}