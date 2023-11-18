package ite.jp.ak.lab03.client.ui.controllers;

import ite.jp.ak.lab03.client.dto.FeedbackDto;
import ite.jp.ak.lab03.client.dto.SubmissionDto;
import ite.jp.ak.lab03.client.dto.TreeDto;
import ite.jp.ak.lab03.client.enums.SubmissionStatus;
import ite.jp.ak.lab03.client.ui.ViewManager;
import ite.jp.ak.lab03.client.web.requests.FeedbackApiRequests;
import ite.jp.ak.lab03.client.web.requests.SubmissionApiRequests;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;

public class CitizenViewController {

    @FXML protected Label statusLabel;
    @FXML protected Label dateLabel;
    @FXML protected TextArea feedbackTextArea;
    @FXML protected Label managerLabel;

    @FXML protected TextField treeNameTextField;
    @FXML protected TextField treeDiameterTextField;

    // Tabela zgłoszeń
    @FXML protected TableView<SubmissionDto> submissionTableView;
    @FXML protected TableColumn<SubmissionDto, SubmissionStatus> statusTableColumn;
    @FXML protected TableColumn<SubmissionDto, LocalDate> dateTableColumn;

    // Tabela drzew w wybranym zgłoszeniu
    @FXML protected TableView<TreeDto> treeTableView;
    @FXML protected TableColumn<TreeDto, String> treeNameTableColumn;
    @FXML protected TableColumn<TreeDto, Double> treeDiameterTableColumn;

    // Tabela drzew do zgłoszenia
    @FXML protected TableView<TreeDto> treeToSubmissionTableView;
    @FXML protected TableColumn<TreeDto, String> treeToSubmissionNameTableColumn;
    @FXML protected TableColumn<TreeDto, Double> treeToSubmissionDiameterTableColumn;

    private final ViewManager viewManager = ViewManager.getInstance();

    public void initialize() {
        statusTableColumn.setCellValueFactory(new PropertyValueFactory<SubmissionDto, SubmissionStatus>("status"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<SubmissionDto, LocalDate>("date"));

        treeToSubmissionNameTableColumn.setCellValueFactory(new PropertyValueFactory<TreeDto, String>("name"));
        treeToSubmissionDiameterTableColumn.setCellValueFactory(new PropertyValueFactory<TreeDto, Double>("diameter"));

        treeNameTableColumn.setCellValueFactory(new PropertyValueFactory<TreeDto, String>("name"));
        treeDiameterTableColumn.setCellValueFactory(new PropertyValueFactory<TreeDto, Double>("diameter"));
    }

    public void addSubmission(ActionEvent event) {
        if (treeToSubmissionTableView.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Niepoprawne dane");
            alert.setHeaderText("Nie dodano żadnego drzewa");
            alert.setContentText("Proszę dodać przynajmniej jedno drzewo");
            alert.showAndWait();
            return;
        }

        var newSubmission = new SubmissionDto();
        newSubmission.setCitizen(viewManager.getLoggedInCitizen());
        newSubmission.setTrees(treeToSubmissionTableView.getItems().toArray(new TreeDto[0]));
        newSubmission.setStatus(SubmissionStatus.Submitted);

        SubmissionApiRequests.createNewSubmission(newSubmission);
        treeToSubmissionTableView.getItems().clear();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Zgłoszenie dodane");
        alert.setHeaderText("Zgłoszenie zostało dodane");
        alert.setContentText("Zgłoszenie zostało dodane");
        alert.showAndWait();

        refresh(event);
    }

    public void addTree(ActionEvent event) {
        if (treeNameTextField.getText().isEmpty() || treeDiameterTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Niepoprawne dane");
            alert.setHeaderText("Nie podano wszystkich danych");
            alert.setContentText("Proszę podać nazwę i średnicę drzewa");
            alert.showAndWait();
            return;
        }

        String newTreeName = treeNameTextField.getText();
        Double newTreeDiameter = null;
        try {
            newTreeDiameter = Double.parseDouble(treeDiameterTextField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Niepoprawne dane");
            alert.setHeaderText("Niepoprawna średnica drzewa");
            alert.setContentText("Proszę podać liczbę");
            alert.showAndWait();
            return;
        }

        var newTree = new TreeDto();
        newTree.setName(newTreeName);
        newTree.setDiameter(newTreeDiameter);

        treeToSubmissionTableView.getItems().add(newTree);
    }

    public void refresh(ActionEvent event) {
        var tempSubmission = new SubmissionDto();
        tempSubmission.setCitizen(viewManager.getLoggedInCitizen());
        SubmissionDto[] submissions = SubmissionApiRequests.getSubmissionsByCitizenId(tempSubmission);
        fillSubmissionTableView(submissions);
    }

    private void fillSubmissionTableView(SubmissionDto[] submissions) {
        submissionTableView.getItems().clear();

        ObservableList<SubmissionDto> data = FXCollections.observableArrayList(submissions);

        submissionTableView.setItems(data);
    }

    public void fillTreeTableView(TreeDto[] trees) {
        treeTableView.getItems().clear();

        ObservableList<TreeDto> data = FXCollections.observableArrayList(trees);

        treeTableView.setItems(data);
    }

    public void submissionSelectedEvent(MouseEvent event) {
        SubmissionDto submission = submissionTableView.getSelectionModel().getSelectedItem();
        if (submission != null) {
            statusLabel.setText("Status: " + submission.getStatus());
            dateLabel.setText("Data: " + submission.getDate());
            fillTreeTableView(submission.getTrees());

            var tempFeedback = new FeedbackDto();
            tempFeedback.setSubmission(submission);
            FeedbackDto feedback = FeedbackApiRequests.getFeedbackBySubmissionId(tempFeedback);
            if (feedback != null) {
                feedbackTextArea.setText(feedback.getDescription());
                managerLabel.setText("Kierownik decyzyjny: " + feedback.getManager().getUsername());
            } else {
                feedbackTextArea.setText("");
                managerLabel.setText("Kierownik decyzyjny: brak");
            }
        }
    }
}
