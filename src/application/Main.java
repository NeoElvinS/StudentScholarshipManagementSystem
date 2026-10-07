package application;

import application.controller.ApplicationController;
import application.controller.ScholarshipController;
import application.controller.StudentController;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage stage;

    @Override
    public void start(Stage stage) {

        this.stage = stage;

        showDashboard();
    }

    private void showDashboard() {

        Label title =
                new Label(
                        "STUDENT SCHOLARSHIP\n" +
                        "MANAGEMENT SYSTEM"
                );

        title.setStyle(
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;"
        );

        Button studentButton =
                new Button("Student Management");

        Button scholarshipButton =
                new Button("Scholarship Management");

        Button applicationButton =
                new Button("Scholarship Applications");

        Button exitButton =
                new Button("Exit");

        studentButton.setPrefWidth(250);
        scholarshipButton.setPrefWidth(250);
        applicationButton.setPrefWidth(250);
        exitButton.setPrefWidth(250);

        studentButton.setPrefHeight(40);
        scholarshipButton.setPrefHeight(40);
        applicationButton.setPrefHeight(40);
        exitButton.setPrefHeight(40);

        studentButton.setOnAction(
                e -> openStudentWindow()
        );

        scholarshipButton.setOnAction(
                e -> openScholarshipWindow()
        );

        applicationButton.setOnAction(
                e -> openApplicationWindow()
        );

        exitButton.setOnAction(
                e -> stage.close()
        );

        VBox layout = new VBox(20);

        layout.setPadding(
                new Insets(40)
        );

        layout.setAlignment(
                Pos.CENTER
        );

        layout.getChildren().addAll(
                title,
                studentButton,
                scholarshipButton,
                applicationButton,
                exitButton
        );

        Scene scene =
                new Scene(
                        layout,
                        800,
                        600
                );

        stage.setTitle(
                "Student Scholarship Management System"
        );

        stage.setScene(scene);

        stage.show();
    }

    private void openStudentWindow() {

        Stage studentStage =
                new Stage();

        StudentController controller =
                new StudentController(
                        studentStage::close
                );

        Scene scene =
                new Scene(
                        controller,
                        1100,
                        700
                );

        studentStage.setTitle(
                "Student Management"
        );

        studentStage.setScene(scene);

        studentStage.show();
    }

    private void openScholarshipWindow() {

        Stage scholarshipStage =
                new Stage();

        ScholarshipController controller =
                new ScholarshipController(
                        scholarshipStage::close
                );

        Scene scene =
                new Scene(
                        controller,
                        1100,
                        700
                );

        scholarshipStage.setTitle(
                "Scholarship Management"
        );

        scholarshipStage.setScene(scene);

        scholarshipStage.show();
    }

    private void openApplicationWindow() {

        Stage applicationStage =
                new Stage();

        ApplicationController controller =
                new ApplicationController(
                        applicationStage::close
                );

        Scene scene =
                new Scene(
                        controller,
                        1100,
                        700
                );

        applicationStage.setTitle(
                "Scholarship Applications"
        );

        applicationStage.setScene(scene);

        applicationStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}