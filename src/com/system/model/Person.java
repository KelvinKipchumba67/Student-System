package com.system.model;

public class Person {
    //instance variables
    private int personId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;

    //Constructor
    public Person(int personId, String firstName, String lastName, String email, String phoneNo) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    //Getters and Setters
    public int getPersonId() {
        return personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }
}