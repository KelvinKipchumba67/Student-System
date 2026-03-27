package com.system.view;

import com.system.dao.StudentDatabaseDAO;
import javax.swing.*;
import java.awt.*;

public class AddMarksView extends JFrame {

    private JTextField regNoField, courseCodeField, catField, examField;
    private JComboBox<String> yearCombo, semCombo;
    private StudentDatabaseDAO studentDAO;

    public AddMarksView() {
        studentDAO = new StudentDatabaseDAO();

        setTitle("Lecturer Portal - Academic Records Entry");
        setSize(450, 450); // Made slightly taller to fit the new fields
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 1. The Header
        JLabel headerLabel = new JLabel("Enter Student Scores", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(headerLabel, BorderLayout.NORTH);

        // 2. The Form Panel (6 rows now)
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));

        formPanel.add(new JLabel("Student Reg No:"));
        regNoField = new JTextField();
        formPanel.add(regNoField);

        formPanel.add(new JLabel("Course Code:"));
        courseCodeField = new JTextField();
        formPanel.add(courseCodeField);

        // Dropdown for Academic Year
        formPanel.add(new JLabel("Academic Year:"));
        String[] years = {"2023/2024", "2024/2025", "2025/2026"};
        yearCombo = new JComboBox<>(years);
        formPanel.add(yearCombo);

        // Dropdown for Semester
        formPanel.add(new JLabel("Semester:"));
        String[] semesters = {"Semester 1", "Semester 2", "Semester 3"};
        semCombo = new JComboBox<>(semesters);
        formPanel.add(semCombo);

        formPanel.add(new JLabel("CAT Score (Max 30):"));
        catField = new JTextField();
        formPanel.add(catField);

        formPanel.add(new JLabel("Exam Score (Max 70):"));
        examField = new JTextField();
        formPanel.add(examField);

        add(formPanel, BorderLayout.CENTER);

        // 3. The Submit Button
        JButton submitBtn = new JButton("Save Record");
        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        submitBtn.setBackground(Color.decode("#28A745")); // A nice success green
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        buttonPanel.add(submitBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // 4. Submission Logic
        submitBtn.addActionListener(e -> {
            String regNo = regNoField.getText().trim();
            String courseCode = courseCodeField.getText().trim();
            String year = (String) yearCombo.getSelectedItem();
            String semester = (String) semCombo.getSelectedItem();

            if (regNo.isEmpty() || courseCode.isEmpty() || catField.getText().isEmpty() || examField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double cat = Double.parseDouble(catField.getText().trim());
                double exam = Double.parseDouble(examField.getText().trim());

                // Validation Rules
                if (cat < 0 || cat > 30) {
                    JOptionPane.showMessageDialog(this, "CAT score must be between 0 and 30.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (exam < 0 || exam > 70) {
                    JOptionPane.showMessageDialog(this, "Exam score must be between 0 and 70.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Send to the Database using our new smart method!
                boolean isSaved = studentDAO.saveStudentMarks(regNo, courseCode, year, semester, cat, exam);

                if (isSaved) {
                    JOptionPane.showMessageDialog(this, "✅ Successfully saved " + courseCode + " marks for " + regNo);
                    // Clear only the marks, keep the rest in case they are doing data entry for a whole class
                    catField.setText("");
                    examField.setText("");
                    regNoField.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to save! Check if Reg No '" + regNo + "' exists in the system.", "Database Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Marks must be valid numbers!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}