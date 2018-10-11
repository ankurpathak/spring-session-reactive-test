package com.ankurpathak.springsessionreactivetest;

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
import java.util.List;

@Component
public class CustomReactiveUserDetailsService implements ReactiveUserDetailsService {

    public static final String USERNAME_NOT_FOUND_MESSAGE = "Username %s not found.";
    private final IReactiveUserRepository userRepository;

    public CustomReactiveUserDetailsService(IReactiveUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Mono<UserDetails> findByUsername(String username) {
        List<Criteria> criteriaList = new ArrayList<>();
        if (!StringUtils.isNumeric(username)) {
            criteriaList.add(Criteria.where("email.value").is(username));
            criteriaList.add(Criteria.where("username").is(username));
        } else {
            criteriaList.add(Criteria.where("_id").is(PrimitiveUtils.toBigInteger(username)));
            criteriaList.add(Criteria.where("contact.value").is(username));
            /*
            SecurityUtil.getDomainContext()
                    .map(DomainContext::getRemoteAddress)
                    .flatMap(ipService::ipToCountryAlphaCode)
                    .map(countryService::alphaCodeToCallingCodes)
                    .ifPresent(callingCodes -> {
                        callingCodes.stream()
                                .map(callingCode->String.format("+%s%s",callingCode, username))
                                .forEach(contact -> criteriaList.add(Criteria.where("contact.value").is(contact)));
                    });
                    */
        }

        Criteria criteria = new Criteria().orOperator(criteriaList.toArray(new Criteria[]{}));
        return userRepository.findByCriteria(criteria, PageRequest.of(0, 1), User.class)
                .next()
                .switchIfEmpty(Mono.error(new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND_MESSAGE,username))))
                .map(CustomUserDetails::new);
    }


    public static final List<User> users = new ArrayList<>();


    static {
        users.add(User.getInstance().id(BigInteger.ONE.add(BigInteger.ONE)).firstName("Ankur").lastName("Pathak").addRole(Role.ROLE_ADMIN).email(Contact.getInstance("ankurpathak@live.in")).password(Password.getInstance().value("password")));
        users.add(User.getInstance().id(BigInteger.ONE.add(BigInteger.ONE).add(BigInteger.ONE)).firstName("Amar").lastName("Mule").addRole(Role.ROLE_ADMIN).email(Contact.getInstance("amarmule@live.in")).password(Password.getInstance().value("password")));
    }
}
