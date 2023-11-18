package ite.jp.ak.lab03.client.ui.controllers;

import ite.jp.ak.lab03.client.dto.*;
import ite.jp.ak.lab03.client.enums.SubmissionStatus;
import ite.jp.ak.lab03.client.exceptions.AssignmentAlreadyExistException;
import ite.jp.ak.lab03.client.exceptions.FeedbackAlreadyExistException;
import ite.jp.ak.lab03.client.exceptions.NoFeedbackDescriptionException;
import ite.jp.ak.lab03.client.exceptions.NoReportForSubmissionException;
import ite.jp.ak.lab03.client.ui.ViewManager;
import ite.jp.ak.lab03.client.ui.models.SubmissionTableModel;
import ite.jp.ak.lab03.client.web.facades.ManagerRequestsFacade;
import ite.jp.ak.lab03.client.web.requests.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;

public class ManagerViewControler {
    @FXML
    protected Label citizenLabel;
    @FXML protected Label dateLabel;
    @FXML protected Label statusLabel;
    @FXML protected Label controllerLabel;

    @FXML protected TextArea reportTextArea;
    @FXML protected TextArea feedbackTextArea;

    @FXML protected TableView<SubmissionTableModel> submissionTableView;
    @FXML protected TableColumn<SubmissionTableModel, String> citizenTableColumn;
    @FXML protected TableColumn<SubmissionTableModel, LocalDate> dateTableColumn;
    @FXML protected TableColumn<SubmissionTableModel, SubmissionStatus> statusTableColumn;

    @FXML protected TableView<TreeDto> treeTableView;
    @FXML protected TableColumn<TreeDto, String> treeNameTableColumn;
    @FXML protected TableColumn<TreeDto, Double> treeDiameterTableColumn;

    @FXML protected ComboBox<String> controllerComboBox;
    @FXML protected Button assignButton;

    private final ViewManager viewManager = ViewManager.getInstance();
    private SubmissionTableModel selectedSubmissionTableModel;

    public void initialize() {
        citizenTableColumn.setCellValueFactory(new PropertyValueFactory<>("citizen"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusTableColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        treeNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        treeDiameterTableColumn.setCellValueFactory(new PropertyValueFactory<>("diameter"));

        ControllerDto[] controllers = ControllerApiRequests.getAllControllers();
        for (ControllerDto controller : controllers) {
            controllerComboBox.getItems().add(controller.getUsername());
        }
    }

    public void refresh() {
        SubmissionDto[] submissions = SubmissionApiRequests.getAllSubmissions();
        submissionTableView.getItems().clear();

        for (SubmissionDto submission : submissions) {
            SubmissionTableModel submissionTableModel = new SubmissionTableModel();
            submissionTableModel.setCitizen(submission.getCitizen().getUsername());
            submissionTableModel.setDate(submission.getDate());
            submissionTableModel.setStatus(submission.getStatus());
            submissionTableModel.setId(submission.getId());
            submissionTableModel.setTrees(submission.getTrees());
            submissionTableView.getItems().add(submissionTableModel);
        }
    }

    public void submissionSelectedEvent(MouseEvent event) {
        SubmissionTableModel submissionTableModel = submissionTableView.getSelectionModel().getSelectedItem();
        selectedSubmissionTableModel = submissionTableModel;
        if (submissionTableModel == null)
            return;

        citizenLabel.setText("Obywatel: " + submissionTableModel.getCitizen());
        dateLabel.setText("Data: " + submissionTableModel.getDate().toString());
        statusLabel.setText("Status: " + submissionTableModel.getStatus());
        fillTreeTable(submissionTableModel.getTrees());

        ReportDto tempReport = new ReportDto();
        tempReport.setSubmission(SubmissionTableModel.toDto(submissionTableModel));
        ReportDto report = ReportApiRequests.getReportBySubmissionId(tempReport);

        AssignmentDto tempAssignment = new AssignmentDto();
        tempAssignment.setSubmission(SubmissionTableModel.toDto(submissionTableModel));
        AssignmentDto assignment = AssignmentApiRequests.getAssignmentBySubmissionId(tempAssignment);

        if (report != null || assignment != null) {
            controllerComboBox.setVisible(false);
            assignButton.setVisible(false);
            controllerLabel.setText("Kontroler: " + assignment.getController().getUsername());
            if (report != null)
                reportTextArea.setText(report.getDescription());
            else
                reportTextArea.setText("");
        }
        else {
            controllerComboBox.setVisible(true);
            assignButton.setVisible(true);
            controllerLabel.setText("Kontroler: brak");
            reportTextArea.setText("");
        }

        FeedbackDto tempFeedback = new FeedbackDto();
        tempFeedback.setSubmission(SubmissionTableModel.toDto(submissionTableModel));
        FeedbackDto feedback = FeedbackApiRequests.getFeedbackBySubmissionId(tempFeedback);

        if (feedback != null) {
            feedbackTextArea.setText(feedback.getDescription());
        } else {
            feedbackTextArea.setText("");
        }
    }

    private void fillTreeTable(TreeDto[] trees) {
        ObservableList<TreeDto> treeDtoObservableList = FXCollections.observableArrayList();
        treeDtoObservableList.addAll(trees);
        treeTableView.setItems(treeDtoObservableList);
    }

    public void assignController(ActionEvent event) {
        SubmissionTableModel submissionTableModel = selectedSubmissionTableModel;
        if (submissionTableModel == null)
            return;

        String controllerUsername = controllerComboBox.getSelectionModel().getSelectedItem();
        if (controllerUsername == null)
            return;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        ControllerDto controller;
        try {
            controller = ManagerRequestsFacade.createNewAssignment(SubmissionTableModel.toDto(submissionTableModel), controllerUsername);
            alert.setTitle("Sukces");
            alert.setHeaderText("Przypisano kontrolera");
            alert.setContentText("Zgłoszenie zostało przekazane do kontroli");
            alert.showAndWait();
        } catch (AssignmentAlreadyExistException e) {
            alert.setTitle("Błąd");
            alert.setHeaderText("Nie można przypisać kontrolera");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        controllerComboBox.setVisible(false);
        assignButton.setVisible(false);
        controllerLabel.setText("Kontroler: " + controller.getUsername());
    }

    public void createFeedback(ActionEvent event) {
        SubmissionTableModel submissionTableModel = selectedSubmissionTableModel;
        if (submissionTableModel == null)
            return;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try {
            ManagerRequestsFacade.createFeedbackForSubmission(SubmissionTableModel.toDto(submissionTableModel), viewManager.getLoggedInManager(), feedbackTextArea.getText());
            alert.setTitle("Sukces");
            alert.setHeaderText("Dodano komentarz");
            alert.setContentText("Zgłoszenie zostało zamknięte");
        } catch (FeedbackAlreadyExistException | NoFeedbackDescriptionException | NoReportForSubmissionException e) {
            alert.setTitle("Błąd");
            alert.setHeaderText("Nie można dodać komentarza");
            alert.setContentText(e.getMessage());
        }
        alert.showAndWait();
    }
}
