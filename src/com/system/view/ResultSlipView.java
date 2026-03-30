package com.system.view;

import com.system.model.Student;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ResultSlipView extends JFrame {

    public ResultSlipView(Student student) {
        // 1. Setup the Window (Made larger for a comfortable document view)
        setTitle("Official Result Slip - " + student.getRegNo());
        setSize(850, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); // Pure white background

        // ==========================================
        // 2. THE HEADER SECTION
        // ==========================================
        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.setBackground(Color.WHITE);

        // A. The Official University Banner (Deep Blue)
        JPanel bannerPanel = new JPanel(new GridLayout(2, 1));
        bannerPanel.setBackground(Color.decode("#1A365D"));
        bannerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel uniLabel = new JLabel("CHUKA UNIVERSITY", SwingConstants.CENTER);
        uniLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        uniLabel.setForeground(Color.WHITE);

        JLabel titleLabel = new JLabel("OFFICIAL ACADEMIC TRANSCRIPT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(Color.decode("#A0AEC0")); // Soft blue-gray

        bannerPanel.add(uniLabel);
        bannerPanel.add(titleLabel);
        topWrapper.add(bannerPanel, BorderLayout.NORTH);

        // B. The Student Information Card
        JPanel infoCard = new JPanel(new GridLayout(2, 1, 5, 5));
        infoCard.setBackground(Color.WHITE);
        infoCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 30, 10, 30), // Outer margin
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.decode("#E2E8F0"), 2), // Gray border
                        BorderFactory.createEmptyBorder(15, 20, 15, 20) // Inner padding
                )
        ));

        JLabel nameLabel = new JLabel("<html><b>Student Name:</b> &nbsp; " + student.getFirstName() + " " + student.getLastName() + "</html>");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        nameLabel.setForeground(Color.decode("#1A365D"));

        JLabel regLabel = new JLabel("<html><b>Registration No:</b> &nbsp; " + student.getRegNo() + " &nbsp;&nbsp;|&nbsp;&nbsp; <b>Programme:</b> &nbsp; " + student.getProgramme() + "</html>");
        regLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        regLabel.setForeground(Color.decode("#1A365D"));

        infoCard.add(nameLabel);
        infoCard.add(regLabel);
        topWrapper.add(infoCard, BorderLayout.CENTER);

        add(topWrapper, BorderLayout.NORTH);

        // ==========================================
        // 3. THE GRADES TABLE
        // ==========================================
        String[] columns = {"Code", "Title", "Academic Year", "Semester", "CAT", "Exam", "Total", "Grade"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable gradesTable = new JTable(tableModel);

        // --- MODERN UI UPGRADES FOR THE TABLE ---
        gradesTable.setRowHeight(35); // Generous row height
        gradesTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gradesTable.setSelectionBackground(Color.decode("#E2E8F0"));
        gradesTable.setSelectionForeground(Color.BLACK);
        gradesTable.setShowGrid(false);
        gradesTable.setIntercellSpacing(new Dimension(0, 0));
        gradesTable.setEnabled(false); // Make it read-only

        // Style the Table Header
        JTableHeader header = gradesTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(Color.decode("#F8FAFC"));
        header.setForeground(Color.decode("#4A5568"));
        header.setPreferredSize(new Dimension(100, 45));
        // ----------------------------------------

        JScrollPane scrollPane = new JScrollPane(gradesTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30)); // Side padding for the table
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // ==========================================
        // 4. THE LIVE DATABASE CONNECTION
        // ==========================================
        com.system.dao.StudentDatabaseDAO dao = new com.system.dao.StudentDatabaseDAO();
        java.util.List<Object[]> liveScores = dao.getStudentMarks(student.getPersonId());

        double totalAccumulatedScore = 0;
        int courseCount = 0;

        if (liveScores.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No academic records found in the database for " + student.getRegNo(), "Notice", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Object[] row : liveScores) {
                String code = (String) row[0];
                String title = (String) row[1];
                String year = (String) row[2];
                String semester = (String) row[3];
                double cat = (Double) row[4];
                double exam = (Double) row[5];

                double total = cat + exam;
                String grade = calculateGrade((int) Math.round(total));

                totalAccumulatedScore += total;
                courseCount++;

                tableModel.addRow(new Object[]{code, title, year, semester, cat, exam, total, grade});
            }
        }

        // ==========================================
        // 5. THE FOOTER (AVERAGES)
        // ==========================================
        JPanel footerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));

        int averageScore = courseCount > 0 ? (int) Math.round(totalAccumulatedScore / courseCount) : 0;
        String averageGrade = calculateGrade(averageScore);

        JLabel avgScoreLabel = new JLabel("Cumulative Average Score: " + averageScore + " / 100", SwingConstants.RIGHT);
        avgScoreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        avgScoreLabel.setForeground(Color.decode("#4A5568"));

        JLabel avgGradeLabel = new JLabel("FINAL AVERAGE GRADE:  " + averageGrade, SwingConstants.RIGHT);
        avgGradeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        // Deep Green for passing grades, Bright Red for an F
        avgGradeLabel.setForeground(averageGrade.equals("F") ? Color.decode("#DC2626") : Color.decode("#10B981"));

        footerPanel.add(avgScoreLabel);
        footerPanel.add(avgGradeLabel);
        add(footerPanel, BorderLayout.SOUTH);
    }

    // Helper method for University Grading Rules
    private String calculateGrade(int totalScore) {
        if (totalScore >= 70 && totalScore <= 100) return "A";
        if (totalScore >= 60 && totalScore <= 69) return "B";
        if (totalScore >= 50 && totalScore <= 59) return "C";
        if (totalScore >= 40 && totalScore <= 49) return "D";
        return "F";
    }
}