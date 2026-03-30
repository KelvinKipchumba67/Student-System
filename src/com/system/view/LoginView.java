package com.system.view;

import com.system.dao.UserDAO;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserDAO userDAO;

    public LoginView() {
        userDAO = new UserDAO();

        // 1. Setup the Window
        setTitle("Chuka University - Secure Login");
        setSize(500, 550);
        setLocationRelativeTo(null); // Centers on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Closing login closes the app
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#F4F6F9")); // Light gray desktop background

        // 2. The Main Login Card (White box in the center)
        JPanel cardWrapper = new JPanel(new GridBagLayout());
        cardWrapper.setOpaque(false);

        JPanel loginCard = new JPanel(new BorderLayout(0, 20));
        loginCard.setBackground(Color.WHITE);
        loginCard.setPreferredSize(new Dimension(380, 400));
        loginCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#E2E8F0"), 1), // Subtle border
                BorderFactory.createEmptyBorder(30, 40, 40, 40) // Internal padding
        ));

        // --- A. Card Header ---
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBackground(Color.WHITE);

        JLabel logoLabel = new JLabel("🔒 SECURE PORTAL", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoLabel.setForeground(Color.decode("#3B82F6")); // Blue accent

        JLabel titleLabel = new JLabel("Sign In", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.decode("#1A365D"));

        headerPanel.add(logoLabel);
        headerPanel.add(titleLabel);
        loginCard.add(headerPanel, BorderLayout.NORTH);

        // --- B. The Input Fields ---
        JPanel formPanel = new JPanel(new GridLayout(4, 1, 0, 10));
        formPanel.setBackground(Color.WHITE);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Color labelColor = Color.decode("#4A5568");

        JLabel userLabel = new JLabel("Username / Reg No:");
        userLabel.setFont(labelFont);
        userLabel.setForeground(labelColor);
        userLabel.setVerticalAlignment(SwingConstants.BOTTOM);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#CBD5E1"), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(labelFont);
        passLabel.setForeground(labelColor);
        passLabel.setVerticalAlignment(SwingConstants.BOTTOM);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#CBD5E1"), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        formPanel.add(userLabel);
        formPanel.add(usernameField);
        formPanel.add(passLabel);
        formPanel.add(passwordField);
        loginCard.add(formPanel, BorderLayout.CENTER);

        // --- C. The Login Button ---
        JButton loginBtn = new JButton("Authenticate");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginBtn.setBackground(Color.decode("#1A365D")); // Deep Blue Button
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.setPreferredSize(new Dimension(100, 45));

        loginCard.add(loginBtn, BorderLayout.SOUTH);

        // Add the card to the wrapper, then to the screen
        cardWrapper.add(loginCard);
        add(cardWrapper, BorderLayout.CENTER);

        //auth for admin, lec and student
        loginBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Required Fields", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Ask the database to verify the user
            String role = userDAO.authenticateUser(username, password);

            if (role.equals("Admin")) {
                JOptionPane.showMessageDialog(null, "Welcome, System Administrator.");
                new AdminDashboard().setVisible(true);
            }
            else if (role.equals("Lecturer")) {
                JOptionPane.showMessageDialog(null, "Welcome, Lecturer.");
                new LecturerDashboard(username).setVisible(true);
            }
            else if (role.equals("Student")) {
                JOptionPane.showMessageDialog(null, "Welcome, Student.");
                // THE FIX: Launch the restricted Student Dashboard and hand it the Reg No!
                new StudentDashboard(username).setVisible(true);
            }


        });
    }
}