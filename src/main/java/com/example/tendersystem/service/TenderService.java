package com.example.tendersystem.service;

import com.example.tendersystem.model.Tender;
import com.example.tendersystem.model.TenderProposal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TenderService {

    // Дані для підключення до бази
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tender_db";
    private static final String USER = "root";
    private static final String PASS = System.getenv("DB_PASSWORD");

    // Тимчасові списки для старих методів, поки ми їх теж не переведемо на базу
    private static List<Tender> tenders = new ArrayList<>();
    private static List<TenderProposal> tendProposals = new ArrayList<>();
    private static int newId = 1;
    private static int newProposalId = 1;

    // --- НОВИЙ МЕТОД (ПРАЦЮЄ ЧЕРЕЗ БАЗУ ДАНИХ) ---
    public List<Tender> getAllTenders() {
        List<Tender> list = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Драйвер MySQL не знайдено в збірці проєкту!");
            e.printStackTrace();
            return list;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tenders")) {

            while (rs.next()) {
                Tender tender = new Tender();
                int currentTenderId = rs.getInt("id");

                tender.setId(rs.getInt("id"));
                tender.setName(rs.getString("name"));
                tender.setDescription(rs.getString("description"));
                tender.setStatus(rs.getString("status"));
                tender.setOwnerName(rs.getString("ownerName"));

                String catSql = "SELECT categoryName FROM tender_categories WHERE tender_id = ?";
                try (PreparedStatement catStmt = conn.prepareStatement(catSql)) {
                    catStmt.setInt(1, currentTenderId);
                    try (ResultSet catRs = catStmt.executeQuery()) {
                        List<String> categories = new ArrayList<>();
                        while (catRs.next()) {
                            categories.add(catRs.getString("categoryName"));
                        }
                        tender.setCategories(categories); // Кладемо список у наш тендер
                    }
                }

                list.add(tender);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // --- СТАРІ МЕТОДИ (ЗАЛИШАЮТЬСЯ ЯК Є, ЩОБ НЕ БУЛО ПОМИЛОК) ---
    public Tender createTender(Tender tend) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Драйвер MySQL не знайдено!");
        }

        String sql = "INSERT INTO tenders (name, description, status, ownerName) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, tend.getName());
            pstmt.setString(2, tend.getDescription());
            pstmt.setString(3, tend.getStatus());
            pstmt.setString(4, tend.getOwnerName());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                // Якщо база каже "я нічого не записала", ми викидаємо помилку!
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
            System.err.println("❌ ПОМИЛКА ПІД ЧАС ЗБЕРЕЖЕННЯ В БАЗУ:");
            e.printStackTrace();
            // Викидаємо помилку далі, щоб сервер віддав 500 статус, а не брехав, що все добре
            throw new RuntimeException("Не вдалося зберегти тендер у БД", e);
        }

        return tend;
    }

    public TenderProposal createTendProposals(int tenderId, TenderProposal tenderProposal) {
        tenderProposal.setId(newProposalId++);
        tenderProposal.setTenderId(tenderId);
        tendProposals.add(tenderProposal);
        return tenderProposal;
    }

    public List<TenderProposal> getAllProposalsTenderId(int tenderId) {
        List<TenderProposal> result = new ArrayList<>();
        for (TenderProposal p : tendProposals) {
            if (p.getTenderId() == tenderId) {
                result.add(p);
            }
        }
        return result;
    }
}
//    public void deleteTender(int id) {
//        tenders.removeIf(t -> t.getId() == id);
//    }

