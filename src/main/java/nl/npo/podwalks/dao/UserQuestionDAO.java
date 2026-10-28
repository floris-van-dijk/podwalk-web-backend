package nl.npo.podwalks.dao;

import nl.npo.podwalks.dto.CreateUserQuestionDTO;
import nl.npo.podwalks.dto.UserQuestionDTO;
import nl.npo.podwalks.model.AppUser;
import nl.npo.podwalks.model.UserQuestion;
import nl.npo.podwalks.repository.UserQuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserQuestionDAO {
    private final UserQuestionRepository userQuestionRepository;

    public UserQuestionDAO(UserQuestionRepository userQuestionRepository) {
        this.userQuestionRepository = userQuestionRepository;
    }

    public List<UserQuestionDTO> getAllUserQuestions() {
        return userQuestionRepository.findAll()
                .stream()
                .map(UserQuestionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<UserQuestionDTO> getUserQuestionsByUser(AppUser user) {
        return userQuestionRepository.findAllByUser(user)
                .stream()
                .map(UserQuestionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public UserQuestionDTO createUserQuestion(CreateUserQuestionDTO createDto, AppUser user) {
        UserQuestion userQuestion = new UserQuestion();
        userQuestion.setQuestion(createDto.question);
        userQuestion.setSubmittedAt(LocalDateTime.now());
        userQuestion.setUser(user);
        userQuestion.setViewed(false);

        UserQuestion savedQuestion = userQuestionRepository.save(userQuestion);
        return UserQuestionDTO.fromEntity(savedQuestion);
    }

    public UserQuestionDTO updateUserQuestion(UUID questionUuid, UserQuestionDTO updateDto, AppUser loggedInUser) {
        if (!"ADMIN".equals(loggedInUser.getRole())) {
            throw new AccessDeniedException("Only admins can answer questions.");
        }

        UserQuestion question = userQuestionRepository.findById(questionUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found with UUID: " + questionUuid));

        question.setAnswer(updateDto.answer);
        question.setViewed(false);

        UserQuestion updatedQuestion = userQuestionRepository.save(question);
        return UserQuestionDTO.fromEntity(updatedQuestion);
    }

    public void deleteUserQuestion(UUID uuid, AppUser user) {
        UserQuestion question = userQuestionRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found"));

        if (!"ADMIN".equals(user.getRole()) && !question.getUser().getUuid().equals(user.getUuid())) {
            throw new AccessDeniedException("User is not allowed to delete this question");
        }

        userQuestionRepository.delete(question);
    }
}