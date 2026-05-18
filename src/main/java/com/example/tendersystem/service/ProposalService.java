package com.example.tendersystem.service;
import com.example.tendersystem.model.TenderProposal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProposalService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tender_db";
    private static final String USER = "root";
    private static final String PASS = System.getenv("DB_PASSWORD");

    public boolean createProposal(TenderProposal proposal) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            return false;
        }

        String checkStatusSql = "SELECT status FROM tenders WHERE id = ?";
        String checkDuplicateSql = "SELECT COUNT(*) FROM tender_proposals WHERE tenderId = ? AND executorId = ?";
        String insertSql = "INSERT INTO tender_proposals (tenderId, executorId, price, description) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            try (PreparedStatement checkStmt = conn.prepareStatement(checkStatusSql)) {
                checkStmt.setInt(1, proposal.getTenderId());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        String status = rs.getString("status");
                        if (!"ACTIVE".equalsIgnoreCase(status)) {
                            System.out.println("Спроба зламу: тендер " + proposal.getTenderId() + " вже закритий!");
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }

            try (PreparedStatement checkDupStmt = conn.prepareStatement(checkDuplicateSql)) {
                checkDupStmt.setInt(1, proposal.getTenderId());
                checkDupStmt.setInt(2, proposal.getExecutorId());
                try (ResultSet rs = checkDupStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        System.out.println("Відхилено: виконавець " + proposal.getExecutorId() + " вже подав заявку на тендер " + proposal.getTenderId());
                        return false;
                    }
                }
            }

            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setInt(1, proposal.getTenderId());
                pstmt.setInt(2, proposal.getExecutorId());
                pstmt.setDouble(3, proposal.getPrice());
                pstmt.setString(4, proposal.getDescription());

                int affectedRows = pstmt.executeUpdate();
                return affectedRows > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<TenderProposal> getProposalsByTenderId(int tenderId) {
        List<TenderProposal> list = new ArrayList<>();
        try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch (ClassNotFoundException e) { return list; }

        String sql = "SELECT * FROM tender_proposals WHERE tenderId = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, tenderId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TenderProposal p = new TenderProposal();
                    p.setId(rs.getInt("id"));
                    p.setTenderId(rs.getInt("tenderId"));
                    p.setExecutorId(rs.getInt("executorId"));
                    p.setPrice(rs.getDouble("price"));
                    p.setDescription(rs.getString("description"));
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}