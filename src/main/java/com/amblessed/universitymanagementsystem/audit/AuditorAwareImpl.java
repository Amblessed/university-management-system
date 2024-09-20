package com.amblessed.universitymanagementsystem.audit;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 07-Sep-24
 */


import org.springframework.data.domain.AuditorAware;
/*import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;*/

import java.util.Optional;

public class AuditorAwareImpl {
//public class AuditorAwareImpl implements AuditorAware<String> {

    //@Override
    /*public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken){
               return Optional.of("Bright Onwumere");
        }
        User userPrincipal = (User)authentication.getPrincipal();
        return Optional.ofNullable(userPrincipal.getUsername());
    }*/
}
