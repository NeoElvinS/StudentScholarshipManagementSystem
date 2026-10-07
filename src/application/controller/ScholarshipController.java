package application.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import application.database.DBConnection;
import application.model.Scholarship;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ScholarshipController extends VBox {

    private TextField idField;
    private TextField nameField;
    private TextField providerField;
    private TextField minCgpaField;
    private TextField maxIncomeField;
    private TextField amountField;

    private DatePicker deadlinePicker;

    private TableView<Scholarship> table;

    private ObservableList<Scholarship> scholarshipList =
            FXCollections.observableArrayList();

    private Runnable backAction;

    public ScholarshipController(Runnable backAction) {

        this.backAction = backAction;

        setSpacing(10);
        setPadding(new Insets(20));

        Label title =
                new Label("SCHOLARSHIP MANAGEMENT");

        idField = new TextField();
        nameField = new TextField();
        providerField = new TextField();
        minCgpaField = new TextField();
        maxIncomeField = new TextField();
        amountField = new TextField();

        deadlinePicker = new DatePicker();

        idField.setPromptText("Scholarship ID");
        nameField.setPromptText("Scholarship Name");
        providerField.setPromptText("Provider");
        minCgpaField.setPromptText("Minimum CGPA");
        maxIncomeField.setPromptText("Maximum Income");
        amountField.setPromptText("Amount");

        idField.setDisable(true);

        GridPane form = new GridPane();

        form.setHgap(10);
        form.setVgap(10);

        form.add(new Label("Scholarship ID"), 0, 0);
        form.add(idField, 1, 0);

        form.add(new Label("Name"), 0, 1);
        form.add(nameField, 1, 1);

        form.add(new Label("Provider"), 0, 2);
        form.add(providerField, 1, 2);

        form.add(new Label("Minimum CGPA"), 0, 3);
        form.add(minCgpaField, 1, 3);

        form.add(new Label("Maximum Income"), 0, 4);
        form.add(maxIncomeField, 1, 4);

        form.add(new Label("Amount"), 0, 5);
        form.add(amountField, 1, 5);

        form.add(new Label("Deadline"), 0, 6);
        form.add(deadlinePicker, 1, 6);

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

        TableColumn<Scholarship, Integer> idColumn =
                new TableColumn<>("ID");

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("scholarshipId")
        );

        TableColumn<Scholarship, String> nameColumn =
                new TableColumn<>("Name");

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );

        TableColumn<Scholarship, String> providerColumn =
                new TableColumn<>("Provider");

        providerColumn.setCellValueFactory(
                new PropertyValueFactory<>("provider")
        );

        TableColumn<Scholarship, Double> minCgpaColumn =
                new TableColumn<>("Min CGPA");

        minCgpaColumn.setCellValueFactory(
                new PropertyValueFactory<>("minCgpa")
        );

        TableColumn<Scholarship, Double> maxIncomeColumn =
                new TableColumn<>("Max Income");

        maxIncomeColumn.setCellValueFactory(
                new PropertyValueFactory<>("maxIncome")
        );

        TableColumn<Scholarship, Double> amountColumn =
                new TableColumn<>("Amount");

        amountColumn.setCellValueFactory(
                new PropertyValueFactory<>("amount")
        );

        TableColumn<Scholarship, LocalDate> deadlineColumn =
                new TableColumn<>("Deadline");

        deadlineColumn.setCellValueFactory(
                new PropertyValueFactory<>("deadline")
        );

        table.getColumns().addAll(
                idColumn,
                nameColumn,
                providerColumn,
                minCgpaColumn,
                maxIncomeColumn,
                amountColumn,
                deadlineColumn
        );

        table.setItems(scholarshipList);

        addButton.setOnAction(e -> addScholarship());
        updateButton.setOnAction(e -> updateScholarship());
        deleteButton.setOnAction(e -> deleteScholarship());
        clearButton.setOnAction(e -> clearFields());
        backButton.setOnAction(e -> backAction.run());

        table.setOnMouseClicked(e -> selectScholarship());

        getChildren().addAll(
                title,
                form,
                buttons,
                new Label("Scholarship Records"),
                table
        );

        loadScholarships();
    }

    private void addScholarship() {

        if (!validateFields()) {
            return;
        }

        String sql =
                "INSERT INTO scholarships " +
                "(name, provider, min_cgpa, max_income, amount, deadline) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, nameField.getText());
            ps.setString(2, providerField.getText());
            ps.setDouble(3,
                    Double.parseDouble(minCgpaField.getText()));
            ps.setDouble(4,
                    Double.parseDouble(maxIncomeField.getText()));
            ps.setDouble(5,
                    Double.parseDouble(amountField.getText()));

            ps.setDate(
                    6,
                    Date.valueOf(deadlinePicker.getValue())
            );

            ps.executeUpdate();

            showMessage(
                    Alert.AlertType.INFORMATION,
                    "Success",
                    "Scholarship added successfully."
            );

            clearFields();
            loadScholarships();

        } catch (Exception e) {

            showMessage(
                    Alert.AlertType.ERROR,
                    "Error",
                    e.getMessage()
            );
        }
    }

    private void updateScholarship() {

        if (idField.getText().isEmpty()) {

            showMessage(
                    Alert.AlertType.WARNING,
                    "Warning",
                    "Select a scholarship first."
            );

            return;
        }

        if (!validateFields()) {
            return;
        }

        String sql =
                "UPDATE scholarships SET name=?, provider=?, " +
                "min_cgpa=?, max_income=?, amount=?, deadline=? " +
                "WHERE scholarship_id=?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, nameField.getText());
            ps.setString(2, providerField.getText());

            ps.setDouble(
                    3,
                    Double.parseDouble(minCgpaField.getText())
            );

            ps.setDouble(
                    4,
                    Double.parseDouble(maxIncomeField.getText())
            );

            ps.setDouble(
                    5,
                    Double.parseDouble(amountField.getText())
            );

            ps.setDate(
                    6,
                    Date.valueOf(deadlinePicker.getValue())
            );

            ps.setInt(
                    7,
                    Integer.parseInt(idField.getText())
            );

            ps.executeUpdate();

            showMessage(
                    Alert.AlertType.INFORMATION,
                    "Success",
                    "Scholarship updated successfully."
            );

            clearFields();
            loadScholarships();

        } catch (Exception e) {

            showMessage(
                    Alert.AlertType.ERROR,
                    "Error",
                    e.getMessage()
            );
        }
    }

    private void deleteScholarship() {

        if (idField.getText().isEmpty()) {

            showMessage(
                    Alert.AlertType.WARNING,
                    "Warning",
                    "Select a scholarship first."
            );

            return;
        }

        String sql =
                "DELETE FROM scholarships WHERE scholarship_id=?";

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
                    "Scholarship deleted successfully."
            );

            clearFields();
            loadScholarships();

        } catch (Exception e) {

            showMessage(
                    Alert.AlertType.ERROR,
                    "Delete Error",
                    "Scholarship may have applications already.\n"
                    + e.getMessage()
            );
        }
    }

    private void loadScholarships() {

        scholarshipList.clear();

        String sql = "SELECT * FROM scholarships";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
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

                scholarshipList.add(scholarship);
            }

        } catch (Exception e) {

            showMessage(
                    Alert.AlertType.ERROR,
                    "Database Error",
                    e.getMessage()
            );
        }
    }

    private void selectScholarship() {

        Scholarship scholarship =
                table.getSelectionModel()
                     .getSelectedItem();

        if (scholarship == null) {
            return;
        }

        idField.setText(
                String.valueOf(
                        scholarship.getScholarshipId()
                )
        );

        nameField.setText(
                scholarship.getName()
        );

        providerField.setText(
                scholarship.getProvider()
        );

        minCgpaField.setText(
                String.valueOf(
                        scholarship.getMinCgpa()
                )
        );

        maxIncomeField.setText(
                String.valueOf(
                        scholarship.getMaxIncome()
                )
        );

        amountField.setText(
                String.valueOf(
                        scholarship.getAmount()
                )
        );

        deadlinePicker.setValue(
                scholarship.getDeadline()
        );
    }

    private void clearFields() {

        idField.clear();
        nameField.clear();
        providerField.clear();
        minCgpaField.clear();
        maxIncomeField.clear();
        amountField.clear();
        deadlinePicker.setValue(null);

        table.getSelectionModel().clearSelection();
    }

    private boolean validateFields() {

        if (nameField.getText().isEmpty()
                || providerField.getText().isEmpty()
                || minCgpaField.getText().isEmpty()
                || maxIncomeField.getText().isEmpty()
                || amountField.getText().isEmpty()
                || deadlinePicker.getValue() == null) {

            showMessage(
                    Alert.AlertType.WARNING,
                    "Validation",
                    "Please fill all fields."
            );

            return false;
        }

        try {

            double minCgpa =
                    Double.parseDouble(
                            minCgpaField.getText()
                    );

            double maxIncome =
                    Double.parseDouble(
                            maxIncomeField.getText()
                    );

            double amount =
                    Double.parseDouble(
                            amountField.getText()
                    );

            if (minCgpa < 0 || minCgpa > 10) {

                showMessage(
                        Alert.AlertType.WARNING,
                        "Validation",
                        "Minimum CGPA must be between 0 and 10."
                );

                return false;
            }

            if (maxIncome < 0 || amount < 0) {

                showMessage(
                        Alert.AlertType.WARNING,
                        "Validation",
                        "Income and amount cannot be negative."
                );

                return false;
            }

        } catch (NumberFormatException e) {

            showMessage(
                    Alert.AlertType.WARNING,
                    "Validation",
                    "Enter valid numeric values."
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