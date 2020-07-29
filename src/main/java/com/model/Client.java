package com.model;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Client {
    private long id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String middleName;
    @NotNull
    @Size(min = 1)
    @Min(0)
    private long phoneNumber;


    public Client() {
    }

    public Client(long id, String name, String surname, String middleName, long phoneNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.phoneNumber = phoneNumber;
    }

    public Client(String name, String surname, String middleName, long phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.phoneNumber = phoneNumber;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return this.surname +" "+this.name +" "+  this.middleName+" "+phoneNumber;
    }
}
