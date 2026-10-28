package nl.npo.podwalks.dto;

import nl.npo.podwalks.model.UserQuestion;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserQuestionDTO {
    public UUID uuid;
    public String question;
    public String answer;
    public boolean viewed;
    public LocalDateTime submittedAt;

    public static UserQuestionDTO fromEntity(UserQuestion entity) {
        UserQuestionDTO dto = new UserQuestionDTO();
        dto.uuid = entity.getUuid();
        dto.question = entity.getQuestion();
        dto.answer = entity.getAnswer();
        dto.viewed = entity.getViewed();
        dto.submittedAt = entity.getSubmittedAt();
        return dto;
    }
}