package com.example.hospital.service;

import com.example.hospital.entity.Patient;
import com.example.hospital.repository.PatientRepository;

public class PatientService {
    private final PatientRepository repo;

    public PatientService(PatientRepository repo) {
        this.repo = repo;
    }

    // 5. Реализовать метод по добавлению пациента в бд.
    public void addDemoPatient(){
        Patient patient = new Patient("Инна", "Мелешко", "Булимия", 101,42);
        long id = repo.insert(patient);
        System.out.println("Добавлен id = " + id + " -> " + patient);
    }
    //6. Реализовать метод по удалению пациента из бд по id.
    public void demoDelete(long id){
        System.out.println("\n=== Удаляем пациента id = " + id + " ===");
        boolean ok = repo.deleteById(id);
        System.out.println(ok ? "Удалили" : "Не найден");
    }

    // 7.1
    public void demoFindAll() {
        System.out.println("\n=== 7.1 Все пациенты ===");
        repo.getAll().forEach(System.out::println);
    }

    // 7.2
    public void demoFindByWard(int ward) {
        System.out.println("\n=== 7.2 Пациенты из палаты " + ward + " ===");
        repo.findByWard(ward).forEach(System.out::println);
    }

    // 7.3
    public void demoFindByDiagnosisAndAgeGreater(String diagnosis, int age) {
        System.out.println("\n=== 7.3 Пациенты с диагнозом '" + diagnosis + "' и возрастом > " + age + " ===");
        repo.findByDiagnosisAndAgeGreater(diagnosis, age).forEach(System.out::println);
    }

    // 7.4
    public void demoFindAgeGreaterThanOrderByAgeDesc(int age) {
        System.out.println("\n=== 7.4 Пациенты с возрастом > " + age + " (по убыванию) ===");
        repo.findAgeGreaterThanOrderByAgeDesc(age).forEach(System.out::println);
    }

    // 7.5
    public void demoAvgAgeByWard(int ward) {
        System.out.println("\n=== 7.5 Средний возраст в палате " + ward + " ===");
        repo.avgAgeByWard(ward).ifPresentOrElse(
                avg -> System.out.println(String.format("Средний возраст = %.2f", avg)),
                () -> System.out.println("В этой палате пациентов нет")
        );
    }

    // 7.6
    public void demoCountAll() {
        System.out.println("\n=== 7.6 Общее количество пациентов ===");
        System.out.println("Всего = " + repo.countAll());
    }
}
