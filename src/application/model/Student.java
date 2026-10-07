package application.model;

public class Student {

    private int studentId;
    private String name;
    private String department;
    private int year;
    private double cgpa;
    private double familyIncome;
    private String email;

    public Student(
            int studentId,
            String name,
            String department,
            int year,
            double cgpa,
            double familyIncome,
            String email) {

        this.studentId = studentId;
        this.name = name;
        this.department = department;
        this.year = year;
        this.cgpa = cgpa;
        this.familyIncome = familyIncome;
        this.email = email;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public double getFamilyIncome() {
        return familyIncome;
    }

    public void setFamilyIncome(double familyIncome) {
        this.familyIncome = familyIncome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return studentId + " - " + name;
    }
}