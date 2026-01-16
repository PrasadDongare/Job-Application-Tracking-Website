package controller.web;

import domain.dto.job.JobRequestDto;
import domain.entity.enums.ApplicationStatus;
import service.ApplicationService;
import service.JobService;
import service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminWebController {

    private final JobService jobService;
    private final ApplicationService appService;
    private final UserService userService;

    public AdminWebController(JobService jobService,
                              ApplicationService appService,
                              UserService userService) {
        this.jobService = jobService;
        this.appService = appService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", userService.countUsers());
        model.addAttribute("totalApplications", appService.countApplications());
        model.addAttribute("byStatus", appService.countByStatus());
        return "admin/dashboard";
    }

    @GetMapping("/jobs")
    public String jobs(Model model, Pageable pageable) {
        model.addAttribute("jobs", jobService.listJobs(pageable));
        return "admin/jobs";
    }

    @GetMapping("/jobs/new")
    public String jobForm(Model model) {
        model.addAttribute("job", new JobRequestDto());
        return "admin/job-form";
    }

    @PostMapping("/jobs")
    public String createJob(@ModelAttribute("job") JobRequestDto dto) {
        jobService.createJob(dto);
        return "redirect:/admin/jobs";
    }

    @GetMapping("/applications")
    public String applications(Model model) {
        model.addAttribute("applications", appService.getAllApplications());
        model.addAttribute("statuses", ApplicationStatus.values());
        return "admin/applications";
    }
}