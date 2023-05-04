package com.caps.capstone.proj.Controller;


import com.caps.capstone.proj.Jwt.JwtToken;
import com.caps.capstone.proj.Repository.UserRepo;
import com.caps.capstone.proj.Service.EmailService;
import com.caps.capstone.proj.Service.UserService;
import com.caps.capstone.proj.model.LoginAuthRequest;
import com.caps.capstone.proj.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserRepo userRepo;

    @Autowired
    EmailService emailService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtToken jwtToken;



    @PostMapping("/signin")
    public String signin(@RequestBody User user)throws MessagingException,UnsupportedEncodingException{
        if(!userRepo.existsById(user.getEmail())){
            System.out.println(user);
            BCryptPasswordEncoder bcrypt= new BCryptPasswordEncoder();
            String encryptpwd=bcrypt.encode(user.getPassword());
            user.setPassword(encryptpwd);
            System.out.println(user.getPassword());
            user.setEnabled(false);
            userRepo.save(user);
            emailService.Register(user);
            return "Verification mail sended";

        }
        else {
            Optional<User> userDetails=userRepo.findById(user.getEmail());
            if (userDetails.get().getEnabled()) {
                return "Already exists.try alter";
            }
            else {
                return "Waiting for verification";
            }
        }
    }
@GetMapping("/verify")
    public String verifyUser(@RequestParam String code) {
        String[] paramList=code.split("-");
        String verifyCode=paramList[0];
        String email=paramList[1];
        Optional<User> user=userRepo.findById(email);

        if(user.get().getVerifyotp().equals(verifyCode) && user.get().getVerifyotp() != null) {
            user.get().setEnabled(true);
            user.get().setVerifyotp(null);
            userRepo.save(user.get());
            return "Account verified";
        }
        else {
            return "Invalid url";
        }
}

@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginAuthRequest loginAuthRequest) {
        String email=loginAuthRequest.getEmail();
        String password=loginAuthRequest.getPassword();
        if (userRepo.existsById(email)) {
            Optional<User> user=userRepo.findById(email);
            if (user.get().getEnabled()) {
                try {
                    System.out.println(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password)));
                }
                catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid credentials");
                }
                UserDetails userDetails=userService.loadUserByUsername(loginAuthRequest.getEmail());
                String jwt=jwtToken.generateToken(userDetails);
                return ResponseEntity.ok(jwt);
            }
            else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account is not enabled");
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account not found");
        }
}


}
