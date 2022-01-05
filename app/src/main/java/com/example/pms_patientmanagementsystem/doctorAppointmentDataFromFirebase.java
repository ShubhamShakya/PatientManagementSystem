package com.example.pms_patientmanagementsystem;

public class doctorAppointmentDataFromFirebase {
    String patientMail,time,date,disease;

    public doctorAppointmentDataFromFirebase(){

    }

    public doctorAppointmentDataFromFirebase(String patientMail, String time, String date,String disease) {
        this.patientMail = patientMail;
        this.time = time;
        this.date = date;
        this.disease=disease;
    }

    public String getPatientMail() {
        return patientMail;
    }

    public void setPatientMail(String patientMail) {
        this.patientMail = patientMail;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
