package com.amblessed.universitymanagementsystem.service;



/*
 * @Project Name: spring-security-demo
 * @Author: Okechukwu Bright Onwumere
 * @Created: 05-Sep-24
 */


import com.amblessed.universitymanagementsystem.entity.User;
import com.amblessed.universitymanagementsystem.entity.UserPrincipal;
import com.amblessed.universitymanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
/*import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;*/
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService {
//public class MyUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;


    /*@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            System.out.printf("User with %s not found%n", email);
            throw new UsernameNotFoundException(email);
        }
        return new UserPrincipal(user);
    }*/
}
