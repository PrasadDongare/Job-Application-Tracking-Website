package controller.api;

import domain.dto.job.JobRecommendationDto;
import security.CustomUserDetails;
import service.RecommendationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/recommendations")
@PreAuthorize("hasRole('USER')")
public class RecommendationRestController {

    private final RecommendationService recService;

    public RecommendationRestController(RecommendationService recService) {
        this.recService = recService;
    }

    @GetMapping
    public List<JobRecommendationDto> recommend(Authentication auth) {
        Long userId = ((CustomUserDetails) auth.getPrincipal()).getId();
        return recService.recommendJobsForUser(userId);
    }
}