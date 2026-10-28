package nl.npo.podwalks.dto;

import nl.npo.podwalks.model.FAQ;

import java.util.UUID;

public class FAQDTO {
    public UUID id;
    public String question;
    public String answer;

    public static FAQDTO fromEntity(FAQ entity) {
        FAQDTO dto = new FAQDTO();
        dto.id = entity.getId();
        dto.question = entity.getQuestion();
        dto.answer = entity.getAnswer();
        return dto;
    }
}
