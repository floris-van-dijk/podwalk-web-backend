package nl.npo.podwalks.controller;

import nl.npo.podwalks.config.JWTUtil;
import nl.npo.podwalks.dto.AuthDTO;
import nl.npo.podwalks.dto.LoginResponse;
import nl.npo.podwalks.model.AppUser;
import nl.npo.podwalks.repository.UserRepository;
import nl.npo.podwalks.service.CredentialValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final CredentialValidator validator;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;

    public AuthController(UserRepository userRepository,
                          CredentialValidator validator,
                          PasswordEncoder passwordEncoder,
                          JWTUtil jwtUtil,
                          AuthenticationManager authManager) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody AuthDTO authDTO) {
        if (!validator.isValidEmail(authDTO.email)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No valid email provided"
            );
        }

        if (!validator.isValidPassword(authDTO.password)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No valid password provided"
            );
        }

        Optional<AppUser> appUser = userRepository.findByEmail(authDTO.email);

        if (appUser.isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Can not register with this email"
            );
        }
        String encodedPassword = passwordEncoder.encode(authDTO.password);

        AppUser registeredAppUser = new AppUser(authDTO.email, authDTO.username, encodedPassword);
        userRepository.save(registeredAppUser);

        String token = jwtUtil.generateToken(registeredAppUser.getEmail());
        LoginResponse loginResponse = new LoginResponse(
                registeredAppUser.getEmail(),
                registeredAppUser.getUsername(),
                registeredAppUser.getRole(),
                token
        );
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthDTO body) {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.email, body.password);

            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(body.email);

            Optional<AppUser> AppUser = userRepository.findByEmail(body.email);
            if (AppUser.isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"
                );
            }
            LoginResponse loginResponse = new LoginResponse(
                    AppUser.get().getEmail(),
                    AppUser.get().getUsername(),
                    AppUser.get().getRole(),
                    token
            );

            return ResponseEntity.ok(loginResponse);

        } catch (AuthenticationException authExc) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "No valid credentials"
            );
        }
    }
}
