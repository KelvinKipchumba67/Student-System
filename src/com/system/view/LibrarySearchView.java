package com.system.view;

import com.system.dao.LibraryDAO;
import com.system.model.BookSearchResult;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LibrarySearchView extends JFrame {
    private JTextField searchField;
    private JTable resultsTable;
    private DefaultTableModel tableModel;
    private LibraryDAO libraryDAO;

    public LibrarySearchView() {
        libraryDAO = new LibraryDAO();

        // 1. Setup the Main Window
        setTitle("Library System - Book Search");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centers the window on your screen
        setLayout(new BorderLayout(10, 10));

        // 2. Setup the Top Panel (Search Bar)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Search Book Title: "));
        searchField = new JTextField(30);
        topPanel.add(searchField);
        add(topPanel, BorderLayout.NORTH);

        // 3. Setup the Table (Results)
        String[] columns = {"ISNN", "Book Title", "Available Copies", "Borrowed Copies"};
        tableModel = new DefaultTableModel(columns, 0);
        resultsTable = new JTable(tableModel);
        add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        // 4. Add the "Search-As-You-Type" Listener
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