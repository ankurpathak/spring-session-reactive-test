package com.ankurpathak.springsessionreactivetest;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CustomReactiveUserDetailsService implements ReactiveUserDetailsService {

    public static final String USERNAME_NOT_FOUND_MESSAGE = "Username %s not found.";


    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return users.stream()
                .filter(user -> Objects.equals(username, user.getEmail()) || Objects.equals(username, String.valueOf(user.getId())))
                .findFirst()
                .map(CustomUserDetails::new)
                .map(x-> (UserDetails)x)
                .map(Mono::just)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND_MESSAGE, username)));
    }


    public static final List<User> users = new ArrayList<>();


    static {
        users.add(User.getInstance().id("1").firstName("Ankur").lastName("Pathak").addRole(Role.ROLE_ADMIN).email("ankurpathak@live.in").password("password"));
        users.add(User.getInstance().id("2").firstName("Amar").lastName("Mule").addRole(Role.ROLE_ADMIN).email("amarmule@live.in").password("password"));
    }
}