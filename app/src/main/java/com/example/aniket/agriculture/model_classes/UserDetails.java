package com.example.aniket.agriculture.model_classes;

public class UserDetails {
    String firstName;
    String lastName;
    String userID;
    String emailID;

    public UserDetails(){
    }

    public UserDetails(String firstName, String lastName, String userID, String emailID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userID = userID;
        this.emailID = emailID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserID() {
        return userID;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }
}
