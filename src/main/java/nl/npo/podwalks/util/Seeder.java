package nl.npo.podwalks.util;

import nl.npo.podwalks.model.AppUser;
import nl.npo.podwalks.model.FAQ;
import nl.npo.podwalks.model.UserQuestion;
import nl.npo.podwalks.repository.FAQRepository;
import nl.npo.podwalks.repository.UserQuestionRepository;
import nl.npo.podwalks.repository.UserRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Seeder {

    private final UserRepository userRepository;
    private final FAQRepository faqRepository;
    private final UserQuestionRepository userQuestionRepository;

    public Seeder(UserRepository userRepository, FAQRepository faqRepository, UserQuestionRepository userQuestionRepository) {
        this.userRepository = userRepository;
        this.faqRepository = faqRepository;
        this.userQuestionRepository = userQuestionRepository;
    }

    @EventListener
    public void seeder(ContextRefreshedEvent event) {
        this.seedUser();
        this.seedFAQ();
        this.seedUserQuestion();
    }

    private void seedUser() {
        AppUser appUserAdmin = new AppUser();
        appUserAdmin.setUsername("John Doe");
        appUserAdmin.setEmail("johndoe@example.com");
        appUserAdmin.setPassword(new BCryptPasswordEncoder().encode("Wachtwoord123!!"));
        appUserAdmin.setRole("ADMIN");
        userRepository.save(appUserAdmin);

        AppUser appUserAdmin1 = new AppUser();
        appUserAdmin1.setUsername("Podwalks");
        appUserAdmin1.setEmail("beheerder@podwalks.nl");
        appUserAdmin1.setPassword(new BCryptPasswordEncoder().encode("Password123!!!"));
        appUserAdmin1.setRole("ADMIN");
        userRepository.save(appUserAdmin1);

        AppUser appUser1 = new AppUser();
        appUser1.setUsername("Podwalks");
        appUser1.setEmail("gebruiker@podwalks.nl");
        appUser1.setPassword(new BCryptPasswordEncoder().encode("Password123!!!"));
        appUser1.setRole("USER");
        userRepository.save(appUser1);

        AppUser appUserUser = new AppUser();
        appUserUser.setUsername("Jane Doe");
        appUserUser.setEmail("janedoe@example.com");
        appUserUser.setPassword(new BCryptPasswordEncoder().encode("Wachtwoord456!!"));
        userRepository.save(appUserUser);
    }

    private void seedFAQ(){
        FAQ faq1 = new FAQ();
        faq1.setQuestion("Hoe kan je de app downloaden?");
        faq1.setAnswer("Je kan de app downloaden via de Play Store voor Android of Apple Store voor IOS.");
        faqRepository.save(faq1);

        FAQ faq2 = new FAQ();
        faq2.setQuestion("Heb je WiFi nodig om de app te gebruiken?");
        faq2.setAnswer("Nee, dit is niet nodig! Download de podwalk vooraf en heb offline plezier!");
        faqRepository.save(faq2);

        FAQ faq3 = new FAQ();
        faq3.setQuestion("Op welke apparaten kan je de app gebruiken?");
        faq3.setAnswer("De app is momenteel alleen te gebruiken voor mobiel.");
        faqRepository.save(faq3);

        FAQ faq4 = new FAQ();
        faq4.setQuestion("Ik ben slecht ter been, kan ik de app ook gebruiken?");
        faq4.setAnswer("Zeker! Per podwalk wordt vermeld hoe toegankelijk deze is. " +
                "Desnoods kan de podwalk virtueel beleefd worden.");
        faqRepository.save(faq4);
    }

    private void seedUserQuestion() {
        UserQuestion userQuestion1 = new UserQuestion();
        userQuestion1.setUser(userRepository.findByEmail("janedoe@example.com").get());
        userQuestion1.setSubmittedAt(LocalDateTime.now());
        userQuestion1.setQuestion("De app werkt niet, wat kan ik doen?");
        userQuestion1.setAnswer("De beste manier om dit op te lossen is door de app opnieuw te downloaden, je gegevens zullen bewaard blijven!");
        userQuestion1.setViewed(true);
        userQuestionRepository.save(userQuestion1);

        UserQuestion userQuestion2 = new UserQuestion();
        userQuestion2.setUser(userRepository.findByEmail("janedoe@example.com").get());
        userQuestion2.setSubmittedAt(LocalDateTime.now());
        userQuestion2.setQuestion("Wanneer wordt een nieuwe podwalk gepubliceerd?");
        userQuestionRepository.save(userQuestion2);

        UserQuestion userQuestion3 = new UserQuestion();
        userQuestion3.setUser(userRepository.findByEmail("janedoe@example.com").get());
        userQuestion3.setSubmittedAt(LocalDateTime.now());
        userQuestion3.setQuestion("Hoe lang duurt een podwalk ongeveer?");
        userQuestionRepository.save(userQuestion3);
    }
}
