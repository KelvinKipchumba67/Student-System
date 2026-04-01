package com.system.view;

import javax.swing.*;
import java.awt.*;

public class LecturerDashboard extends JFrame {

    private String loggedInStaff;

    public LecturerDashboard(String staffUsername) {
        this.loggedInStaff = staffUsername;

        //Setup
        setTitle("University - Lecturer Portal");
        setSize(850, 450); // Wide enough to comfortably fit 3 cards
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#F4F6F9"));

        //Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.decode("#1A365D"));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel titleLabel = new JLabel("Lecturer Portal");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Logged in as: " + loggedInStaff);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(Color.decode("#A0AEC0"));

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);

        //Dashboard Cards
        JPanel cardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 35, 60));
        cardPanel.setOpaque(false);

        //buttons
        JButton manageStudentBtn = createTileButton("Student Records", "🎓", "#3B82F6"); // Blue
        JButton addMarksBtn = createTileButton("Enter Marks", "📝", "#F59E0B"); // Amber
        JButton libraryBtn = createTileButton("Digital Library", "📚", "#8B5CF6"); // Purple

        Dimension cardSize = new Dimension(220, 180);
        manageStudentBtn.setPreferredSize(cardSize);
        addMarksBtn.setPreferredSize(cardSize);
        libraryBtn.setPreferredSize(cardSize);

        cardPanel.add(manageStudentBtn);
        cardPanel.add(addMarksBtn);
        cardPanel.add(libraryBtn);

        add(cardPanel, BorderLayout.CENTER);

        //making the buttons clickable
        manageStudentBtn.addActionListener(e -> new StudentManagementView().setVisible(true));
        addMarksBtn.addActionListener(e -> new AddMarksView().setVisible(true));
        libraryBtn.addActionListener(e -> new LibrarySearchView().setVisible(true));
    }

    private JButton createTileButton(String text, String icon, String topBorderHexColor) {
        String htmlText = "<html><center><span style='font-size:45px;'>" + icon + "</span><br><br><span style='font-size:16px; font-family:Segoe UI; font-weight:bold; color:#333333;'>" + text + "</span></center></html>";
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