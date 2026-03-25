package com.system.model;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private String isnn;
    private String title;
    private String edition;
    private String version;
    private int yearPublished;

    // A single book can have many physical copies
    private List<BookCopy> copies;

    public Book(String isnn, String title, String edition, String version, int yearPublished) {
        this.isnn = isnn;
        this.title = title;
        this.edition = edition;
        this.version = version;
        this.yearPublished = yearPublished;
        this.copies = new ArrayList<>();
    }

    public void addCopy(BookCopy copy) {
        this.copies.add(copy);
    }

    // Getters
    public String getIsnn() { return isnn; }
    public String getTitle() { return title; }
    public List<BookCopy> getCopies() { return copies; }

    // Business Logic: Count how many copies are actually available right now
    public int getAvailableCopyCount() {
        int count = 0;
        for (BookCopy copy : copies) {
            if (copy.getStatus() == LibraryEnums.CopyStatus.AVAILABLE) {
                count++;
            }
        }
        return count;
    }
}