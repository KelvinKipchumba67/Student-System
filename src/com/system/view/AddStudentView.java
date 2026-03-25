package com.system.view;

import com.system.dao.StudentDAO;
import com.system.dao.StudentDatabaseDAO;
import com.system.model.Student;

import javax.swing.*;
import java.awt.*;

public class AddStudentView extends JFrame {

    private JTextField fNameField, lNameField, emailField, phoneField, regNoField, progField;
    private StudentDAO studentDAO;

    public AddStudentView() {
        studentDAO = new StudentDatabaseDAO();

        setTitle("Register New Student");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // So it doesn't close the whole app!

        // Create a panel for the form with some padding
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 20));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Initialize fields
        fNameField = new JTextField();
        lNameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        regNoField = new JTextField();
        progField = new JTextField();

        // Add labels and fields to the panel
        formPanel.add(new JLabel("First Name:"));
        formPanel.add(fNameField);

        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lNameField);

        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);

        formPanel.add(new JLabel("Phone Number:"));
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Registration No:"));
        formPanel.add(regNoField);

        formPanel.add(new JLabel("Programme:"));
        formPanel.add(progField);

        // The Save Button
        JButton saveBtn = new JButton("Save Student");
        saveBtn.setFocusPainted(false);
        formPanel.add(new JLabel("")); // Empty space for alignment
        formPanel.add(saveBtn);

        add(formPanel, BorderLayout.CENTER);

        // Wire up the Save logic
        saveBtn.addActionListener(e -> {
            // Grab text from all fields
            String fName = fNameField.getText().trim();
            String lName = lNameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String regNo = regNoField.getText().trim();
            String prog = progField.getText().trim();

            // Basic validation so they don't submit empty fields
            if (fName.isEmpty() || lName.isEmpty() || regNo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "First Name, Last Name, and Reg No are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create the Java Object
            Student newStudent = new Student(0, fName, lName, email, phone, regNo, prog);

            // Hand it to the Bouncer (DAO) to save to the database!
            studentDAO.saveStudent(newStudent);

            JOptionPane.showMessageDialog(this, "Student " + fName + " " + lName + " saved successfully!");

            // Clear the form for the next entry
            fNameField.setText(""); lNameField.setText(""); emailField.setText("");
            phoneField.setText(""); regNoField.setText(""); progField.setText("");
        });
    }
}