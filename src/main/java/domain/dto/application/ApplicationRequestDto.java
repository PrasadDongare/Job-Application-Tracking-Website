package domain.dto.application;

import jakarta.validation.constraints.NotNull;

public class ApplicationRequestDto {

    @NotNull
    private Long jobId;

    // getters & setters
    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }
}