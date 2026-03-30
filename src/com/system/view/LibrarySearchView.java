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

    public LibrarySearchView() {
        libraryDAO = new LibraryDAO();

        // 1. Setup the Main Window (Made larger for a modern dashboard feel)
        setTitle("Chuka University - Digital Library Search");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); // Pure white background

        // 2. Setup the Top Panel (Deep Blue Banner with Search Bar)
        JPanel topPanel = new JPanel(new BorderLayout(15, 0));
        topPanel.setBackground(Color.decode("#1A365D")); // Deep University Blue
        topPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30)); // Generous padding

        JLabel searchLabel = new JLabel("Search Library Catalog:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        searchLabel.setForeground(Color.WHITE);

        searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        // Adds a white border and internal padding so the text isn't cramped
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        topPanel.add(searchLabel, BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // 3. Setup the Table (Results)
        String[] columns = {"ISBN", "Book Title", "Available Copies", "Borrowed Copies"};
        tableModel = new DefaultTableModel(columns, 0);
        resultsTable = new JTable(tableModel);

        // --- MODERN UI UPGRADES FOR THE TABLE ---
        resultsTable.setRowHeight(35); // Gives the text breathing room
        resultsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resultsTable.setSelectionBackground(Color.decode("#E2E8F0")); // Soft gray/blue when clicked
        resultsTable.setSelectionForeground(Color.BLACK);
        resultsTable.setShowGrid(false); // Removes the ugly default grid lines
        resultsTable.setIntercellSpacing(new Dimension(0, 0));
        resultsTable.setEnabled(false); // Makes it read-only for the user

        // Style the Table Header
        JTableHeader header = resultsTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(Color.decode("#F8FAFC")); // Very light gray header background
        header.setForeground(Color.decode("#4A5568")); // Dark gray text
        header.setPreferredSize(new Dimension(100, 45)); // Taller header
        // ----------------------------------------

        // Wrap the table in a scroll pane and give it invisible margins
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // 4. Add the "Search-As-You-Type" Listener (Unchanged Logic)
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { triggerSearch(); }
            public void removeUpdate(DocumentEvent e) { triggerSearch(); }
            public void changedUpdate(DocumentEvent e) { triggerSearch(); }
        });

        // Load all books initially
        triggerSearch();
    }

    // This method is called every time a letter is typed or deleted
    private void triggerSearch() {
        String searchText = searchField.getText();

        // Ask the database for matching books
        List<BookSearchResult> results = libraryDAO.searchBooksAsYouType(searchText);

        // Clear the old table data
        tableModel.setRowCount(0);

        // Add the new results to the table
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