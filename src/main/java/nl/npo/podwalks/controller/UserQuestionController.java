package nl.npo.podwalks.controller;

import nl.npo.podwalks.dao.UserQuestionDAO;
import nl.npo.podwalks.dto.CreateUserQuestionDTO;
import nl.npo.podwalks.dto.UserQuestionDTO;
import nl.npo.podwalks.model.AppUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/faq/user")
public class UserQuestionController {
    private final UserQuestionDAO userQuestionDAO;

    public UserQuestionController(UserQuestionDAO userQuestionDAO) {
        this.userQuestionDAO = userQuestionDAO;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserQuestionDTO>> getAllUserQuestions() {
        List<UserQuestionDTO> questions = this.userQuestionDAO.getAllUserQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping
    public ResponseEntity<List<UserQuestionDTO>> getMyUserQuestions(@AuthenticationPrincipal AppUser user) {
        List<UserQuestionDTO> questions = this.userQuestionDAO.getUserQuestionsByUser(user);
        return ResponseEntity.ok(questions);
    }

    @PostMapping
    public ResponseEntity<UserQuestionDTO> createUserQuestion(
            @RequestBody CreateUserQuestionDTO createDto,
            @AuthenticationPrincipal AppUser user
    ) {
        UserQuestionDTO newQuestion = this.userQuestionDAO.createUserQuestion(createDto, user);
        return new ResponseEntity<>(newQuestion, HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<UserQuestionDTO> updateUserQuestion(
            @PathVariable UUID uuid,
            @RequestBody UserQuestionDTO updateDto,
            @AuthenticationPrincipal AppUser user
    ) {
        UserQuestionDTO updatedQuestion = this.userQuestionDAO.updateUserQuestion(uuid, updateDto, user);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteUserQuestion(
            @PathVariable UUID uuid,
            @AuthenticationPrincipal AppUser user
    ) {
        this.userQuestionDAO.deleteUserQuestion(uuid, user);
        return ResponseEntity.noContent().build();
    }
}