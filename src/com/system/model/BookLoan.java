package com.system.model;

import java.time.LocalDate;

public class BookLoan {
    private int loanId;
    private Student student;
    private BookCopy copy;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate; // This will be null until the book is actually returned
    private LibraryEnums.LoanStatus status;

    public BookLoan(int loanId, Student student, BookCopy copy, LocalDate borrowDate, LocalDate dueDate) {
        this.loanId = loanId;
        this.student = student;
        this.copy = copy;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.status = LibraryEnums.LoanStatus.ACTIVE;

        //sets status to borrowed
        this.copy.setStatus(LibraryEnums.CopyStatus.BORROWED);
    }

    //handles returning the book
    public void returnBook(LocalDate dateReturned) {
        this.returnDate = dateReturned;
        this.status = LibraryEnums.LoanStatus.RETURNED;
        this.copy.setStatus(LibraryEnums.CopyStatus.AVAILABLE);
    }

    //Getters and Setters...
    public Student getStudent() { return student; }
    public BookCopy getCopy() { return copy; }
}