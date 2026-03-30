package com.system.view;

import com.system.dao.StudentDatabaseDAO;
import com.system.model.Student;
import javax.swing.*;
import java.awt.*;

public class AddStudentView extends JFrame {

    private JTextField fNameField, lNameField, emailField, phoneField, regNoField;
    private JComboBox<String> progCombo;
    private StudentDatabaseDAO studentDAO;

    public AddStudentView() {
        studentDAO = new StudentDatabaseDAO();

        // 1. Setup Window
        setTitle("Admissions - Register New Student");
        setSize(550, 600); // Taller to fit all the student details
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // 2. The Header
        JLabel headerLabel = new JLabel("Student Registration Form", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(Color.decode("#1A365D"));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0));
        add(headerLabel, BorderLayout.NORTH);

        // 3. The Form Panel (With wide margins)
        JPanel formWrapper = new JPanel(new BorderLayout());
        formWrapper.setBackground(Color.WHITE);
        formWrapper.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 15, 25));
        formPanel.setBackground(Color.WHITE);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Color labelColor = Color.decode("#4A5568");

        formPanel.add(createStyledLabel("First Name:", labelFont, labelColor));
        fNameField = new JTextField();
        formPanel.add(fNameField);

        formPanel.add(createStyledLabel("Last Name:", labelFont, labelColor));
        lNameField = new JTextField();
        formPanel.add(lNameField);

        formPanel.add(createStyledLabel("Email Address:", labelFont, labelColor));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(createStyledLabel("Phone Number:", labelFont, labelColor));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        formPanel.add(createStyledLabel("Registration No:", labelFont, labelColor));
        regNoField = new JTextField();
        formPanel.add(regNoField);

        formPanel.add(createStyledLabel("Programme:", labelFont, labelColor));
        String[] programmes = {"Computer Science", "Software Engineering", "Information Technology", "Data Science"};
        progCombo = new JComboBox<>(programmes);
        progCombo.setBackground(Color.WHITE);
        formPanel.add(progCombo);

        formWrapper.add(formPanel, BorderLayout.CENTER);
        add(formWrapper, BorderLayout.CENTER);

        // 4. The Submit Button (Vibrant Blue)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

        JButton submitBtn = new JButton("Register Student");
        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        submitBtn.setBackground(Color.decode("#3B82F6")); // Modern Blue Accent
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        submitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitBtn.setPreferredSize(new Dimension(250, 45));

        buttonPanel.add(submitBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // 5. Submission Logic
        submitBtn.addActionListener(e -> {
            // Basic validation
            if (fNameField.getText().isEmpty() || lNameField.getText().isEmpty() || regNoField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create a new Student object (personId is 0 initially, DB will auto-generate it)
            Student newStudent = new Student(
                    0,
                    fNameField.getText().trim(),
                    lNameField.getText().trim(),
                    emailField.getText().trim(),
                    phoneField.getText().trim(),
                    regNoField.getText().trim(),
                    (String) progCombo.getSelectedItem()
            );

            studentDAO.saveStudent(newStudent);
            JOptionPane.showMessageDialog(this, "✅ Student Registered Successfully!");

            // Clear fields after saving
            fNameField.setText(""); lNameField.setText(""); emailField.setText("");
            phoneField.setText(""); regNoField.setText("");
        });
    }

    private JLabel createStyledLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }
}