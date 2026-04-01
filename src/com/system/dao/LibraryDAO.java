package com.system.dao;

import com.system.model.BookSearchResult;
import com.system.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LibraryDAO {

    public List<BookSearchResult> searchBooksAsYouType(String searchText) {
        List<BookSearchResult> results = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();


        //joins the Book and Book_copy tables
        String sql = "SELECT b.isnn, b.title, " +
                "SUM(CASE WHEN c.Status = 'Available' THEN 1 ELSE 0 END) as available_count, " +
                "SUM(CASE WHEN c.Status = 'Borrowed' THEN 1 ELSE 0 END) as borrowed_count " +
                "FROM Book b " +
                "LEFT JOIN Book_copy c ON b.isnn = c.isnn " +
                "WHERE b.title LIKE ? " +
                "GROUP BY b.isnn, b.title";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            // The % signs mean anything before or after the search text
            stmt.setString(1, "%" + searchText + "%");

            ResultSet rs = stmt.executeQuery();// this holds the data sent from the db

            while (rs.next()) {
                results.add(new BookSearchResult(
                        rs.getString("isnn"),
                        rs.getString("title"),
                        rs.getInt("available_count"),
                        rs.getInt("borrowed_count")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    //Borrowing a book logic
    public void borrowBook(int studentId, String isnn) {
        Connection conn = DatabaseConnection.getConnection();
        try {
            //Find an available physical copy of the book
            String findCopySql = "SELECT copy_id FROM Book_copy WHERE isnn = ? AND Status = 'Available' LIMIT 1";
            PreparedStatement findStmt = conn.prepareStatement(findCopySql);
            findStmt.setString(1, isnn);
            ResultSet rs = findStmt.executeQuery();

            if (rs.next()) {
                int copyId = rs.getInt("copy_id");

                //Mark that copy as Borrowed if the book is found
                String updateCopySql = "UPDATE Book_copy SET Status = 'Borrowed' WHERE copy_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateCopySql);
                updateStmt.setInt(1, copyId);
                updateStmt.executeUpdate();

                //Create the official Loan record which is due in 14 days
                String insertLoanSql = "INSERT INTO Book_loan (student_id, copy_id, borrow_date, due_date, Status) " +
                        "VALUES (?, ?, CURRENT_DATE, DATE_ADD(CURRENT_DATE, INTERVAL 14 DAY), 'Active')";
                PreparedStatement loanStmt = conn.prepareStatement(insertLoanSql);
                loanStmt.setInt(1, studentId);
                loanStmt.setInt(2, copyId);
                loanStmt.executeUpdate();

                System.out.println("Success: Student " + studentId + " borrowed copy #" + copyId + " of book " + isnn);
            } else {
                System.out.println("Sorry: No copies of " + isnn + " are currently available. You should reserve it!");
            }
        } catch (Exception e) {
            System.out.println("Error borrowing book: " + e.getMessage());
        }
    }

    //return book logic
    public void returnBook(int studentId, int copyId) {
        Connection conn = DatabaseConnection.getConnection();
        try {
            //Close the active loan
            String updateLoanSql = "UPDATE Book_loan SET return_date = CURRENT_DATE, Status = 'Returned' " +
                    "WHERE student_id = ? AND copy_id = ? AND Status = 'Active'";
            PreparedStatement loanStmt = conn.prepareStatement(updateLoanSql);
            loanStmt.setInt(1, studentId);
            loanStmt.setInt(2, copyId);
            int rowsAffected = loanStmt.executeUpdate();

            if (rowsAffected > 0) {
                // Step B: Make the physical copy 'Available' again
                String updateCopySql = "UPDATE Book_copy SET Status = 'Available' WHERE copy_id = ?";
                PreparedStatement copyStmt = conn.prepareStatement(updateCopySql);
                copyStmt.setInt(1, copyId);
                copyStmt.executeUpdate();

                System.out.println("Success: Student " + studentId + " returned copy #" + copyId);
            } else {
                System.out.println("Error: No active loan found for this student and copy.");
            }
        } catch (Exception e) {
            System.out.println("Error returning book: " + e.getMessage());
        }
    }

    //reserve book logic
    public void reserveBook(int studentId, String isnn) {
        Connection conn = DatabaseConnection.getConnection();
        try {
            //insert into the reservation table
            String sql = "INSERT INTO Book_reservation (student_id, isnn, Status) VALUES (?, ?, 'Pending')";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentId);
            stmt.setString(2, isnn);
            stmt.executeUpdate();

            System.out.println("Success: Student " + studentId + " has been placed on the waitlist for book " + isnn);
        } catch (Exception e) {
            System.out.println("Error reserving book: " + e.getMessage());
        }
    }
}