package com.system.view;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        //Setup
        setTitle("University - Management System");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#F4F6F9")); // Very light modern gray background

        //Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.decode("#1A365D")); // Deep University Blue
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel titleLabel = new JLabel("University Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Administrative Dashboard");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(Color.decode("#A0AEC0")); // Soft gray-blue

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);

        //Dashboard Grid
        JPanel gridPanel = new JPanel(new GridLayout(2, 3, 25, 25));
        gridPanel.setOpaque(false); // Let the light gray background show through
        gridPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        //Buttons
        JButton studentBtn = createTileButton("Manage Students", "🎓", "#3B82F6"); // Blue accent
        JButton addStudentBtn = createTileButton("Register Student", "👤", "#10B981"); // Green accent
        JButton addMarksBtn = createTileButton("Enter Marks", "📝", "#F59E0B"); // Amber accent
        JButton lecturerBtn = createTileButton("Lecturer Portal", "👨‍🏫", "#6366F1"); // Indigo accent
        JButton libraryBtn = createTileButton("Library System", "📚", "#8B5CF6"); // Purple accent
        JButton addLecturerBtn = createTileButton("Register Lecturer", "📚", "#8B5CF6"); // Purple accent

        // Adding buttons to the grid
        gridPanel.add(studentBtn);
        gridPanel.add(addStudentBtn);
        gridPanel.add(addMarksBtn);
        gridPanel.add(lecturerBtn);
        gridPanel.add(libraryBtn);
        gridPanel.add(addLecturerBtn);
        gridPanel.add(new JLabel(""));

        add(gridPanel, BorderLayout.CENTER);

        //making buttons clickable
        libraryBtn.addActionListener(e -> new LibrarySearchView().setVisible(true));
        studentBtn.addActionListener(e -> new StudentManagementView().setVisible(true));
        lecturerBtn.addActionListener(e -> new LecturerManagementView().setVisible(true));
        addStudentBtn.addActionListener(e -> new AddStudentView().setVisible(true));
        addMarksBtn.addActionListener(e -> new AddMarksView().setVisible(true));
        addLecturerBtn.addActionListener(e -> new AddLecturerView().setVisible(true));
    }
    private JButton createTileButton(String text, String icon, String topBorderHexColor) {
        //HTML formatting to give a massive icon and bold text underneath
        String htmlText = "<html><center>" +
                "<span style='font-size:45px;'>" + icon + "</span><br><br>" +
                "<span style='font-size:16px; font-family:Segoe UI; font-weight:bold; color:#333333;'>" + text + "</span>" +
                "</center></html>";

        JButton btn = new JButton(htmlText);
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(5, 1, 1, 1, Color.decode(topBorderHexColor)), // The colored top bar
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        return btn;
    }
}