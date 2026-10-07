package application.model;

import java.time.LocalDate;

public class ScholarshipApplication {

    private int applicationId;
    private String studentName;
    private String scholarshipName;
    private LocalDate applicationDate;
    private String status;

    public ScholarshipApplication(
            int applicationId,
            String studentName,
            String scholarshipName,
            LocalDate applicationDate,
            String status) {

        this.applicationId = applicationId;
        this.studentName = studentName;
        this.scholarshipName = scholarshipName;
        this.applicationDate = applicationDate;
        this.status = status;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getScholarshipName() {
        return scholarshipName;
    }

    public void setScholarshipName(String scholarshipName) {
        this.scholarshipName = scholarshipName;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}