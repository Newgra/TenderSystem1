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

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tenders")) {

            while (rs.next()) {
                Tender tender = new Tender();
                tender.setId(rs.getInt("id"));
                tender.setName(rs.getString("name"));
                tender.setDescription(rs.getString("description"));
                tender.setStatus(rs.getString("status"));
                tender.setOwnerName(rs.getString("ownerName"));

                list.add(tender);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // --- СТАРІ МЕТОДИ (ЗАЛИШАЮТЬСЯ ЯК Є, ЩОБ НЕ БУЛО ПОМИЛОК) ---
    public Tender createTender(Tender tend) {
        tend.setId(newId++);
        tenders.add(tend);
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

