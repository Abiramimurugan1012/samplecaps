package com.caps.capstone.proj.Service;

import com.caps.capstone.proj.Repository.UserRepo;
import com.caps.capstone.proj.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"Userlogin"})
public class UserService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    @Cacheable(key = "#email")


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> founduser=userRepo.findById(email);
        if (founduser.isEmpty()) {
            return null;
        }
        String emailId=founduser.get().getEmail();
        String pwd=founduser.get().getPassword();
        return new org.springframework.security.core.userdetails.User(emailId,pwd,new ArrayList<>());
    }
}
