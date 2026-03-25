package com.system.view;

import javax.swing.*;
import java.awt.*;

public class MainDashboard extends JFrame {

    public MainDashboard() {
        //Setup the Main Window
        setTitle("University Management System");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        //Header
        JLabel headerLabel = new JLabel("Student System Menu", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(headerLabel, BorderLayout.NORTH);

        //The BULLETPROOF Button Panel (GridBagLayout prevents stretching!)
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // Adds 10px of space between buttons
        gbc.gridx = 0; // Aligns them in a single column
        gbc.fill = GridBagConstraints.HORIZONTAL; // Makes them all the exact same width
        gbc.ipadx = 40; // Makes the buttons a bit wider internally
        gbc.ipady = 10; // Makes the buttons a bit taller internally

        // 4. Create Buttons
        JButton studentBtn = new JButton("Open Student Management");
        JButton lecturerBtn = new JButton("Lecturer Management");
        JButton libraryBtn = new JButton("Open Library System");
        JButton addStudentBtn = new JButton("Add New Student");
        addStudentBtn.setFocusPainted(false);

        studentBtn.setFocusPainted(false);
        lecturerBtn.setFocusPainted(false);
        libraryBtn.setFocusPainted(false);

        //Add to panel using the constraints
        buttonPanel.add(studentBtn, gbc);
        buttonPanel.add(lecturerBtn, gbc);
        buttonPanel.add(libraryBtn, gbc);
        add(buttonPanel, BorderLayout.CENTER);
        buttonPanel.add(addStudentBtn, gbc);

        //Wire up the buttons
        libraryBtn.addActionListener(e -> {
            LibrarySearchView libraryWindow = new LibrarySearchView();
            libraryWindow.setVisible(true);
        });

        //opens our visual Student System
        studentBtn.addActionListener(e -> {
            StudentManagementView studentWindow = new StudentManagementView();
            studentWindow.setVisible(true);
        });

        lecturerBtn.addActionListener(e -> {
            LecturerManagementView lecturerWindow = new LecturerManagementView();
            lecturerWindow.setVisible(true);
        });

        addStudentBtn.addActionListener(e -> {
            AddStudentView addWindow = new AddStudentView();
            addWindow.setVisible(true);
        });
    }
}