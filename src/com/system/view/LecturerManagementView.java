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
        //Setup
        setTitle("Lecturer Portal - Course Allocations");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        //Search Bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        searchPanel.setBackground(Color.decode("#1A365D"));

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

        //Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout(0, 25)); // 25px vertical gap
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 30, 40));

        //Lecturer profile card
        JPanel profileCard = new JPanel(new GridLayout(3, 1, 5, 5));
        profileCard.setBackground(Color.WHITE);
        profileCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#E2E8F0"), 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
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

        //Allocated Courses Table
        JPanel tableWrapper = new JPanel(new BorderLayout(0, 10));
        tableWrapper.setBackground(Color.WHITE);

        JLabel tableTitle = new JLabel("Allocated Courses");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tableTitle.setForeground(Color.decode("#4A5568"));
        tableWrapper.add(tableTitle, BorderLayout.NORTH);

        String[] columns = {"Course Code", "Course Title"};
        tableModel = new DefaultTableModel(columns, 0);
        courseTable = new JTable(tableModel);

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


        searchBtn.addActionListener(e -> {
            String staffNoStr = searchField.getText().trim();
            if (staffNoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Staff Number.", "Input Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int staffNo = Integer.parseInt(staffNoStr);

                com.system.dao.AddLecturerDAO dao = new com.system.dao.AddLecturerDAO();

                Object[] profile = dao.getLecturerProfile(staffNo);

                if (profile != null) {
                    String fullName = (String) profile[0];
                    String dept = (String) profile[1];
                    int lecturerId = (int) profile[2];

                    nameLabel.setText("Lecturer Name: " + fullName);
                    staffNoLabel.setText("Staff Number: " + staffNo);
                    deptLabel.setText("Department: " + dept);


                    tableModel.setRowCount(0);

                    String[] newColumns = {"Course Code", "Semester"};
                    tableModel.setColumnIdentifiers(newColumns);

                    java.util.List<Object[]> courses = dao.getLecturerCourses(lecturerId);
                    for (Object[] row : courses) {
                        tableModel.addRow(row);
                    }

                } else {
                    nameLabel.setText("Lecturer Name: --");
                    staffNoLabel.setText("Staff Number: --");
                    deptLabel.setText("Department: --");
                    tableModel.setRowCount(0);

                    JOptionPane.showMessageDialog(this, "No Lecturer found with Staff Number: " + staffNo, "Not Found", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Staff Number must be digits only.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}