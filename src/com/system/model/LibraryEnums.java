package com.system.model;

public class LibraryEnums {

    public enum CopyStatus {
        AVAILABLE, BORROWED
    }

    public enum LoanStatus {
        ACTIVE, RETURNED, OVERDUE
    }

    public enum ReservationStatus {
        PENDING, FULFILLED, CANCELLED
    }
}