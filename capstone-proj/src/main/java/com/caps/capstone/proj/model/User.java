package com.caps.capstone.proj.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@Document(collection="login")
public class User {

    @Id
    private String email;
    private String fname;
    private String lname;
    private String password;
    private String gender;
    private String dob;
    private Boolean enabled= false;

    private String provider="LOCAL";
    private String role="User";
    private String verifyotp;
    private ArrayList<String> friends=new ArrayList<>();
    private ArrayList<String> friendrequest=new ArrayList<>();
    private ArrayList<String> categories;


    public User(String email, String fname, String lname, String password, String gender, String dob, Boolean enabled, String provider, String role, String verifyotp, ArrayList<String> friends, ArrayList<String> friendrequest, ArrayList<String> categories) {
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.password = password;
        this.gender = gender;
        this.dob = dob;
        this.enabled = enabled;
        this.provider = provider;
        this.role = role;
        this.verifyotp = verifyotp;
        this.friends = friends;
        this.friendrequest = friendrequest;
        this.categories = categories;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getVerifyotp() {
        return verifyotp;
    }

    public void setVerifyotp(String verifyotp) {
        this.verifyotp = verifyotp;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public ArrayList<String> getFriendrequest() {
        return friendrequest;
    }

    public void setFriendrequest(ArrayList<String> friendrequest) {
        this.friendrequest = friendrequest;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }
}
