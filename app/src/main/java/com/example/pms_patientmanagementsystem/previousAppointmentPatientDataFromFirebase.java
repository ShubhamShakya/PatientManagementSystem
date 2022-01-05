package com.example.pms_patientmanagementsystem;

public class previousAppointmentPatientDataFromFirebase {
    String doctorMail,date,prescription,disease;

    public String getDoctorMail() {
        return doctorMail;
    }

    public void setDoctorMail(String doctorMail) {
        this.doctorMail = doctorMail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }
}
