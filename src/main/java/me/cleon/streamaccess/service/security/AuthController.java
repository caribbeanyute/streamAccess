package me.cleon.streamaccess.service.security;

import me.cleon.streamaccess.domain.User;
import me.cleon.streamaccess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private UserRepository userRepo;
    @Autowired private JWTUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Map<String, Object> registerHandler(@RequestBody User user){
        System.out.println(user.getPassword_hash());
        String encodedPass = passwordEncoder.encode(user.getPassword_hash());
        user.setCreated_date(Instant.now());
        user.setPassword_hash(encodedPass);
        user = userRepo.save(user);
        String token = jwtUtil.generateToken(user.getUsername());
        return Collections.singletonMap("jwt-token", token);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginHandler(@RequestBody JWTRequest body){
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword());

            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(body.getUsername());

            return ResponseEntity.ok().body(Collections.singletonMap("jwt-token", token));
        }catch (AuthenticationException authExc){
            //throw new RuntimeException("Invalid Login Credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");

        }
    }


}