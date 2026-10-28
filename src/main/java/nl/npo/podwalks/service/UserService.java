package nl.npo.podwalks.service;

import jakarta.servlet.http.HttpServletRequest;
import nl.npo.podwalks.config.JWTUtil;
import nl.npo.podwalks.model.AppUser;
import nl.npo.podwalks.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final HttpServletRequest request;
    private final JWTUtil jwtUtil;

    public UserService(UserRepository userRepository, HttpServletRequest request, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.request = request;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }

    public AppUser getUser() {
        String authHeader = request.getHeader("Authorization");
        String jwt = authHeader.substring(7);
        String userEmail = this.jwtUtil.validateTokenAndRetrieveSubject(jwt);

        Optional<AppUser> optionalAppUser = userRepository.findByEmail(userEmail);

        if (optionalAppUser.isPresent()) return optionalAppUser.get();

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"
        );
    }
}
