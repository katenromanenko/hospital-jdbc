package com.example.hospital.repository;

import com.example.hospital.config.JDBCConnection;
import com.example.hospital.entity.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientRepositoryImpl implements PatientRepository {

    private final JDBCConnection connectionProvider;

    public PatientRepositoryImpl(JDBCConnection connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    // 5. Реализовать метод по добавлению пациента в бд.
    @Override
    public long insert(Patient p){
        String sql = "INSERT INTO patient (first_name, last_name, diagnosis, ward, age) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (Connection c = connectionProvider.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1, p.getFirstName());
            ps.setString(2, p.getLastName());
            ps.setString(3, p.getDiagnosis());
            ps.setInt(4, p.getWard());
            ps.setInt(5, p.getAge());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    long id = rs.getLong(1);
                    p.setId(id);
                    return id;
                }
                throw new SQLException("INSERT не вернул идентификатор");
            }
        } catch (SQLException e) {
            throw new RuntimeException("INSERT ошибка: " + e.getMessage(), e);
        }
    }

    // 6. Реализовать метод по удалению пациента из бд по id.
    public boolean deleteById(long id){
        String sql = "DELETE FROM patient WHERE id=?";

        try (Connection c = connectionProvider.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("deleteById ошибка: " + e.getMessage(), e);
        }
    }

    // 7.1 Все пациенты
    @Override
    public List<Patient> getAll() {
        String sql = "SELECT * FROM patient";
        List<Patient> patients = new ArrayList<>();

        try (Connection c = connectionProvider.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                patients.add(map(rs));
            }
            return patients;

        } catch (SQLException e) {
            throw new RuntimeException("getAll ошибка: " + e.getMessage(), e);
        }
    }
    // 7.2 Выполнить выборку всех пациентов из одной палаты
    @Override
    public List<Patient> findByWard(int ward) {
        String sql = "SELECT id, first_name, last_name, diagnosis, ward, age " +
                "FROM patient WHERE ward = ?";

        List<Patient> patients = new ArrayList<>();

        try (Connection c = connectionProvider.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, ward); //

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    patients.add(map(rs));
                }
            }

            return patients;

        } catch (SQLException e) {
            throw new RuntimeException("findByWard ошибка: " + e.getMessage(), e);
        }
    }

    // 7.3 Выполнить выборки пациентов по диагнозу и по возрасту старше к примеру 30 лет.
    @Override
    public List<Patient> findByDiagnosisAndAgeGreater(String diagnosis, int age) {
        String sql = "SELECT id, first_name, last_name, diagnosis, ward, age " +
                "FROM patient WHERE diagnosis ILIKE ? AND age > ?";

        List<Patient> patients = new ArrayList<>();

        try (Connection c = connectionProvider.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, diagnosis);
            ps.setInt(2, age);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    patients.add(map(rs));
                }
            }

            return patients;

        } catch (SQLException e) {
            throw new RuntimeException("findByDiagnosisAndAgeGreater ошибка: " + e.getMessage(), e);
        }
    }

    // 7.4: пациенты старше определённого возраста, отсортированные по возрасту по убыванию.
    @Override
    public List<Patient> findAgeGreaterThanOrderByAgeDesc(int age) {
        String sql = "SELECT id, first_name, last_name, diagnosis, ward, age " +
                "FROM patient WHERE age > ? ORDER BY age DESC";

        List<Patient> patients = new ArrayList<>();

        try (Connection c = connectionProvider.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, age);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    patients.add(map(rs));
                }
            }

            return patients;

        } catch (SQLException e) {
            throw new RuntimeException("findAgeGreaterThanOrderByAgeDesc ошибка: " + e.getMessage(), e);
        }
    }

    // 7.5 Вывести средний возраст всех пациентов в палате
    @Override
    public Optional<Double> avgAgeByWard(int ward) {
        String sql = "SELECT AVG(age) AS avg_age FROM patient WHERE ward = ?";

        try (Connection c = connectionProvider.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, ward);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double avg = rs.getDouble("avg_age");
                    if (rs.wasNull()) return Optional.empty(); // если строк нет
                    return Optional.of(avg);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("avgAgeByWard ошибка: " + e.getMessage(), e);
        }
    }

    // 7.6 Вывести общее количество пациентов
    @Override
    public long countAll() {
        String sql = "SELECT COUNT(*) AS cnt FROM patient";

        try (Connection c = connectionProvider.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            return rs.next() ? rs.getLong("cnt") : 0L;

        } catch (SQLException e) {
            throw new RuntimeException("countAll ошибка: " + e.getMessage(), e);
        }
    }


    private Patient map(ResultSet rs) throws SQLException {
        Patient p = new Patient();
        p.setId(rs.getLong("id"));
        p.setFirstName(rs.getString("first_name"));
        p.setLastName(rs.getString("last_name"));
        p.setDiagnosis(rs.getString("diagnosis"));
        p.setWard(rs.getInt("ward"));
        p.setAge(rs.getInt("age"));
        return p;
    }
}

