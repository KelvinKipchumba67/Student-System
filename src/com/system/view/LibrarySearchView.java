package com.system.view;

import com.system.dao.LibraryDAO;
import com.system.model.BookSearchResult;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class LibrarySearchView extends JFrame {
    private JTextField searchField;
    private JTable resultsTable;
    private DefaultTableModel tableModel;
    private LibraryDAO libraryDAO;
    private int loggedInStudentId;

    public LibrarySearchView(int studentId) {
        libraryDAO = new LibraryDAO();
        this.loggedInStudentId = studentId;
        //Setup
        setTitle("University System - Digital Library Search");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); // Pure white background

        JPanel topPanel = new JPanel(new BorderLayout(15, 0));
        topPanel.setBackground(Color.decode("#1A365D")); // Deep University Blue
        topPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel searchLabel = new JLabel("Search Library Catalog:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        searchLabel.setForeground(Color.WHITE);

        searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        topPanel.add(searchLabel, BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"ISBN", "Book Title", "Available Copies", "Borrowed Copies"};
        tableModel = new DefaultTableModel(columns, 0);
        resultsTable = new JTable(tableModel);

        resultsTable.setRowHeight(35);
        resultsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resultsTable.setSelectionBackground(Color.decode("#E2E8F0"));
        resultsTable.setSelectionForeground(Color.BLACK);
        resultsTable.setShowGrid(false);
        resultsTable.setIntercellSpacing(new Dimension(0, 0));
        resultsTable.setEnabled(false);

        JTableHeader header = resultsTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(Color.decode("#F8FAFC"));
        header.setPreferredSize(new Dimension(100, 45));

        JButton borrowBtn = new JButton("Borrow Selected");
        JButton reserveBtn = new JButton("Reserve Selected");



        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        bottomPanel.setBackground(Color.WHITE);

        borrowBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        borrowBtn.setBackground(Color.decode("#8B5CF6")); // Purple accent
        borrowBtn.setForeground(Color.WHITE);
        borrowBtn.setFocusPainted(false);
        borrowBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        reserveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        reserveBtn.setBackground(Color.decode("#10B981")); // Green accent
        reserveBtn.setForeground(Color.WHITE);
        reserveBtn.setFocusPainted(false);
        reserveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bottomPanel.add(reserveBtn);
        bottomPanel.add(borrowBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        resultsTable.setEnabled(true);

        //Search-As-You-Type listener logic
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { triggerSearch(); }
            public void removeUpdate(DocumentEvent e) { triggerSearch(); }
            public void changedUpdate(DocumentEvent e) { triggerSearch(); }
        });

        borrowBtn.addActionListener(e -> {
            int row = resultsTable.getSelectedRow();

            if (row != -1) {
                int availableCopies = (int) tableModel.getValueAt(row, 2);
                if (availableCopies <= 0) {
                    JOptionPane.showMessageDialog(this,
                            "There are no copies currently available to borrow. Please use the 'Reserve' button to join the waitlist.",
                            "Out of Stock",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String isnn = (String) tableModel.getValueAt(row, 0);
                LibraryDAO libraryDAO = new LibraryDAO();

                if (libraryDAO.recordTransaction(loggedInStudentId, isnn, "Borrow")) {
                    JOptionPane.showMessageDialog(this, "Book Borrowed Successfully!");
                    triggerSearch(); // Refresh the table so the count goes down
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a book from the table first.");
            }
        });

        reserveBtn.addActionListener(e -> {
            int row = resultsTable.getSelectedRow();

            if (row != -1) {
                String isnn = (String) tableModel.getValueAt(row, 0);

                LibraryDAO libraryDAO = new LibraryDAO();
                if (libraryDAO.recordTransaction(loggedInStudentId, isnn, "Reserve")) {
                    JOptionPane.showMessageDialog(this,
                            "Book Reserved Successfully! You are now on the waitlist.",
                            "Reservation Confirmed",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select a book from the table first.",
                        "No Book Selected",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        //Loads all books initially
        triggerSearch();
    }

    //This method is called every time a letter is typed or deleted
    private void triggerSearch() {
        String searchText = searchField.getText();

        //Asks the database for matching books
        List<BookSearchResult> results = libraryDAO.searchBooksAsYouType(searchText);

        //Clears the old table data
        tableModel.setRowCount(0);

        //Adds the new results to the table
        for (BookSearchResult book : results) {
            tableModel.addRow(new Object[]{
                    book.getIsnn(),
                    book.getTitle(),
                    book.getAvailableCopies(),
                    book.getBorrowedCopies()
            });
        }
    }
}