package controller.api;

import domain.dto.application.ApplicationRequestDto;
import domain.dto.application.ApplicationResponseDto;
import security.CustomUserDetails;
import service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/applications")
@PreAuthorize("hasRole('USER')")
public class ApplicationRestController {

    private final ApplicationService appService;

    public ApplicationRestController(ApplicationService appService) {
        this.appService = appService;
    }

    @PostMapping
    public ApplicationResponseDto apply(Authentication auth,
                                        @Valid @RequestBody ApplicationRequestDto dto) {
        Long userId = ((CustomUserDetails) auth.getPrincipal()).getId();
        return appService.applyForJob(userId, dto.getJobId());
    }

    @GetMapping
    public List<ApplicationResponseDto> myApps(Authentication auth) {
        Long userId = ((CustomUserDetails) auth.getPrincipal()).getId();
        return appService.getUserApplications(userId);
    }
}