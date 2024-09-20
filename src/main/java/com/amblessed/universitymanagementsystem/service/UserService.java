package com.amblessed.universitymanagementsystem.service;



/*
 * @Project Name: spring-security-demo
 * @Author: Okechukwu Bright Onwumere
 * @Created: 05-Sep-24
 */



import com.amblessed.universitymanagementsystem.entity.User;
import com.amblessed.universitymanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
/*import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;*/
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    //private final BCryptPasswordEncoder passwordEncoder;
    //private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;


    /*public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String verify(User user) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getEmail());
        }
        return "User is not authenticated";
    }*/
}
