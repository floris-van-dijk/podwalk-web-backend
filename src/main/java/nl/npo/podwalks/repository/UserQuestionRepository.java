package nl.npo.podwalks.repository;

import nl.npo.podwalks.model.AppUser;
import nl.npo.podwalks.model.UserQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserQuestionRepository extends JpaRepository<UserQuestion, UUID> {
    List<UserQuestion> findAllByUser(AppUser user);
}
