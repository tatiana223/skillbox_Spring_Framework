package org.example.model;

public class Contact {
    private String fullName;
    private String phoneNumber;
    private String email;

    public Contact() {

    }

    public Contact(String fullName, String phoneNumber, String email) {
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return fullName + " | " + phoneNumber + " | " + email;
    }

    public String toFileString() {
        return fullName + ";" + phoneNumber + ";" + email;
    }



}
