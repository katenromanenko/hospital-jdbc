package com.example.hospital.entity;

public class Patient {
    private long id;
    private String firstName;
    private String lastName;
    private String diagnosis;
    private int ward;
    private int age;

    public Patient(){}

    public Patient(long id, String firstName, String lastName, String diagnosis, int ward, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.diagnosis = diagnosis;
        this.ward = ward;
        this.age = age;
    }

    public Patient(String firstName, String lastName, String diagnosis, int ward, int age) {
        this(0, firstName, lastName, diagnosis, ward, age);
    }

    public long getId() { return id;}
    public void setId(long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public int getWard() { return ward; }
    public void setWard(int ward) { this.ward = ward; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    @Override
    public String toString() {
        return "Patient {" +
                " id = " + id +
                ", firstName= '" + firstName + '\'' +
                ", lastName= '" + lastName
                + ", diagnosis= '" + diagnosis + '\'' +
                ", ward= '" + ward  +
                ", age= '" + age  +
                '}';

    }
}
