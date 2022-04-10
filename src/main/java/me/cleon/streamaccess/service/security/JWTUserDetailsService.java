package me.cleon.streamaccess.service.security;

import me.cleon.streamaccess.domain.User;
import me.cleon.streamaccess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class JWTUserDetailsService implements UserDetailsService {

    @Autowired private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userRes = userRepo.findByUsername(username);
        if(userRes.isEmpty())
            throw new UsernameNotFoundException("Could not findUser with email = " + username);
        User user = userRes.get();
        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword_hash(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}