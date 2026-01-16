package controller.web;

import security.CustomUserDetails;
import service.ApplicationService;
import service.JobService;
import service.RecommendationService;
import service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UserWebController {

    private final JobService jobService;
    private final ApplicationService appService;
    private final RecommendationService recService;
    private final UserService userService;

    public UserWebController(JobService jobService,
                             ApplicationService appService,
                             RecommendationService recService,
                             UserService userService) {
        this.jobService = jobService;
        this.appService = appService;
        this.recService = recService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        Long userId = ((CustomUserDetails)auth.getPrincipal()).getId();
        model.addAttribute("recommendations", recService.recommendJobsForUser(userId));
        model.addAttribute("applications", appService.getUserApplications(userId));
        return "user/dashboard";
    }

    @GetMapping("/jobs")
    public String jobs(Model model, Pageable pageable) {
        model.addAttribute("jobs", jobService.listJobs(pageable));
        return "user/jobs";
    }

    @GetMapping("/applications")
    public String applications(Model model, Authentication auth) {
        Long userId = ((CustomUserDetails)auth.getPrincipal()).getId();
        model.addAttribute("applications", appService.getUserApplications(userId));
        return "user/applications";
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication auth) {
        model.addAttribute("profile", userService.getProfileByEmail(auth.getName()));
        return "user/profile";
    }
}