package com.caps.capstone.proj.Service;

import com.caps.capstone.proj.Repository.UserRepo;
import com.caps.capstone.proj.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;
@Service
public class EmailService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    JavaMailSender mailSender;

    public void Register(User user) throws MessagingException, UnsupportedEncodingException {
        Random r=new Random();
        int n=r.nextInt();
        String code=Integer.toHexString(n);
        user.setVerifyotp(code);
        userRepo.save(user);
        sendverificationmail(user,code);
    }

    public void sendverificationmail(User user,String code) throws MessagingException,UnsupportedEncodingException {
        String toAddress=user.getEmail();
        String fromAddress="muruganabirami476@gmail.com";
        String SenderName="Capstone";
        String Subject="Please verify your registeration";
        String Content="Dear [[name]],<br>"
                +"Please Click the link below to verify your registeration:<br><br>"
                +"<h2><a href=\"[[URL]]\" target=\"_self\">Click me to verify</a></h3><br><br>"
                +"Thank you!<br>"
                +"Capstone.";

        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message);
        helper.setFrom(fromAddress,SenderName);
        helper.setTo(toAddress);
        helper.setSubject(Subject);
        Content=Content.replace("[[name]]",user.getFname()+" "+user.getLname());
        String verifyUrl="http://127.0.0.1:9090/api/verify?code=" + code+"-"+user.getEmail();
        Content=Content.replace("[[URL]]",verifyUrl);
        helper.setText(Content,true);
        mailSender.send(message);


    }

}
