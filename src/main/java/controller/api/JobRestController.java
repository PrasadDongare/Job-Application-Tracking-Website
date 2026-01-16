package controller.api;

import domain.dto.job.JobRequestDto;
import domain.dto.job.JobResponseDto;
import service.JobService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/jobs")
@PreAuthorize("hasRole('ADMIN')")
public class JobRestController {

    private final JobService jobService;

    public JobRestController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public JobResponseDto create(@Valid @RequestBody JobRequestDto dto) {
        return jobService.createJob(dto);
    }

    @PutMapping("/{id}")
    public JobResponseDto update(@PathVariable Long id,
                                 @Valid @RequestBody JobRequestDto dto) {
        return jobService.updateJob(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        jobService.deleteJob(id);
    }

    @GetMapping("/{id}")
    public JobResponseDto get(@PathVariable Long id) {
        return jobService.getJob(id);
    }

    @GetMapping
    public Page<JobResponseDto> list(Pageable pageable) {
        return jobService.listJobs(pageable);
    }
}