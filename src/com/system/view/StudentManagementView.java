package com.system.view;

import com.system.dao.StudentDAO;
import com.system.dao.StudentDatabaseDAO;
import com.system.model.Student;

import javax.swing.*;
import java.awt.*;

public class StudentManagementView extends JFrame {

    private JTextField searchField;
    private StudentDAO studentDAO;

    // UI components
    private JLabel nameValue, emailValue, phoneValue, regNoValue, progValue;
    private JButton generateSlipBtn;

    public StudentManagementView() {
        studentDAO = new StudentDatabaseDAO();

        //Setup
        setTitle("Student Management System - Records");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        //The top search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        searchPanel.setBackground(Color.decode("#1A365D"));

        JLabel searchTitle = new JLabel("Enter Reg No:");
        searchTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        searchTitle.setForeground(Color.WHITE);

        searchField = new JTextField(15);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JButton searchBtn = new JButton("Search Student");
        searchBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchBtn.setBackground(Color.decode("#3B82F6"));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        searchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        searchPanel.add(searchTitle);
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        add(searchPanel, BorderLayout.NORTH);

        //Content panel
        JPanel contentPanel = new JPanel(new BorderLayout(0, 25));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 40, 50));

        //profile card
        JPanel profileCard = new JPanel(new BorderLayout());
        profileCard.setBackground(Color.WHITE);
        profileCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#E2E8F0"), 2),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        //Profile title
        JLabel cardTitle = new JLabel("Official Student Profile");
        cardTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        cardTitle.setForeground(Color.decode("#1A365D"));
        cardTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        profileCard.add(cardTitle, BorderLayout.NORTH);

        //Student details grid
        JPanel detailsGrid = new JPanel(new GridLayout(5, 2, 10, 15));
        detailsGrid.setBackground(Color.WHITE);


        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Color labelColor = Color.decode("#4A5568");
        Font valueFont = new Font("Segoe UI", Font.PLAIN, 15);
        Color valueColor = Color.decode("#1A365D");


        nameValue = createValueLabel("--", valueFont, valueColor);
        emailValue = createValueLabel("--", valueFont, valueColor);
        phoneValue = createValueLabel("--", valueFont, valueColor);
        regNoValue = createValueLabel("--", valueFont, valueColor);
        progValue = createValueLabel("--", valueFont, valueColor);


        detailsGrid.add(createStyledLabel("Full Name:", labelFont, labelColor)); detailsGrid.add(nameValue);
        detailsGrid.add(createStyledLabel("Email Address:", labelFont, labelColor)); detailsGrid.add(emailValue);
        detailsGrid.add(createStyledLabel("Phone Number:", labelFont, labelColor)); detailsGrid.add(phoneValue);
        detailsGrid.add(createStyledLabel("Registration No:", labelFont, labelColor)); detailsGrid.add(regNoValue);
        detailsGrid.add(createStyledLabel("Programme:", labelFont, labelColor)); detailsGrid.add(progValue);

        profileCard.add(detailsGrid, BorderLayout.CENTER);
        contentPanel.add(profileCard, BorderLayout.CENTER);

        JPanel buttonWrapper = new JPanel();
        buttonWrapper.setBackground(Color.WHITE);

        generateSlipBtn = new JButton("Generate Official Result Slip");
        generateSlipBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        generateSlipBtn.setBackground(Color.decode("#10B981"));
        generateSlipBtn.setForeground(Color.WHITE);
        generateSlipBtn.setFocusPainted(false);
        generateSlipBtn.setPreferredSize(new Dimension(300, 45));
        generateSlipBtn.setEnabled(false);
        buttonWrapper.add(generateSlipBtn);
        contentPanel.add(buttonWrapper, BorderLayout.SOUTH);

        add(contentPanel, BorderLayout.CENTER);

        //Search logic
        searchBtn.addActionListener(e -> {
            String regNo = searchField.getText().trim();

            if (regNo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Registration Number.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Student student = studentDAO.searchStudentByRegNo(regNo);

            if (student != null) {
                nameValue.setText("<html><b>" + student.getFirstName() + " " + student.getLastName() + "</b></html>");
                emailValue.setText(student.getEmail());
                phoneValue.setText(student.getPhoneNo());
                regNoValue.setText(student.getRegNo());
                progValue.setText(student.getProgramme());

                generateSlipBtn.setEnabled(true);
                generateSlipBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

                for (java.awt.event.ActionListener al : generateSlipBtn.getActionListeners()) {
                    generateSlipBtn.removeActionListener(al);
                }

                generateSlipBtn.addActionListener(event -> {
                    ResultSlipView slipWindow = new ResultSlipView(student);
                    slipWindow.setVisible(true);
                });

            } else {
                nameValue.setText("--");
                emailValue.setText("--");
                phoneValue.setText("--");
                regNoValue.setText("--");
                progValue.setText("--");

                generateSlipBtn.setEnabled(false);
                generateSlipBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                JOptionPane.showMessageDialog(this, "No student found with Reg No: " + regNo, "Not Found", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JLabel createStyledLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text, SwingConstants.RIGHT);
        label.setFont(font);
        label.setForeground(color);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
        return label;
    }

    // Helper method for the dynamic values
    private JLabel createValueLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }
}