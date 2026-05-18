package com.example.tendersystem.service;

import com.example.tendersystem.model.Tender;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TenderService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tender_db";
    private static final String USER = "root";
    private static final String PASS = System.getenv("DB_PASSWORD");

    public List<Tender> getAllTenders() {
        List<Tender> list = new ArrayList<>();
        try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch (ClassNotFoundException e) { return list; }

        // LEFT JOIN гарантує, що тендер виведеться, навіть якщо юзера випадково видалили
        String sql = "SELECT t.*, u.username AS ownerName FROM tenders t LEFT JOIN users u ON t.ownerId = u.id";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Tender tender = new Tender();
                tender.setId(rs.getInt("id"));
                tender.setName(rs.getString("name"));
                tender.setDescription(rs.getString("description"));
                tender.setStatus(rs.getString("status"));
                tender.setOwnerId(rs.getInt("ownerId"));
                tender.setOwnerName(rs.getString("ownerName"));
                list.add(tender);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Tender createTender(Tender tend) {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch (ClassNotFoundException e) { return null; }
        String sql = "INSERT INTO tenders (name, description, status, ownerId) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, tend.getName());
            pstmt.setString(2, tend.getDescription());
            pstmt.setString(3, tend.getStatus());
            pstmt.setInt(4, tend.getOwnerId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Створення тендеру не вдалося, жодного рядка не змінено.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    tend.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Створення тендеру не вдалося, ID не отримано.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Помилка під час збереження в базу:");
            e.printStackTrace();
            throw new RuntimeException("Не вдалося зберегти тендер у БД", e);
        }

        return tend;
    }

    public Tender getTenderById(int id) {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch (ClassNotFoundException e) { return null; }

        String sql = "SELECT t.*, u.username AS ownerName FROM tenders t LEFT JOIN users u ON t.ownerId = u.id WHERE t.id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Tender t = new Tender();
                    t.setId(rs.getInt("id"));
                    t.setName(rs.getString("name"));
                    t.setDescription(rs.getString("description"));
                    t.setStatus(rs.getString("status"));
                    t.setOwnerId(rs.getInt("ownerId"));
                    t.setOwnerName(rs.getString("ownerName"));
                    t.setExecutorId(rs.getInt("executorId"));

                    return t;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateTenderStatus(int tenderId, String newStatus) {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch (ClassNotFoundException e) { return false; }

        String sql;
        if ("ACTIVE".equalsIgnoreCase(newStatus)) {
            sql = "UPDATE tenders SET status = ?, executorId = NULL WHERE id = ?";
        } else {
            sql = "UPDATE tenders SET status = ? WHERE id = ?";
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newStatus);
            pstmt.setInt(2, tenderId);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Tender> searchTenders(String keyword) {
        List<Tender> list = new ArrayList<>();
        try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch (ClassNotFoundException e) { return list; }

        String sql = "SELECT t.*, u.username AS ownerName FROM tenders t " +
                "LEFT JOIN users u ON t.ownerId = u.id " +
                "WHERE t.name LIKE ? OR t.description LIKE ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Tender tender = new Tender();
                    tender.setId(rs.getInt("id"));
                    tender.setName(rs.getString("name"));
                    tender.setDescription(rs.getString("description"));
                    tender.setStatus(rs.getString("status"));
                    tender.setOwnerId(rs.getInt("ownerId"));
                    tender.setOwnerName(rs.getString("ownerName"));
                    tender.setExecutorId(rs.getInt("executorId"));

                    list.add(tender);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteTender(int id) {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch (ClassNotFoundException e) { return false; }

        String sqlDeleteProposals = "DELETE FROM tender_proposals WHERE tenderId = ?";
        String sqlDeleteTender = "DELETE FROM tenders WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            try (PreparedStatement pstmt1 = conn.prepareStatement(sqlDeleteProposals)) {
                pstmt1.setInt(1, id);
                pstmt1.executeUpdate();
            }

            try (PreparedStatement pstmt2 = conn.prepareStatement(sqlDeleteTender)) {
                pstmt2.setInt(1, id);
                int affectedRows = pstmt2.executeUpdate();
                return affectedRows > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean selectExecutor(int tenderId, int executorId) {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch (ClassNotFoundException e) { return false; }

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            String sqlUpdateTender = "UPDATE tenders SET status = 'CLOSED', executorId = ? WHERE id = ?";
            try (PreparedStatement pstmt1 = conn.prepareStatement(sqlUpdateTender)) {
                pstmt1.setInt(1, executorId);
                pstmt1.setInt(2, tenderId);
                pstmt1.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}