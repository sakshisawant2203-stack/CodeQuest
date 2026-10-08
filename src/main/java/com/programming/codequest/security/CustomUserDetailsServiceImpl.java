package com.programming.codequest.security;

import com.programming.codequest.entity.User;
import com.programming.codequest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    // basically tells Spring Security:
    // (in UserDetailsService)"Here is the username and password of my user in the format Spring Security understands."

    @Autowired
    private UserRepository userRepository;


    // Spring Security needs the user's stored password,It calls CustomUserDetailsServiceImpl
   // loadUserByUsername(String username)
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

      //  CustomUserDetailsServiceImpl says: "I'll find this user from MongoDB.
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(
                    "User not found with username : " + username
            );
        }

        return new CustomUserDetails(user);
    }
}
