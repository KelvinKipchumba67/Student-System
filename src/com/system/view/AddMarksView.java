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

        // 1. Setup Window (Slightly larger for better padding)
        setTitle("Lecturer Portal - Academic Records Entry");
        setSize(500, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); // Pure white background

        // 2. The Header (Deep Blue to match the dashboard)
        JLabel headerLabel = new JLabel("Enter Student Scores", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(Color.decode("#1A365D")); // Deep University Blue
        headerLabel.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0));
        add(headerLabel, BorderLayout.NORTH);

        // 3. The Form Panel (Added a wrapper to give it wide margins on the sides)
        JPanel formWrapper = new JPanel(new BorderLayout());
        formWrapper.setBackground(Color.WHITE);
        formWrapper.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40)); // Top, Left, Bottom, Right padding

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 15, 25)); // Increased vertical gap to 25px
        formPanel.setBackground(Color.WHITE);

        // Helper font for labels
        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Color labelColor = Color.decode("#4A5568"); // Soft dark gray

        formPanel.add(createStyledLabel("Student Reg No:", labelFont, labelColor));
        regNoField = new JTextField();
        formPanel.add(regNoField);

        formPanel.add(createStyledLabel("Course Code:", labelFont, labelColor));
        courseCodeField = new JTextField();
        formPanel.add(courseCodeField);

        formPanel.add(createStyledLabel("Academic Year:", labelFont, labelColor));
        String[] years = {"2023/2024", "2024/2025", "2025/2026"};
        yearCombo = new JComboBox<>(years);
        yearCombo.setBackground(Color.WHITE);
        formPanel.add(yearCombo);

        formPanel.add(createStyledLabel("Semester:", labelFont, labelColor));
        String[] semesters = {"Semester 1", "Semester 2", "Semester 3"};
        semCombo = new JComboBox<>(semesters);
        semCombo.setBackground(Color.WHITE);
        formPanel.add(semCombo);

        formPanel.add(createStyledLabel("CAT Score (Max 30):", labelFont, labelColor));
        catField = new JTextField();
        formPanel.add(catField);

        formPanel.add(createStyledLabel("Exam Score (Max 70):", labelFont, labelColor));
        examField = new JTextField();
        formPanel.add(examField);

        formWrapper.add(formPanel, BorderLayout.CENTER);
        add(formWrapper, BorderLayout.CENTER);

        // 4. The Submit Button (Vibrant Green, large, and clickable)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0)); // Bottom padding

        JButton submitBtn = new JButton("Save Record");
        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        submitBtn.setBackground(Color.decode("#10B981")); // Modern Emerald Green
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        submitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitBtn.setPreferredSize(new Dimension(250, 45)); // Make it a nice wide button

        buttonPanel.add(submitBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // 5. Submission Logic (Unchanged from your working backend)
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

                if (cat < 0 || cat > 30) {
                    JOptionPane.showMessageDialog(this, "CAT score must be between 0 and 30.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (exam < 0 || exam > 70) {
                    JOptionPane.showMessageDialog(this, "Exam score must be between 0 and 70.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean isSaved = studentDAO.saveStudentMarks(regNo, courseCode, year, semester, cat, exam);

                if (isSaved) {
                    JOptionPane.showMessageDialog(this, "✅ Successfully saved " + courseCode + " marks for " + regNo);
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

    // Helper method to keep label styling clean and consistent
    private JLabel createStyledLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }
}