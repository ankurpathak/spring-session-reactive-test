package com.ankurpathak.springsessionreactivetest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class CustomReactiveUserDetailsService implements ReactiveUserDetailsService {

    public static final String USERNAME_NOT_FOUND_MESSAGE = "Username %s not found.";
    private final IReactiveUserService userService;

    public CustomReactiveUserDetailsService(IReactiveUserService userService) {
        this.userService = userService;
    }


    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userService.possibleCandidateKeys(username)
                .flatMapIterable(Map::entrySet)
                .map(entry -> Criteria.where(KEY_MAPPINGS.get(entry.getKey())).is(entry.getValue()))
                .mergeWith(userService.possibleContacts(username).map(contact -> Criteria.where(KEY_CONTACT).is(contact)))
                .collectList()
                .filter(CollectionUtils::isNotEmpty)
                .map(criteriaList -> new Criteria().orOperator(criteriaList.toArray(new Criteria[]{})))
                .flatMapMany(criteria -> userService.findByCriteria(criteria, PageRequest.of(0, 1), User.class))
                .next()
                .switchIfEmpty(Mono.error(new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND_MESSAGE, username))))
                .map(CustomUserDetails::new);
    }


    public static final String KEY_EMAIL = "email.value";
    public static final String KEY_CONTACT = "contact.value";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_ID = "_id";


    public static final Map<String, String> KEY_MAPPINGS = new LinkedHashMap<>();

    static {
        KEY_MAPPINGS.put(Params.ID, KEY_ID);
        KEY_MAPPINGS.put(Params.USERNAME, KEY_USERNAME);
        KEY_MAPPINGS.put(Params.EMAIL, KEY_EMAIL);
        KEY_MAPPINGS.put(Params.CONTACT, KEY_CONTACT);
    }


    public static final List<User> users = new ArrayList<>();


    static {
        users.add(User.getInstance().id(BigInteger.ONE.add(BigInteger.ONE)).firstName("Ankur").lastName("Pathak").addRole(Role.ROLE_ADMIN).email(Contact.getInstance("ankurpathak@live.in")).password(Password.getInstance().value("password")));
        users.add(User.getInstance().id(BigInteger.ONE.add(BigInteger.ONE).add(BigInteger.ONE)).firstName("Amar").lastName("Mule").addRole(Role.ROLE_ADMIN).email(Contact.getInstance("amarmule@live.in")).password(Password.getInstance().value("password")));
    }
}
