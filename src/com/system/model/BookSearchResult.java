package com.system.model;

public class BookSearchResult {
    private String isnn;
    private String title;
    private int availableCopies;
    private int borrowedCopies;

    public BookSearchResult(String isnn, String title, int availableCopies, int borrowedCopies) {
        this.isnn = isnn;
        this.title = title;
        this.availableCopies = availableCopies;
        this.borrowedCopies = borrowedCopies;
    }

    public String getIsnn() { return isnn; }
    public String getTitle() { return title; }
    public int getAvailableCopies() { return availableCopies; }
    public int getBorrowedCopies() { return borrowedCopies; }
}