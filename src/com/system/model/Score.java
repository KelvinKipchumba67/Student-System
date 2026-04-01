package com.system.model;

public class Score {
    private double catScore;
    private double examScore;

    public Score(double catScore, double examScore) {
        setCatScore(catScore);
        setExamScore(examScore);
    }

    //Enforcing the Max 30 rule
    public void setCatScore(double catScore) {
        if (catScore < 0 || catScore > 30) {
            throw new IllegalArgumentException("CAT score must be between 0 and 30");
        }
        this.catScore = catScore;
    }

    //Enforcing the Max 70 rule
    public void setExamScore(double examScore) {
        if (examScore < 0 || examScore > 70) {
            throw new IllegalArgumentException("Exam score must be between 0 and 70");
        }
        this.examScore = examScore;
    }

    public double getTotalScore() {
        return catScore + examScore;
    }

    // Calculates the grade for the result slip
    public String getGrade() {
        double total = getTotalScore();
        if (total >= 70) return "A";
        if (total >= 60) return "B";
        if (total >= 50) return "C";
        if (total >= 40) return "D";
        return "F";
    }
}