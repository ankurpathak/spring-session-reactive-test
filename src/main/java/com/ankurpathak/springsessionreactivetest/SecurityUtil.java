package com.ankurpathak.springsessionreactivetest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class SecurityUtil {
    public static Mono<User> getMe() {
        return getCustomUserDetails().map(CustomUserDetails::getUser);
    }













    /*


    public static String getRequestedOrganization(){
        if (getDomainContext().isPresent()) {
            return getDomainContext().get().getRequestedOrganization();
        }
        return null;
    }

    public static String getRequestedUser(){
        if (getDomainContext().isPresent()) {
            return getDomainContext().get().getRequestedUser();
        }
        return null;
    }

    */


    public static Mono<DomainContext> getDomainContext() {
        return ReactiveDomainContextHolder.getContext();
    }




    public static Mono<Authentication> getAuthentication() {
        return getSecurityContext().map(SecurityContextImpl::getAuthentication);
    }



    public static Mono<CustomUserDetails> getCustomUserDetails() {
        return getAuthentication()
                .map(Authentication::getPrincipal)
                .ofType(CustomUserDetails.class);
    }





    public static Mono<ExtendedSecurityContextImpl> getSecurityContext() {
        return ReactiveSecurityContextHolder.getContext()
                .filter(ExtendedSecurityContextImpl.class::isInstance)
                .map(ExtendedSecurityContextImpl.class::cast);
    }




/*


    public static String getOrganization() {
        String organizationId = getRequestedOrganization();
        if(StringUtils.isBlank(organizationId))
            organizationId = getMeOrganization();
        if(StringUtils.isBlank(organizationId))
            organizationId = "0";
        return organizationId;
    }


    public static String getMeOrganization(){
        Optional<User> me = getMe();
        if(me.isPresent() && me.get().getOrganization() != null)
            return String.valueOf(me.get().getOrganization().getId());
        return null;
    }

    public static boolean isMyOrganization(String myOrganizationId){
        return getOrganization().equals(myOrganizationId);
    }

    public static String getUser(){
        String userId = getRequestedUser();
        if(StringUtils.isBlank(userId)){
            userId = getMeId();
        }
        if(StringUtils.isBlank(userId))
            userId = "0";
        return userId;
    }








    public static Boolean isMe(String meId){
        return getUser().equals(meId);
    }




    public static String getMeId(){
        Optional<User> me = getMe();
        if(me.isPresent()  && me.get().getId()!= null)
            return String.valueOf(me.get().getId());
        else return null;
    }

    */


}
