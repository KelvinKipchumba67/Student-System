package com.system.view;

import com.system.model.Student;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ResultSlipView extends JFrame {

    public ResultSlipView(Student student) {
        // 1. Setup the Window
        setTitle("Official Result Slip - " + student.getRegNo());
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 2. The Header (University Info & Student Details)
        JPanel headerPanel = new JPanel(new GridLayout(4, 1));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel uniLabel = new JLabel("CHUKA UNIVERSITY - ACADEMIC TRANSCRIPT", SwingConstants.CENTER);
        uniLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel titleLabel = new JLabel("OFFICIAL RESULT SLIP", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel nameLabel = new JLabel("Student Name: " + student.getFirstName() + " " + student.getLastName());
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel regLabel = new JLabel("Registration No: " + student.getRegNo() + "  |  Programme: " + student.getProgramme());
        regLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        headerPanel.add(uniLabel);
        headerPanel.add(titleLabel);
        headerPanel.add(new JLabel(" ")); // Spacer
        headerPanel.add(nameLabel);
        headerPanel.add(regLabel);
        add(headerPanel, BorderLayout.NORTH);

        // 3. The Grades Table
        String[] columns = {"Code", "Title", "Academic Year", "Semester", "CAT", "Exam", "Total", "Grade"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable gradesTable = new JTable(tableModel);
        gradesTable.setRowHeight(25);
        gradesTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gradesTable.setEnabled(false); // Make it read-only

        add(new JScrollPane(gradesTable), BorderLayout.CENTER);

        // ==========================================
        // 4. THE LIVE DATABASE CONNECTION
        // ==========================================
        com.system.dao.StudentDatabaseDAO dao = new com.system.dao.StudentDatabaseDAO();

        // Fetch ONLY the scores that belong to this specific student
        java.util.List<Object[]> liveScores = dao.getStudentMarks(student.getPersonId());

        double totalAccumulatedScore = 0;
        int courseCount = 0;

        if (liveScores.isEmpty()) {
            // If they have no marks in the database yet, show a blank slip and a notice!
            JOptionPane.showMessageDialog(this, "No academic records found in the database for " + student.getRegNo(), "Notice", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // If they DO have marks, calculate grades and populate the table dynamically
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

        // 5. The Footer (Averages)
        JPanel footerPanel = new JPanel(new GridLayout(2, 1));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        int averageScore = courseCount > 0 ? (int) Math.round(totalAccumulatedScore / courseCount) : 0;
        String averageGrade = calculateGrade(averageScore);

        JLabel avgScoreLabel = new JLabel("Average Score: " + averageScore + " / 100", SwingConstants.RIGHT);
        avgScoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel avgGradeLabel = new JLabel("FINAL AVERAGE GRADE: " + averageGrade, SwingConstants.RIGHT);
        avgGradeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        avgGradeLabel.setForeground(averageGrade.equals("F") ? Color.RED : new Color(0, 128, 0));

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