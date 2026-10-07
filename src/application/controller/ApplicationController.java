package application.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import application.database.DBConnection;
import application.model.Scholarship;
import application.model.ScholarshipApplication;
import application.model.Student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ApplicationController extends VBox {

    private ComboBox<Student> studentCombo;
    private ComboBox<Scholarship> scholarshipCombo;

    private Label eligibilityLabel;

    private TableView<ScholarshipApplication> table;

    private ObservableList<ScholarshipApplication>
            applicationList =
            FXCollections.observableArrayList();

    private Runnable backAction;

    public ApplicationController(Runnable backAction) {

        this.backAction = backAction;

        setSpacing(10);
        setPadding(new Insets(20));

        Label title =
                new Label("SCHOLARSHIP APPLICATION");

        studentCombo = new ComboBox<>();
        scholarshipCombo = new ComboBox<>();

        studentCombo.setPrefWidth(250);
        scholarshipCombo.setPrefWidth(250);

        GridPane form = new GridPane();

        form.setHgap(10);
        form.setVgap(10);

        form.add(
                new Label("Student"),
                0,
                0
        );

        form.add(
                studentCombo,
                1,
                0
        );

        form.add(
                new Label("Scholarship"),
                0,
                1
        );

        form.add(
                scholarshipCombo,
                1,
                1
        );

        Button eligibilityButton =
                new Button("Check Eligibility");

        Button applyButton =
                new Button("Apply for Scholarship");

        Button approveButton =
                new Button("Approve");

        Button rejectButton =
                new Button("Reject");

        Button refreshButton =
                new Button("Refresh");

        Button backButton =
                new Button("Back");

        eligibilityLabel =
                new Label("Eligibility: Not Checked");

        HBox buttons = new HBox(10);

        buttons.getChildren().addAll(
                eligibilityButton,
                applyButton
        );

        HBox adminButtons = new HBox(10);

        adminButtons.getChildren().addAll(
                approveButton,
                rejectButton,
                refreshButton,
                backButton
        );

        table = new TableView<>();

        TableColumn<ScholarshipApplication, Integer>
                idColumn =
                new TableColumn<>("ID");

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "applicationId"
                )
        );

        TableColumn<ScholarshipApplication, String>
                studentColumn =
                new TableColumn<>("Student");

        studentColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "studentName"
                )
        );

        TableColumn<ScholarshipApplication, String>
                scholarshipColumn =
                new TableColumn<>("Scholarship");

        scholarshipColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "scholarshipName"
                )
        );

        TableColumn<ScholarshipApplication, LocalDate>
                dateColumn =
                new TableColumn<>("Date");

        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "applicationDate"
                )
        );

        TableColumn<ScholarshipApplication, String>
                statusColumn =
                new TableColumn<>("Status");

        statusColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "status"
                )
        );

        table.getColumns().addAll(
                idColumn,
                studentColumn,
                scholarshipColumn,
                dateColumn,
                statusColumn
        );

        table.setItems(applicationList);

        eligibilityButton.setOnAction(
                e -> checkEligibility()
        );

        applyButton.setOnAction(
                e -> applyForScholarship()
        );

        approveButton.setOnAction(
                e -> updateStatus("Approved")
        );

        rejectButton.setOnAction(
                e -> updateStatus("Rejected")
        );

        refreshButton.setOnAction(
                e -> {
                    loadStudents();
                    loadScholarships();
                    loadApplications();
                }
        );

        backButton.setOnAction(
                e -> backAction.run()
        );

        getChildren().addAll(
                title,
                form,
                eligibilityLabel,
                buttons,
                new Label("Applications"),
                table,
                adminButtons
        );

        loadStudents();
        loadScholarships();
        loadApplications();
    }

    private void loadStudents() {

        studentCombo.getItems().clear();

        String sql =
                "SELECT * FROM students";

        try (
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()
        ) {

            while (rs.next()) {

                Student student =
                        new Student(
                                rs.getInt("student_id"),
                                rs.getString("name"),
                                rs.getString("department"),
                                rs.getInt("year"),
                                rs.getDouble("cgpa"),
                                rs.getDouble("family_income"),
                                rs.getString("email")
                        );

                studentCombo.getItems().add(student);
            }

        } catch (Exception e) {

            showMessage(
                    Alert.AlertType.ERROR,
                    "Error",
                    e.getMessage()
            );
        }
    }

    private void loadScholarships() {

        scholarshipCombo.getItems().clear();

        String sql =
                "SELECT * FROM scholarships";

        try (
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()
        ) {

            while (rs.next()) {

                Date sqlDate =
                        rs.getDate("deadline");

                LocalDate deadline =
                        sqlDate == null
                        ? null
                        : sqlDate.toLocalDate();

                Scholarship scholarship =
                        new Scholarship(
                                rs.getInt("scholarship_id"),
                                rs.getString("name"),
                                rs.getString("provider"),
                                rs.getDouble("min_cgpa"),
                                rs.getDouble("max_income"),
                                rs.getDouble("amount"),
                                deadline
                        );

                scholarshipCombo
                        .getItems()
                        .add(scholarship);
            }

        } catch (Exception e) {

            showMessage(
                    Alert.AlertType.ERROR,
                    "Error",
                    e.getMessage()
            );
        }
    }

    private boolean checkEligibility() {

        Student student =
                studentCombo.getValue();

        Scholarship scholarship =
                scholarshipCombo.getValue();

        if (student == null ||
                scholarship == null) {

            showMessage(
                    Alert.AlertType.WARNING,
                    "Warning",
                    "Select a student and scholarship."
            );

            return false;
        }

        boolean eligible =
                student.getCgpa()
                        >= scholarship.getMinCgpa()
                &&
                student.getFamilyIncome()
                        <= scholarship.getMaxIncome();

        if (eligible) {

            eligibilityLabel.setText(
                    "Eligibility: ELIGIBLE"
            );

            return true;

        } else {

            eligibilityLabel.setText(
                    "Eligibility: NOT ELIGIBLE"
            );

            return false;
        }
    }

    private void applyForScholarship() {

        Student student =
                studentCombo.getValue();

        Scholarship scholarship =
                scholarshipCombo.getValue();

        if (student == null ||
                scholarship == null) {

            showMessage(
                    Alert.AlertType.WARNING,
                    "Warning",
                    "Select a student and scholarship."
            );

            return;
        }

        if (!checkEligibility()) {

            showMessage(
                    Alert.AlertType.WARNING,
                    "Not Eligible",
                    "Student is not eligible for this scholarship."
            );

            return;
        }

        String checkSql =
                "SELECT COUNT(*) FROM applications " +
                "WHERE student_id=? AND scholarship_id=?";

        try (
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement checkPs =
                    con.prepareStatement(checkSql)
        ) {

            checkPs.setInt(
                    1,
                    student.getStudentId()
            );

            checkPs.setInt(
                    2,
                    scholarship.getScholarshipId()
            );

            ResultSet rs =
                    checkPs.executeQuery();

            rs.next();

            if (rs.getInt(1) > 0) {

                showMessage(
                        Alert.AlertType.WARNING,
                        "Duplicate Application",
                        "Student has already applied for this scholarship."
                );

                return;
            }

            String insertSql =
                    "INSERT INTO applications " +
                    "(student_id, scholarship_id, " +
                    "application_date, status) " +
                    "VALUES (?, ?, ?, 'Pending')";

            try (
                PreparedStatement ps =
                        con.prepareStatement(insertSql)
            ) {

                ps.setInt(
                        1,
                        student.getStudentId()
                );

                ps.setInt(
                        2,
                        scholarship.getScholarshipId()
                );

                ps.setDate(
                        3,
                        Date.valueOf(LocalDate.now())
                );

                ps.executeUpdate();
            }

            showMessage(
                    Alert.AlertType.INFORMATION,
                    "Success",
                    "Application submitted successfully."
            );

            loadApplications();

        } catch (Exception e) {

            showMessage(
                    Alert.AlertType.ERROR,
                    "Error",
                    e.getMessage()
            );
        }
    }

    private void loadApplications() {

        applicationList.clear();

        String sql =
                "SELECT a.application_id, " +
                "s.name AS student_name, " +
                "sc.name AS scholarship_name, " +
                "a.application_date, " +
                "a.status " +
                "FROM applications a " +
                "JOIN students s " +
                "ON a.student_id=s.student_id " +
                "JOIN scholarships sc " +
                "ON a.scholarship_id=sc.scholarship_id";

        try (
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()
        ) {

            while (rs.next()) {

                Date sqlDate =
                        rs.getDate("application_date");

                LocalDate date =
                        sqlDate.toLocalDate();

                ScholarshipApplication application =
                        new ScholarshipApplication(
                                rs.getInt("application_id"),
                                rs.getString("student_name"),
                                rs.getString("scholarship_name"),
                                date,
                                rs.getString("status")
                        );

                applicationList.add(application);
            }

        } catch (Exception e) {

            showMessage(
                    Alert.AlertType.ERROR,
                    "Database Error",
                    e.getMessage()
            );
        }
    }

    private void updateStatus(String status) {

        ScholarshipApplication selected =
                table.getSelectionModel()
                     .getSelectedItem();

        if (selected == null) {

            showMessage(
                    Alert.AlertType.WARNING,
                    "Warning",
                    "Select an application first."
            );

            return;
        }

        String sql =
                "UPDATE applications " +
                "SET status=? " +
                "WHERE application_id=?";

        try (
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {

            ps.setString(1, status);

            ps.setInt(
                    2,
                    selected.getApplicationId()
            );

            ps.executeUpdate();

            showMessage(
                    Alert.AlertType.INFORMATION,
                    "Success",
                    "Application " +
                    status.toLowerCase() +
                    " successfully."
            );

            loadApplications();

        } catch (Exception e) {

            showMessage(
                    Alert.AlertType.ERROR,
                    "Error",
                    e.getMessage()
            );
        }
    }

    private void showMessage(
            Alert.AlertType type,
            String title,
            String message) {

        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}