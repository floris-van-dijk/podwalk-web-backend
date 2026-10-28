package nl.npo.podwalks.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity(name = "FAQ")
public class FAQ {
    @Id
    @UuidGenerator
    private UUID id;

    private String question;
    private String answer;

    public FAQ() {}

    public FAQ(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public UUID getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
