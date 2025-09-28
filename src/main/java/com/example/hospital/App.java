package com.example.hospital;

import com.example.hospital.config.JDBCConnection;
import com.example.hospital.config.PostgresConnection;
import com.example.hospital.repository.PatientRepository;
import com.example.hospital.repository.PatientRepositoryImpl;
import com.example.hospital.service.PatientService;

public class App {
    public static void main(String[] args) {
        JDBCConnection conn = new PostgresConnection();
        PatientRepository repo = new PatientRepositoryImpl(conn);
        PatientService service = new PatientService(repo);
        service.addDemoPatient(); // 5
        service.demoDelete(8); // 6
        service.demoFindAll(); // 7.1
        service.demoFindByWard(101); // 7.2
        service.demoFindByDiagnosisAndAgeGreater("Булимия", 42); // 7.3
        service.demoFindAgeGreaterThanOrderByAgeDesc(30); // 7.4
        service.demoAvgAgeByWard(202);  // 7.5
        service.demoCountAll();  // 7.6
    }
}
