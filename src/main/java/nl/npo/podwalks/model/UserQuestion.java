package nl.npo.podwalks.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "UserQuestion")
public class UserQuestion {
    @Id
    @UuidGenerator
    private UUID uuid;

    private String question;
    private String answer;
    private Boolean viewed = false;
    private LocalDateTime submittedAt;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonBackReference
    private AppUser user;

    public UserQuestion() {}

    public UserQuestion(String question, String answer, Boolean viewed, AppUser user, LocalDateTime submittedAt) {
        this.question = question;
        this.answer = answer;
        this.viewed = viewed;
        this.user = user;
        this.submittedAt = submittedAt;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public AppUser getUser() {
        return user;
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

    public Boolean getViewed() {
        return viewed;
    }

    public void setViewed(Boolean viewed) {
        this.viewed = viewed;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
