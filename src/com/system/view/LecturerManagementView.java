package com.system.view;

import com.system.dao.LecturerDAO;
import com.system.dao.LecturerDatabaseDAO;
import com.system.model.Course;
import com.system.model.Lecturer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LecturerManagementView extends JFrame {

    private JTextField searchField;
    private LecturerDAO lecturerDAO;

    // UI Components for displaying data
    private JLabel nameValue, emailValue, deptValue, staffNoValue;
    private DefaultTableModel courseTableModel;

    public LecturerManagementView() {
        lecturerDAO = new LecturerDatabaseDAO();

        setTitle("Lecturer Management System");
        setSize(550, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // --- 1. Top Panel (Search Bar) ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        topPanel.add(new JLabel("Enter Staff Number: "));
        searchField = new JTextField(15);
        JButton searchBtn = new JButton("Search");
        searchBtn.setFocusPainted(false);
        topPanel.add(searchField);
        topPanel.add(searchBtn);
        add(topPanel, BorderLayout.NORTH);

        // --- 2. Center Panel (The Modern GUI Display) ---
        JPanel displayPanel = new JPanel(new BorderLayout(10, 10));
        displayPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // 2a. Lecturer Details Box (Grid Layout)
        JPanel detailsBox = new JPanel(new GridLayout(4, 2, 5, 10));
        detailsBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Lecturer Details"));

        // Initialize the labels that will hold the data (currently empty)
        Font boldFont = new Font("SansSerif", Font.BOLD, 14);
        nameValue = new JLabel("-"); nameValue.setFont(boldFont);
        emailValue = new JLabel("-"); emailValue.setFont(boldFont);
        deptValue = new JLabel("-"); deptValue.setFont(boldFont);
        staffNoValue = new JLabel("-"); staffNoValue.setFont(boldFont);

        detailsBox.add(new JLabel("Full Name:")); detailsBox.add(nameValue);
        detailsBox.add(new JLabel("Email Address:")); detailsBox.add(emailValue);
        detailsBox.add(new JLabel("Department:")); detailsBox.add(deptValue);
        detailsBox.add(new JLabel("Staff Number:")); detailsBox.add(staffNoValue);

        // 2b. Courses Table (Replaces the text list)
        JPanel coursesBox = new JPanel(new BorderLayout());
        coursesBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Allocated Courses"));

        String[] columns = {"Course Code", "Course Title"};
        courseTableModel = new DefaultTableModel(columns, 0);
        JTable courseTable = new JTable(courseTableModel);
        courseTable.setRowHeight(25); // Gives the table rows some nice breathing room
        coursesBox.add(new JScrollPane(courseTable), BorderLayout.CENTER);

        // Add the boxes to the main display panel
        displayPanel.add(detailsBox, BorderLayout.NORTH);
        displayPanel.add(coursesBox, BorderLayout.CENTER);
        add(displayPanel, BorderLayout.CENTER);

        // --- 3. The Search Logic ---
        searchBtn.addActionListener(e -> {
            String inputText = searchField.getText().trim();

            if (inputText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Staff Number.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int staffNo = Integer.parseInt(inputText);
                Lecturer lecturer = lecturerDAO.searchLecturerByStaffNo(staffNo);

                // Clear the table before adding new stuff
                courseTableModel.setRowCount(0);

                if (lecturer != null) {
                    // Inject the data into the labels
                    nameValue.setText(lecturer.getFirstName() + " " + lecturer.getLastName());
                    emailValue.setText(lecturer.getEmail());
                    deptValue.setText(lecturer.getDepartment());
                    staffNoValue.setText(String.valueOf(lecturer.getStaffNumber()));

                    // Fetch and populate the table with courses
                    List<Course> courses = lecturerDAO.searchAllocatedCourses(staffNo);
                    for (Course c : courses) {
                        courseTableModel.addRow(new Object[]{c.getCourseCode(), c.getTitle()});
                    }

                } else {
                    // Reset the GUI if no one is found
                    nameValue.setText("-"); emailValue.setText("-");
                    deptValue.setText("-"); staffNoValue.setText("-");
                    JOptionPane.showMessageDialog(this, "No lecturer found with Staff No: " + staffNo, "Not Found", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Staff Number must be a valid number!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}