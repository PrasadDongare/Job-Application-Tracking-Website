package controller.api;

import domain.dto.user.UserProfileDto;
import service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER')")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public UserProfileDto getProfile(Authentication auth) {
        return userService.getProfileByEmail(auth.getName());
    }

    @PutMapping("/profile")
    public UserProfileDto updateProfile(Authentication auth,
                                        @Valid @RequestBody UserProfileDto dto) {
        return userService.updateProfile(auth.getName(), dto);
    }

    @PostMapping("/skills")
    public UserProfileDto updateSkills(Authentication auth,
                                       @RequestBody List<String> skills) {
        return userService.updateSkills(auth.getName(), skills);
    }
}