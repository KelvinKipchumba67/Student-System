package com.system.view;

import com.system.dao.AddLecturerDAO;
import javax.swing.*;
import java.awt.*;

public class AddLecturerView extends JFrame {

    private JTextField fNameField, lNameField, emailField, phoneField, staffNoField, courseCodeField;
    // Note: I changed YearCombo to start with a lowercase 'y' (yearCombo) to follow Java naming conventions!
    private JComboBox<String> deptCombo, semCombo, yearCombo;

    public AddLecturerView() {
        setTitle("Admin - Add Lecturer & Assign Course");
        setSize(450, 650); // Made slightly taller to fit the 9th row
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Header
        JLabel header = new JLabel("Add New Lecturer", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        header.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(header, BorderLayout.NORTH);

        // Form Grid (Exactly 9 rows for 9 fields)
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        formPanel.add(new JLabel("First Name:"));
        fNameField = new JTextField(); formPanel.add(fNameField);

        formPanel.add(new JLabel("Last Name:"));
        lNameField = new JTextField(); formPanel.add(lNameField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField(); formPanel.add(emailField);

        formPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField(); formPanel.add(phoneField);

        formPanel.add(new JLabel("Staff Number (Digits):"));
        staffNoField = new JTextField(); formPanel.add(staffNoField);

        formPanel.add(new JLabel("Department:"));
        String[] depts = {"Computer Science", "Mathematics", "Engineering"};
        deptCombo = new JComboBox<>(depts); formPanel.add(deptCombo);

        formPanel.add(new JLabel("Course Code (e.g. CSC101):"));
        courseCodeField = new JTextField(); formPanel.add(courseCodeField);

        // Semester Dropdown
        formPanel.add(new JLabel("Semester:"));
        String[] semesters = {"Semester 1", "Semester 2", "Semester 3"};
        semCombo = new JComboBox<>(semesters); formPanel.add(semCombo);

        // FIX: Properly initialized the Year Dropdown
        formPanel.add(new JLabel("Academic Year:"));
        String[] academic_years = {"2023/2024", "2024/2025", "2025/2026"}; // Formatted nicely
        yearCombo = new JComboBox<>(academic_years); formPanel.add(yearCombo);

        // Add the single complete formPanel to the screen
        add(formPanel, BorderLayout.CENTER);

        // Submit Button
        JButton submitBtn = new JButton("Save Lecturer & Course");
        JPanel btnPanel = new JPanel();
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        btnPanel.add(submitBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // Button Action
        submitBtn.addActionListener(e -> {
            try {
                int staffNo = Integer.parseInt(staffNoField.getText().trim());

                AddLecturerDAO dao = new AddLecturerDAO();
                boolean success = dao.addLecturerAndCourse(
                        fNameField.getText().trim(),
                        lNameField.getText().trim(),
                        emailField.getText().trim(),
                        phoneField.getText().trim(),
                        staffNo,
                        (String) deptCombo.getSelectedItem(),
                        courseCodeField.getText().trim(),
                        (String) semCombo.getSelectedItem(),
                        (String) yearCombo.getSelectedItem() // Passed the selected year safely
                );

                if (success) {
                    JOptionPane.showMessageDialog(this, "Lecturer and Course assigned successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to save to database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Staff Number must be numbers only.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}