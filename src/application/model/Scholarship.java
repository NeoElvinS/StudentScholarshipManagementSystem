package application.model;

import java.time.LocalDate;

public class Scholarship {

    private int scholarshipId;
    private String name;
    private String provider;
    private double minCgpa;
    private double maxIncome;
    private double amount;
    private LocalDate deadline;

    public Scholarship(
            int scholarshipId,
            String name,
            String provider,
            double minCgpa,
            double maxIncome,
            double amount,
            LocalDate deadline) {

        this.scholarshipId = scholarshipId;
        this.name = name;
        this.provider = provider;
        this.minCgpa = minCgpa;
        this.maxIncome = maxIncome;
        this.amount = amount;
        this.deadline = deadline;
    }

    public int getScholarshipId() {
        return scholarshipId;
    }

    public void setScholarshipId(int scholarshipId) {
        this.scholarshipId = scholarshipId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public double getMinCgpa() {
        return minCgpa;
    }

    public void setMinCgpa(double minCgpa) {
        this.minCgpa = minCgpa;
    }

    public double getMaxIncome() {
        return maxIncome;
    }

    public void setMaxIncome(double maxIncome) {
        this.maxIncome = maxIncome;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return scholarshipId + " - " + name;
    }
}