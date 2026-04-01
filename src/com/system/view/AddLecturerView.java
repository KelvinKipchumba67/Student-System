package com.system.view;

import com.system.dao.AddLecturerDAO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddLecturerView extends JFrame {

    private JTextField fNameField, lNameField, emailField, phoneField, staffNoField, courseCodeField;
    private JComboBox<String> deptCombo, semCombo, yearCombo;

    public AddLecturerView() {
        //Window Configuration
        setTitle("University Admin - Lecturer Onboarding");
        setSize(500, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#F4F6F9")); // Light gray desktop background

        //Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.decode("#1A365D")); // Deep Blue
        headerPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        JLabel titleLabel = new JLabel("Lecturer Registration");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Admin Portal: Add Staff & Assign Coursework");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.decode("#A0AEC0"));

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);

        //Form Card
        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 30));
        centerWrapper.setOpaque(false);

        JPanel formCard = new JPanel(new GridLayout(9, 2, 15, 20));
        formCard.setBackground(Color.WHITE);
        formCard.setPreferredSize(new Dimension(420, 500));
        formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#E2E8F0"), 1),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        //Styling for Labels
        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Color labelColor = Color.decode("#4A5568");

        //Adding Fields
        formCard.add(createLabel("First Name:", labelFont, labelColor));
        fNameField = createTextField(); formCard.add(fNameField);

        formCard.add(createLabel("Last Name:", labelFont, labelColor));
        lNameField = createTextField(); formCard.add(lNameField);

        formCard.add(createLabel("Email Address:", labelFont, labelColor));
        emailField = createTextField(); formCard.add(emailField);

        formCard.add(createLabel("Phone Number:", labelFont, labelColor));
        phoneField = createTextField(); formCard.add(phoneField);

        formCard.add(createLabel("Staff Number:", labelFont, labelColor));
        staffNoField = createTextField(); formCard.add(staffNoField);

        formCard.add(createLabel("Department:", labelFont, labelColor));
        deptCombo = new JComboBox<>(new String[]{"Computer Science", "Mathematics", "Engineering"});
        styleCombo(deptCombo); formCard.add(deptCombo);

        formCard.add(createLabel("Course Code:", labelFont, labelColor));
        courseCodeField = createTextField(); formCard.add(courseCodeField);

        formCard.add(createLabel("Semester:", labelFont, labelColor));
        semCombo = new JComboBox<>(new String[]{"Semester 1", "Semester 2", "Semester 3"});
        styleCombo(semCombo); formCard.add(semCombo);

        formCard.add(createLabel("Academic Year:", labelFont, labelColor));
        yearCombo = new JComboBox<>(new String[]{"2024/2025", "2025/2026", "2026/2027"});
        styleCombo(yearCombo); formCard.add(yearCombo);

        centerWrapper.add(formCard);
        add(centerWrapper, BorderLayout.CENTER);

        //Submit Button
        JPanel footerPanel = new JPanel();
        footerPanel.setOpaque(false);
        footerPanel.setBorder(new EmptyBorder(0, 0, 40, 0));

        JButton submitBtn = new JButton("Register & Assign");
        submitBtn.setPreferredSize(new Dimension(300, 50));
        submitBtn.setBackground(Color.decode("#3B82F6")); // modern blue accent
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        submitBtn.setFocusPainted(false);
        submitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitBtn.setBorder(BorderFactory.createEmptyBorder());

        footerPanel.add(submitBtn);
        add(footerPanel, BorderLayout.SOUTH);

        //Logic
        submitBtn.addActionListener(e -> {
            try {
                int staffNo = Integer.parseInt(staffNoField.getText().trim());
                AddLecturerDAO dao = new AddLecturerDAO();
                boolean success = dao.addLecturerAndCourse(
                        fNameField.getText().trim(), lNameField.getText().trim(),
                        emailField.getText().trim(), phoneField.getText().trim(),
                        staffNo, (String) deptCombo.getSelectedItem(),
                        courseCodeField.getText().trim(), (String) semCombo.getSelectedItem(),
                        (String) yearCombo.getSelectedItem()
                );

                if (success) {
                    JOptionPane.showMessageDialog(this, "Lecturer onboarded successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Database Error. Ensure course exists.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Staff Number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    //Helper methods for styling
    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#CBD5E1"), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return field;
    }

    private void styleCombo(JComboBox<String> combo) {
        combo.setBackground(Color.WHITE);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }
}