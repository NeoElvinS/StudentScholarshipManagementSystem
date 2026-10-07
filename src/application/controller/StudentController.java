package application.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import application.database.DBConnection;
import application.model.Student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StudentController extends VBox {

    private TextField idField;
    private TextField nameField;
    private TextField departmentField;
    private TextField yearField;
    private TextField cgpaField;
    private TextField incomeField;
    private TextField emailField;

    private TableView<Student> table;

    private ObservableList<Student> studentList =
            FXCollections.observableArrayList();

    private Runnable backAction;

    public StudentController(Runnable backAction) {

        this.backAction = backAction;

        setSpacing(10);
        setPadding(new Insets(20));

        Label title =
                new Label("STUDENT MANAGEMENT");

        idField = new TextField();
        nameField = new TextField();
        departmentField = new TextField();
        yearField = new TextField();
        cgpaField = new TextField();
        incomeField = new TextField();
        emailField = new TextField();

        idField.setPromptText("Student ID");
        nameField.setPromptText("Name");
        departmentField.setPromptText("Department");
        yearField.setPromptText("Year");
        cgpaField.setPromptText("CGPA");
        incomeField.setPromptText("Family Income");
        emailField.setPromptText("Email");

        idField.setDisable(true);

        GridPane form = new GridPane();

        form.setHgap(10);
        form.setVgap(10);

        form.add(new Label("Student ID"), 0, 0);
        form.add(idField, 1, 0);

        form.add(new Label("Name"), 0, 1);
        form.add(nameField, 1, 1);

        form.add(new Label("Department"), 0, 2);
        form.add(departmentField, 1, 2);

        form.add(new Label("Year"), 0, 3);
        form.add(yearField, 1, 3);

        form.add(new Label("CGPA"), 0, 4);
        form.add(cgpaField, 1, 4);

        form.add(new Label("Family Income"), 0, 5);
        form.add(incomeField, 1, 5);

        form.add(new Label("Email"), 0, 6);
        form.add(emailField, 1, 6);

        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");
        Button clearButton = new Button("Clear");
        Button backButton = new Button("Back");

        HBox buttons = new HBox(10);

        buttons.getChildren().addAll(
                addButton,
                updateButton,
                deleteButton,
                clearButton,
                backButton
        );

        table = new TableView<>();

        TableColumn<Student, Integer> idColumn =
                new TableColumn<>("ID");

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("studentId")
        );

        TableColumn<Student, String> nameColumn =
                new TableColumn<>("Name");

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );

        TableColumn<Student, String> departmentColumn =
                new TableColumn<>("Department");

        departmentColumn.setCellValueFactory(
                new PropertyValueFactory<>("department")
        );

        TableColumn<Student, Integer> yearColumn =
                new TableColumn<>("Year");

        yearColumn.setCellValueFactory(
                new PropertyValueFactory<>("year")
        );

        TableColumn<Student, Double> cgpaColumn =
                new TableColumn<>("CGPA");

        cgpaColumn.setCellValueFactory(
                new PropertyValueFactory<>("cgpa")
        );

        TableColumn<Student, Double> incomeColumn =
                new TableColumn<>("Income");

        incomeColumn.setCellValueFactory(
                new PropertyValueFactory<>("familyIncome")
        );

        TableColumn<Student, String> emailColumn =
                new TableColumn<>("Email");

        emailColumn.setCellValueFactory(
                new PropertyValueFactory<>("email")
        );

        table.getColumns().addAll(
                idColumn,
                nameColumn,
                departmentColumn,
                yearColumn,
                cgpaColumn,
                incomeColumn,
                emailColumn
        );

        table.setItems(studentList);

        addButton.setOnAction(e -> addStudent());
        updateButton.setOnAction(e -> updateStudent());
        deleteButton.setOnAction(e -> deleteStudent());
        clearButton.setOnAction(e -> clearFields());
        backButton.setOnAction(e -> backAction.run());

        table.setOnMouseClicked(e -> selectStudent());

        getChildren().addAll(
                title,
                form,
                buttons,
                new Label("Student Records"),
                table
        );

        loadStudents();
    }

    private void addStudent() {

        if (!validateFields()) {
            return;
        }

        String sql =
                "INSERT INTO students " +
                "(name, department, year, cgpa, family_income, email) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, nameField.getText());
            ps.setString(2, departmentField.getText());
            ps.setInt(3, Integer.parseInt(yearField.getText()));
            ps.setDouble(4, Double.parseDouble(cgpaField.getText()));
            ps.setDouble(5, Double.parseDouble(incomeField.getText()));
            ps.setString(6, emailField.getText());

            ps.executeUpdate();

            showMessage(
                    Alert.AlertType.INFORMATION,
                    "Success",
                    "Student added successfully."
            );

            clearFields();
            loadStudents();

        } catch (Exception e) {

            showMessage(
                    Alert.AlertType.ERROR,
                    "Error",
                    e.getMessage()
            );
        }
    }

    private void updateStudent() {

        if (idField.getText().isEmpty()) {

            showMessage(
                    Alert.AlertType.WARNING,
                    "Warning",
                    "Select a student first."
            );

            return;
        }

        if (!validateFields()) {
            return;
        }

        String sql =
                "UPDATE students SET name=?, department=?, " +
                "year=?, cgpa=?, family_income=?, email=? " +
                "WHERE student_id=?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, nameField.getText());
            ps.setString(2, departmentField.getText());
            ps.setInt(3, Integer.parseInt(yearField.getText()));
            ps.setDouble(4, Double.parseDouble(cgpaField.getText()));
            ps.setDouble(5, Double.parseDouble(incomeField.getText()));
            ps.setString(6, emailField.getText());
            ps.setInt(7, Integer.parseInt(idField.getText()));

            ps.executeUpdate();

            showMessage(
                    Alert.AlertType.INFORMATION,
                    "Success",
                    "Student updated successfully."
            );

            clearFields();
            loadStudents();

        } catch (Exception e) {

            showMessage(
                    Alert.AlertType.ERROR,
                    "Error",
                    e.getMessage()
            );
        }
    }

    private void deleteStudent() {

        if (idField.getText().isEmpty()) {

            showMessage(
                    Alert.AlertType.WARNING,
                    "Warning",
                    "Select a student first."
            );

            return;
        }

        String sql =
                "DELETE FROM students WHERE student_id=?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    Integer.parseInt(idField.getText())
            );

            ps.executeUpdate();

            showMessage(
                    Alert.AlertType.INFORMATION,
                    "Success",
                    "Student deleted successfully."
            );

            clearFields();
            loadStudents();

        } catch (Exception e) {

            showMessage(
                    Alert.AlertType.ERROR,
                    "Delete Error",
                    "Student may have an application already.\n"
                    + e.getMessage()
            );
        }
    }

    private void loadStudents() {

        studentList.clear();

        String sql = "SELECT * FROM students";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
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

                studentList.add(student);
            }

        } catch (Exception e) {

            showMessage(
                    Alert.AlertType.ERROR,
                    "Database Error",
                    e.getMessage()
            );
        }
    }

    private void selectStudent() {

        Student student =
                table.getSelectionModel()
                     .getSelectedItem();

        if (student == null) {
            return;
        }

        idField.setText(
                String.valueOf(student.getStudentId())
        );

        nameField.setText(student.getName());
        departmentField.setText(student.getDepartment());

        yearField.setText(
                String.valueOf(student.getYear())
        );

        cgpaField.setText(
                String.valueOf(student.getCgpa())
        );

        incomeField.setText(
                String.valueOf(student.getFamilyIncome())
        );

        emailField.setText(student.getEmail());
    }

    private void clearFields() {

        idField.clear();
        nameField.clear();
        departmentField.clear();
        yearField.clear();
        cgpaField.clear();
        incomeField.clear();
        emailField.clear();

        table.getSelectionModel().clearSelection();
    }

    private boolean validateFields() {

        if (nameField.getText().isEmpty()
                || departmentField.getText().isEmpty()
                || yearField.getText().isEmpty()
                || cgpaField.getText().isEmpty()
                || incomeField.getText().isEmpty()) {

            showMessage(
                    Alert.AlertType.WARNING,
                    "Validation",
                    "Please fill all required fields."
            );

            return false;
        }

        try {

            int year =
                    Integer.parseInt(yearField.getText());

            double cgpa =
                    Double.parseDouble(cgpaField.getText());

            double income =
                    Double.parseDouble(incomeField.getText());

            if (year < 1 || year > 4) {

                showMessage(
                        Alert.AlertType.WARNING,
                        "Validation",
                        "Year must be between 1 and 4."
                );

                return false;
            }

            if (cgpa < 0 || cgpa > 10) {

                showMessage(
                        Alert.AlertType.WARNING,
                        "Validation",
                        "CGPA must be between 0 and 10."
                );

                return false;
            }

            if (income < 0) {

                showMessage(
                        Alert.AlertType.WARNING,
                        "Validation",
                        "Family income cannot be negative."
                );

                return false;
            }

        } catch (NumberFormatException e) {

            showMessage(
                    Alert.AlertType.WARNING,
                    "Validation",
                    "Enter valid numbers for Year, CGPA and Income."
            );

            return false;
        }

        return true;
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