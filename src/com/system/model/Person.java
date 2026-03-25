package com.system.model;

public class Person {
    //Instance Variables (The data this blueprint holds)
    //We make them 'private' so outside code can't accidentally mess them up.
    private int personId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;

    //Constructor (How we build a new Person object)
    public Person(int personId, String firstName, String lastName, String email, String phoneNo) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    //Getters and Setters (How outside code interacts with our private variables)
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