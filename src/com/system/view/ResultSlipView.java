package com.system.view;

import com.system.model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ResultSlipView extends JFrame {

    public ResultSlipView(Student student) {
        // 1. Setup the Window
        setTitle("Official Result Slip - " + student.getRegNo());
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 2. The Header (University Info & Student Details)
        JPanel headerPanel = new JPanel(new GridLayout(4, 1));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel uniLabel = new JLabel("UNIVERSITY MANAGEMENT SYSTEM", SwingConstants.CENTER);
        uniLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        JLabel titleLabel = new JLabel("OFFICIAL RESULT SLIP", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel nameLabel = new JLabel("Student Name: " + student.getFirstName() + " " + student.getLastName());
        JLabel regLabel = new JLabel("Registration No: " + student.getRegNo() + "  |  Programme: " + student.getProgramme());

        headerPanel.add(uniLabel);
        headerPanel.add(titleLabel);
        headerPanel.add(new JLabel(" ")); // Spacer
        headerPanel.add(nameLabel);
        headerPanel.add(regLabel);
        add(headerPanel, BorderLayout.NORTH);

        // 3. The Grades Table
        String[] columns = {"Course Code", "Course Title", "CAT (30)", "Exam (70)", "Total", "Grade"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable gradesTable = new JTable(tableModel);
        gradesTable.setRowHeight(25);
        gradesTable.setEnabled(false); // Make it read-only

        add(new JScrollPane(gradesTable), BorderLayout.CENTER);

        // --- SIMULATED DATA FOR THE TUTORIAL ---
        // In your real app, you would fetch these from ScoreDatabaseDAO.
        // We are hardcoding 5 courses here so you can test the math and visuals instantly.
        Object[][] rawScores = {
                {"CSC101", "Intro to Java", 25, 60},
                {"CSC102", "Database Systems", 20, 50},
                {"CSC103", "Web Development", 28, 65},
                {"CSC104", "Mathematics I", 15, 40},
                {"CSC105", "Operating Systems", 22, 45}
        };

        int totalAccumulatedScore = 0;
        int courseCount = 0;

        // Calculate grades and populate the table
        for (Object[] row : rawScores) {
            String code = (String) row[0];
            String title = (String) row[1];
            int cat = (int) row[2];
            int exam = (int) row[3];

            int total = cat + exam;
            String grade = calculateGrade(total);

            totalAccumulatedScore += total;
            courseCount++;

            tableModel.addRow(new Object[]{code, title, cat, exam, total, grade});
        }

        // 4. The Footer (Averages)
        JPanel footerPanel = new JPanel(new GridLayout(2, 1));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        int averageScore = totalAccumulatedScore / courseCount;
        String averageGrade = calculateGrade(averageScore);

        JLabel avgScoreLabel = new JLabel("Average Score: " + averageScore + " / 100", SwingConstants.RIGHT);
        avgScoreLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel avgGradeLabel = new JLabel("FINAL AVERAGE GRADE: " + averageGrade, SwingConstants.RIGHT);
        avgGradeLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        avgGradeLabel.setForeground(averageGrade.equals("F") ? Color.RED : new Color(0, 128, 0)); // Red if F, Green otherwise

        footerPanel.add(avgScoreLabel);
        footerPanel.add(avgGradeLabel);
        add(footerPanel, BorderLayout.SOUTH);
    }

    // Helper method for Grading Rules
    private String calculateGrade(int totalScore) {
        if (totalScore >= 70 && totalScore <= 100) return "A";
        if (totalScore >= 60 && totalScore <= 69) return "B";
        if (totalScore >= 50 && totalScore <= 59) return "C";
        if (totalScore >= 40 && totalScore <= 49) return "D";
        return "F";
    }
}