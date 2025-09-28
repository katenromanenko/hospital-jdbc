package com.example.hospital.repository;

import com.example.hospital.entity.Patient;
import java.util.List;
import java.util.Optional;

public interface PatientRepository {
    // 7.1
    List<Patient> getAll();
    // 5.
    long insert(Patient p);
    // 6.
    boolean deleteById(long id);
    // 7.2
    List<Patient> findByWard(int ward);
    // 7.3
    List<Patient> findByDiagnosisAndAgeGreater(String diagnosis, int age);
    // 7.4
    List<Patient> findAgeGreaterThanOrderByAgeDesc(int age);
    // 7.5
    Optional<Double> avgAgeByWard(int ward);
    // 7.6
    long countAll();
}

