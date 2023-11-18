package ite.jp.ak.lab03.client.ui.controllers;

import ite.jp.ak.lab03.client.dto.*;
import ite.jp.ak.lab03.client.enums.AssignmentStatus;
import ite.jp.ak.lab03.client.enums.SubmissionStatus;
import ite.jp.ak.lab03.client.ui.ViewManager;
import ite.jp.ak.lab03.client.ui.models.SubmissionTableModel;
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

        ControllerDto tempController = new ControllerDto();
        tempController.setUsername(controllerUsername);
        ControllerDto controller = ControllerApiRequests.getControllerByUsername(tempController);

        SubmissionDto submissionDto = SubmissionTableModel.toDto(submissionTableModel);
        submissionDto = SubmissionApiRequests.getSubmissionById(submissionDto);

        // Przypisanie kontrolera do zgłoszenia
        AssignmentDto assignment = new AssignmentDto();
        assignment.setController(controller);
        assignment.setSubmission(submissionDto);

        // Sprawdzenie czy zgłoszenie nie zostało już przydzielone
        AssignmentDto tempAssignment = AssignmentApiRequests.getAssignmentBySubmissionId(assignment);
        if (tempAssignment != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd");
            alert.setHeaderText("Zgłoszenie zostało już przydzielone");
            alert.setContentText("Zgłoszenie zostało już przekazane do kontrolera");
            alert.showAndWait();
            return;
        }

        // Utworzenie nowego zadania
        assignment.setStatus(AssignmentStatus.Remaining);
        AssignmentApiRequests.createNewAssignment(assignment);

        // Zmiana statusu zgłoszenia
        submissionDto.setStatus(SubmissionStatus.Accepted);
        SubmissionApiRequests.updateSubmission(submissionDto);

        controllerComboBox.setVisible(false);
        assignButton.setVisible(false);
        controllerLabel.setText("Kontroler: " + controller.getUsername());
    }

    public void createFeedback(ActionEvent event) {
        SubmissionTableModel submissionTableModel = selectedSubmissionTableModel;
        if (submissionTableModel == null)
            return;

        // Sprawdzenie czy istnieje raport dla tego zgłoszenia
        ReportDto tempReport = new ReportDto();
        tempReport.setSubmission(SubmissionTableModel.toDto(submissionTableModel));
        ReportDto report = ReportApiRequests.getReportBySubmissionId(tempReport);
        if (report == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd");
            alert.setHeaderText("Brak raportu");
            alert.setContentText("Zgłoszenie nie zostało jeszcze skontrolowane");
            alert.showAndWait();
            return;
        }

        String feedbackDescription = feedbackTextArea.getText();
        if (feedbackDescription == null || feedbackDescription.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Niepoprawne dane");
            alert.setHeaderText("Niepoprawny komentarz");
            alert.setContentText("Proszę napisać treść komentarza");
            alert.showAndWait();
            return;
        }

        SubmissionDto submissionDto = SubmissionTableModel.toDto(submissionTableModel);
        submissionDto = SubmissionApiRequests.getSubmissionById(submissionDto);

        FeedbackDto feedback = new FeedbackDto();
        feedback.setSubmission(submissionDto);

        // Sprawdzenie czy zgłoszenie nie zostało już ocenione
        FeedbackDto tempfeedback = FeedbackApiRequests.getFeedbackBySubmissionId(feedback);
        if (tempfeedback != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd");
            alert.setHeaderText("Zgłoszenie zostało już zakończone");
            alert.setContentText("Komentarz został już dodany, zgłoszenie zostało już zakończone");
            alert.showAndWait();
            return;
        }


        feedback.setDescription(feedbackDescription);
        feedback.setManager(viewManager.getLoggedInManager());

        FeedbackApiRequests.createNewFeedback(feedback);

        // Zamknięcie zgłoszenia
        submissionDto.setStatus(SubmissionStatus.Completed);
        SubmissionApiRequests.updateSubmission(submissionDto);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Komentarz dodany");
        alert.setHeaderText("Komentarz został dodany");
        alert.setContentText("Zgłoszenie zostało zakończone");
        alert.showAndWait();

        feedbackTextArea.setText("");
    }
}
