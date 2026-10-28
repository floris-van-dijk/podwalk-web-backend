package nl.npo.podwalks.repository;

import nl.npo.podwalks.model.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, UUID> {
}
