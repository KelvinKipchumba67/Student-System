package com.system.view;

import com.system.dao.StudentDatabaseDAO;
import com.system.model.Student;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {

    private String loggedInRegNo;
    private int studentId;

    public StudentDashboard(int studentId) {
        this.studentId = studentId;

        //Setup
        setTitle("University - Student Portal");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#F4F6F9"));

        //Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.decode("#1A365D"));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel titleLabel = new JLabel("Student Portal");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Logged in as: " + loggedInRegNo);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(Color.decode("#A0AEC0"));

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);

        JPanel cardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 60));
        cardPanel.setOpaque(false);

        JButton libraryBtn = createTileButton("Digital Library", "📚", "#8B5CF6");
        JButton transcriptBtn = createTileButton("My Transcript", "🎓", "#10B981");

        libraryBtn.setPreferredSize(new Dimension(250, 180));
        transcriptBtn.setPreferredSize(new Dimension(250, 180));

        cardPanel.add(libraryBtn);
        cardPanel.add(transcriptBtn);
        add(cardPanel, BorderLayout.CENTER);

        //actions
        libraryBtn.addActionListener(e -> new LibrarySearchView(studentId).setVisible(true));

        transcriptBtn.addActionListener(e -> {
            StudentDatabaseDAO dao = new StudentDatabaseDAO();
            Student student = dao.searchStudentByRegNo(loggedInRegNo);

            if (student != null) {
                new ResultSlipView(student).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Your academic profile has not been fully configured by the Admin yet.", "Profile Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JButton createTileButton(String text, String icon, String topBorderHexColor) {
        String htmlText = "<html><center><span style='font-size:50px;'>" + icon + "</span><br><br><span style='font-size:18px; font-family:Segoe UI; font-weight:bold; color:#333333;'>" + text + "</span></center></html>";
        JButton btn = new JButton(htmlText);
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(6, 1, 1, 1, Color.decode(topBorderHexColor)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        return btn;
    }
}