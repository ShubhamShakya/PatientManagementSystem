package com.example.pms_patientmanagementsystem;

public class patientDataFromFirebase {


    private String name;
    private String specialization;
    private String email;


    public patientDataFromFirebase(){

    }

    public patientDataFromFirebase(String name, String specialization, String email) {
        this.name = name;
        this.specialization = specialization;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
