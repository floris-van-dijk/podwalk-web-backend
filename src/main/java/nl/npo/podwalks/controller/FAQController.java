package nl.npo.podwalks.controller;

import nl.npo.podwalks.dao.FAQDAO;
import nl.npo.podwalks.dto.FAQDTO;
import nl.npo.podwalks.model.FAQ;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/faq")
public class FAQController {
    private final FAQDAO faqDAO;

    public FAQController(FAQDAO faqDAO) {this.faqDAO = faqDAO;}

    @GetMapping
    public ResponseEntity<List<FAQ>> getAllFAQs() {
        return ResponseEntity.ok(this.faqDAO.getAllFAQs());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<FAQDTO> createFAQ(@RequestBody FAQDTO faqDto) {
        FAQDTO newFaq = faqDAO.createFAQ(faqDto);
        return new ResponseEntity<>(newFaq, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> updateFAQ(@RequestBody FAQDTO faqdto) {
        this.faqDAO.updateFAQ(faqdto);
        return ResponseEntity.ok("FAQ updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFAQ(@PathVariable UUID id) {
        this.faqDAO.deleteFAQ(id);
        return ResponseEntity.ok("FAQ deleted successfully");
    }

}
