package com.system.view;

import com.system.dao.StudentDAO;
import com.system.dao.StudentDatabaseDAO;
import com.system.model.Student;

import javax.swing.*;
import java.awt.*;

public class StudentManagementView extends JFrame {

    private JTextField searchField;
    private StudentDAO studentDAO;

    // UI Components for displaying data
    private JLabel nameValue, emailValue, phoneValue, regNoValue, progValue;
    private JButton generateSlipBtn;

    public StudentManagementView() {
        studentDAO = new StudentDatabaseDAO();

        //Setup the Window
        setTitle("Student Management System");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        //Top Panel (Search Bar) ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        topPanel.add(new JLabel("Enter Reg No (e.g., CS-1001): "));
        searchField = new JTextField(15);
        JButton searchBtn = new JButton("Search");
        searchBtn.setFocusPainted(false);
        topPanel.add(searchField);
        topPanel.add(searchBtn);
        add(topPanel, BorderLayout.NORTH);

        //Center Panel (The Modern GUI Display) ---
        JPanel displayPanel = new JPanel(new BorderLayout(10, 10));
        displayPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // Student Details Box (Grid Layout)
        JPanel detailsBox = new JPanel(new GridLayout(5, 2, 5, 15));
        detailsBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Student Profile"));

        generateSlipBtn = new JButton("Generate Official Result Slip");
        generateSlipBtn.setFocusPainted(false);
        generateSlipBtn.setEnabled(false); // Disabled until a student is actually found!

        displayPanel.add(detailsBox, BorderLayout.NORTH);
        displayPanel.add(generateSlipBtn, BorderLayout.SOUTH);

        // Initialize the labels that will hold the data (currently empty)
        Font boldFont = new Font("SansSerif", Font.BOLD, 14);
        nameValue = new JLabel("-"); nameValue.setFont(boldFont);
        emailValue = new JLabel("-"); emailValue.setFont(boldFont);
        phoneValue = new JLabel("-"); phoneValue.setFont(boldFont);
        regNoValue = new JLabel("-"); regNoValue.setFont(boldFont);
        progValue = new JLabel("-"); progValue.setFont(boldFont);

        detailsBox.add(new JLabel("Full Name:")); detailsBox.add(nameValue);
        detailsBox.add(new JLabel("Email Address:")); detailsBox.add(emailValue);
        detailsBox.add(new JLabel("Phone Number:")); detailsBox.add(phoneValue);
        detailsBox.add(new JLabel("Registration No:")); detailsBox.add(regNoValue);
        detailsBox.add(new JLabel("Programme:")); detailsBox.add(progValue);

        // Add the details box to the display panel
        displayPanel.add(detailsBox, BorderLayout.NORTH);
        add(displayPanel, BorderLayout.CENTER);

        //Search Logic ---
        searchBtn.addActionListener(e -> {
            String regNo = searchField.getText().trim();

            if (regNo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Registration Number.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Ask the database for the student
            Student student = studentDAO.searchStudentByRegNo(regNo);

            if (student != null) {
                // Inject the data into the labels
                nameValue.setText(student.getFirstName() + " " + student.getLastName());
                emailValue.setText(student.getEmail());
                phoneValue.setText(student.getPhoneNo());
                regNoValue.setText(student.getRegNo());
                progValue.setText(student.getProgramme());

                // Enable the button and give it the current student's data!
                generateSlipBtn.setEnabled(true);

                // Remove old listeners so we don't open 5 windows if clicked multiple times
                for (java.awt.event.ActionListener al : generateSlipBtn.getActionListeners()) {
                    generateSlipBtn.removeActionListener(al);
                }

                generateSlipBtn.addActionListener(event -> {
                    ResultSlipView slipWindow = new ResultSlipView(student);
                    slipWindow.setVisible(true);
                });

            } else { // 1. Reset all the labels back to dashes
                nameValue.setText("-");
                emailValue.setText("-");
                phoneValue.setText("-");
                regNoValue.setText("-");
                progValue.setText("-");

                // 2. CRITICAL: Lock the button again so it can't be clicked!
                generateSlipBtn.setEnabled(false);

                // 3. Show the error message to the user
                JOptionPane.showMessageDialog(this, "No student found with Reg No: " + regNo, "Not Found", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}