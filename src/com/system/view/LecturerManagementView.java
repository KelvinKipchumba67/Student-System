package com.system.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class LecturerManagementView extends JFrame {

    private JTextField searchField;
    private JButton searchBtn;
    private JLabel nameLabel, deptLabel, staffNoLabel;
    private JTable courseTable;
    private DefaultTableModel tableModel;

    public LecturerManagementView() {
        // 1. Setup Window
        setTitle("Lecturer Portal - Course Allocations");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // 2. The Top Search Bar (Deep Blue Banner)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        searchPanel.setBackground(Color.decode("#1A365D")); // Deep University Blue

        JLabel searchTitle = new JLabel("Search Lecturer (Staff No):");
        searchTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        searchTitle.setForeground(Color.WHITE);

        searchField = new JTextField(15);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        searchBtn = new JButton("Search");
        searchBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchBtn.setBackground(Color.decode("#3B82F6")); // Vibrant Blue
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        searchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        searchPanel.add(searchTitle);
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        add(searchPanel, BorderLayout.NORTH);

        // 3. The Main Content Panel (With wide margins)
        JPanel contentPanel = new JPanel(new BorderLayout(0, 25)); // 25px vertical gap
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 30, 40));

        // --- SECTION A: The Lecturer Profile Card ---
        JPanel profileCard = new JPanel(new GridLayout(3, 1, 5, 5));
        profileCard.setBackground(Color.WHITE);
        profileCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#E2E8F0"), 1), // Light gray border
                BorderFactory.createEmptyBorder(15, 20, 15, 20) // Internal padding
        ));

        nameLabel = new JLabel("Lecturer Name: --");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        nameLabel.setForeground(Color.decode("#1A365D"));

        staffNoLabel = new JLabel("Staff Number: --");
        staffNoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        staffNoLabel.setForeground(Color.decode("#4A5568"));

        deptLabel = new JLabel("Department: --");
        deptLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        deptLabel.setForeground(Color.decode("#4A5568"));

        profileCard.add(nameLabel);
        profileCard.add(staffNoLabel);
        profileCard.add(deptLabel);

        contentPanel.add(profileCard, BorderLayout.NORTH);

        // --- SECTION B: The Allocated Courses Table ---
        JPanel tableWrapper = new JPanel(new BorderLayout(0, 10));
        tableWrapper.setBackground(Color.WHITE);

        JLabel tableTitle = new JLabel("Allocated Courses");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tableTitle.setForeground(Color.decode("#4A5568"));
        tableWrapper.add(tableTitle, BorderLayout.NORTH);

        String[] columns = {"Course Code", "Course Title"};
        tableModel = new DefaultTableModel(columns, 0);
        courseTable = new JTable(tableModel);

        // Modern Table UI Upgrades
        courseTable.setRowHeight(35);
        courseTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        courseTable.setSelectionBackground(Color.decode("#E2E8F0"));
        courseTable.setSelectionForeground(Color.BLACK);
        courseTable.setShowGrid(false);
        courseTable.setIntercellSpacing(new Dimension(0, 0));
        courseTable.setEnabled(false); // Read-only

        JTableHeader header = courseTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(Color.decode("#F8FAFC")); // Very light gray/blue
        header.setForeground(Color.decode("#4A5568"));
        header.setPreferredSize(new Dimension(100, 40));

        JScrollPane scrollPane = new JScrollPane(courseTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#E2E8F0"), 1));

        tableWrapper.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(tableWrapper, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);

        // 4. Temporary Action Listener to demonstrate the UI changes
        searchBtn.addActionListener(e -> {
            String staffNo = searchField.getText().trim();
            if (staffNo.isEmpty()) return;

            // TODO: Replace this with your actual LecturerDAO database fetch!
            // Simulated Database Fetch:
            nameLabel.setText("Lecturer Name: Dr. Alan Turing");
            staffNoLabel.setText("Staff Number: " + staffNo);
            deptLabel.setText("Department: Computer Science");

            // Clear the old table and simulate loading courses
            tableModel.setRowCount(0);
            tableModel.addRow(new Object[]{"CSC101", "Introduction to Java Programming"});
            tableModel.addRow(new Object[]{"CSC102", "Advanced Database Systems"});
            tableModel.addRow(new Object[]{"CSC103", "Data Structures and Algorithms"});
        });
    }
}