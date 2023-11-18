package ite.jp.ak.lab03.client.ui.controllers;

import ite.jp.ak.lab03.client.dto.AssignmentDto;
import ite.jp.ak.lab03.client.dto.ReportDto;
import ite.jp.ak.lab03.client.dto.SubmissionDto;
import ite.jp.ak.lab03.client.dto.TreeDto;
import ite.jp.ak.lab03.client.enums.AssignmentStatus;
import ite.jp.ak.lab03.client.enums.SubmissionStatus;
import ite.jp.ak.lab03.client.exceptions.NoReportDescriptionException;
import ite.jp.ak.lab03.client.exceptions.ReportAlreadyExistException;
import ite.jp.ak.lab03.client.ui.ViewManager;
import ite.jp.ak.lab03.client.ui.models.SubmissionTableModel;
import ite.jp.ak.lab03.client.web.facades.ControllerRequestsFacade;
import ite.jp.ak.lab03.client.web.requests.AssignmentApiRequests;
import ite.jp.ak.lab03.client.web.requests.ReportApiRequests;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;

public class ControllerViewController {
    @FXML protected Label citizenLabel;
    @FXML protected Label dateLabel;

    @FXML protected TextArea reportTextArea;

    @FXML protected TableView<SubmissionTableModel> submissionTableView;
    @FXML protected TableColumn<SubmissionTableModel, String> citizenTableColumn;
    @FXML protected TableColumn<SubmissionTableModel, LocalDate> dateTableColumn;
    @FXML protected TableColumn<SubmissionTableModel, SubmissionStatus> statusTableColumn;

    @FXML protected TableView<TreeDto> treeTableView;
    @FXML protected TableColumn<TreeDto, String> treeNameTableColumn;
    @FXML protected TableColumn<TreeDto, Double> treeDiameterTableColumn;

    private final ViewManager viewManager = ViewManager.getInstance();

    private SubmissionTableModel selectedSubmissionTableModel;

    public void initialize() {
        citizenTableColumn.setCellValueFactory(new PropertyValueFactory<>("citizen"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusTableColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        treeNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        treeDiameterTableColumn.setCellValueFactory(new PropertyValueFactory<>("diameter"));
    }

    public void refresh(ActionEvent event) {
        submissionTableView.getItems().clear();
        AssignmentDto tempAssignment = new AssignmentDto();
        tempAssignment.setController(viewManager.getLoggedInController());
        AssignmentDto[] assignments = AssignmentApiRequests.getAssignmentsByControllerId(tempAssignment);

        for (AssignmentDto assignment : assignments) {
            if (assignment.getStatus() == AssignmentStatus.Done)
                continue;

            SubmissionDto submission = assignment.getSubmission();
            SubmissionTableModel submissionTableModel = SubmissionTableModel.fromDto(submission);
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
        fillTreeTable(submissionTableModel.getTrees());

        ReportDto tempReport = new ReportDto();
        tempReport.setSubmission(SubmissionTableModel.toDto(submissionTableModel));
        ReportDto report = ReportApiRequests.getReportBySubmissionId(tempReport);
        if (report != null)
            reportTextArea.setText(report.getDescription());
        else
            reportTextArea.setText("");
    }

    private void fillTreeTable(TreeDto[] trees) {
        ObservableList<TreeDto> treeDtoObservableList = FXCollections.observableArrayList();
        treeDtoObservableList.addAll(trees);
        treeTableView.setItems(treeDtoObservableList);
    }

    public void makeReport(ActionEvent event) {
        SubmissionTableModel submissionTableModel = selectedSubmissionTableModel;
        if (submissionTableModel == null)
            return;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try {
            ControllerRequestsFacade.createReportForSubmission(SubmissionTableModel.toDto(submissionTableModel), viewManager.getLoggedInController(), reportTextArea.getText());
            alert.setTitle("Sukces");
            alert.setHeaderText("Złożono raport");
            alert.setContentText("Raport został wysłany");
        } catch (ReportAlreadyExistException | NoReportDescriptionException e) {
            alert.setTitle("Błąd");
            alert.setHeaderText("Nie można złożyć raportu");
            alert.setContentText(e.getMessage());
        }

        alert.showAndWait();

    }
}
