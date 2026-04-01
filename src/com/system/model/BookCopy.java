package com.system.model;

public class BookCopy {
    private int copyId;
    private Book book; //Links back to the main Book
    private LibraryEnums.CopyStatus status;

    public BookCopy(int copyId, Book book) {
        this.copyId = copyId;
        this.book = book;
        this.status = LibraryEnums.CopyStatus.AVAILABLE; //Default status
    }

    public int getCopyId() { return copyId; }
    public Book getBook() { return book; }
    public LibraryEnums.CopyStatus getStatus() { return status; }

    public void setStatus(LibraryEnums.CopyStatus status) {
        this.status = status;
    }
}