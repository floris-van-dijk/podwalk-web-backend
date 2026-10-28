package nl.npo.podwalks.dao;

import jakarta.transaction.Transactional;
import nl.npo.podwalks.dto.FAQDTO;
import nl.npo.podwalks.model.FAQ;
import nl.npo.podwalks.repository.FAQRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class FAQDAO {

    private final FAQRepository faqRepository;

    public FAQDAO(FAQRepository faqRepository) {this.faqRepository = faqRepository;}

    public List<FAQ> getAllFAQs() {return faqRepository.findAll();}

    @Transactional
    public FAQDTO createFAQ(FAQDTO faqDTO) {
        FAQ faq = new FAQ(
                faqDTO.question,
                faqDTO.answer
        );
        this.faqRepository.save(faq);
        return FAQDTO.fromEntity(faq);
    }

    @Transactional
    public void updateFAQ(FAQDTO faqDTO) {
        Optional<FAQ> optionalFAQ = this.faqRepository.findById(faqDTO.id);

        if (optionalFAQ.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "FAQ not found"
            );
        }

        FAQ newFAQ = optionalFAQ.get();
        newFAQ.setQuestion(faqDTO.question);
        newFAQ.setAnswer(faqDTO.answer);
        this.faqRepository.save(newFAQ);
    }

    @Transactional
    public void deleteFAQ(UUID id) {
        Optional<FAQ> optionalFAQ = this.faqRepository.findById(id);

        if (optionalFAQ.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "FAQ not found"
            );
        }

        this.faqRepository.delete(optionalFAQ.get());
    }
}