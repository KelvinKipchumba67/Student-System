package com.system.view;

import com.system.model.Student;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ResultSlipView extends JFrame {

    public ResultSlipView(Student student) {
        //Setup
        setTitle("Official Result Slip - " + student.getRegNo());
        setSize(850, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.setBackground(Color.WHITE);
        //header
        JPanel bannerPanel = new JPanel(new GridLayout(2, 1));
        bannerPanel.setBackground(Color.decode("#1A365D"));
        bannerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel uniLabel = new JLabel("UNIVERSITY SYSTEM", SwingConstants.CENTER);
        uniLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        uniLabel.setForeground(Color.WHITE);

        JLabel titleLabel = new JLabel("OFFICIAL ACADEMIC TRANSCRIPT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(Color.decode("#A0AEC0")); // Soft blue-gray

        bannerPanel.add(uniLabel);
        bannerPanel.add(titleLabel);
        topWrapper.add(bannerPanel, BorderLayout.NORTH);

        //Student information card
        JPanel infoCard = new JPanel(new GridLayout(2, 1, 5, 5));
        infoCard.setBackground(Color.WHITE);
        infoCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 30, 10, 30),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.decode("#E2E8F0"), 2),
                        BorderFactory.createEmptyBorder(15, 20, 15, 20)
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

        String[] columns = {"Code", "Title", "Academic Year", "Semester", "CAT", "Exam", "Total", "Grade"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable gradesTable = new JTable(tableModel);

        gradesTable.setRowHeight(35);
        gradesTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gradesTable.setSelectionBackground(Color.decode("#E2E8F0"));
        gradesTable.setSelectionForeground(Color.BLACK);
        gradesTable.setShowGrid(false);
        gradesTable.setIntercellSpacing(new Dimension(0, 0));
        gradesTable.setEnabled(false); // Make it read-only

        //styling for the header
        JTableHeader header = gradesTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(Color.decode("#F8FAFC"));
        header.setForeground(Color.decode("#4A5568"));
        header.setPreferredSize(new Dimension(100, 45));


        JScrollPane scrollPane = new JScrollPane(gradesTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

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
        avgGradeLabel.setForeground(averageGrade.equals("F") ? Color.decode("#DC2626") : Color.decode("#10B981"));

        footerPanel.add(avgScoreLabel);
        footerPanel.add(avgGradeLabel);
        add(footerPanel, BorderLayout.SOUTH);
    }

    //Grading Rules
    private String calculateGrade(int totalScore) {
        if (totalScore >= 70 && totalScore <= 100) return "A";
        if (totalScore >= 60 && totalScore <= 69) return "B";
        if (totalScore >= 50 && totalScore <= 59) return "C";
        if (totalScore >= 40 && totalScore <= 49) return "D";
        return "F";
    }
}