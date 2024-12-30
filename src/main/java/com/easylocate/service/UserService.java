package com.easylocate.service;

import com.easylocate.model.User;
import com.easylocate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    @SuppressWarnings("unused")
    private UserRepository userRepository;

    public Optional<User> findUserById(@NonNull Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User saveUser(@javax.validation.Valid User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User with specified username: " + username + " doesn't exist");
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new org.springframework.security.core.userdetails.User(
                user.get().getUsername(), user.get().getPassword(), enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, getAuthorities(Collections.singletonList("ROLE_USER")));
    }

    private static List<GrantedAuthority> getAuthorities (Collection<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    public User processOAuth2User(OAuth2User oauth2User) {
        String email = oauth2User.getAttribute("email");
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setFirstName("");
                    newUser.setLastName("");
                    newUser.setEmail(email);
                    newUser.setUsername(generateUsernameFromEmail(Objects.requireNonNull(email)));
                    newUser.setPassword("");
                    return userRepository.save(newUser);
                });
    }

    public User processOidcUser(OidcUser oidcUser) {
        String email = oidcUser.getEmail();
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setFirstName("");
                    newUser.setLastName("");
                    newUser.setEmail(email);
                    newUser.setUsername(generateUsernameFromEmail(email));
                    newUser.setPassword("");
                    return userRepository.save(newUser);
                });
    }

    private String generateUsernameFromEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
