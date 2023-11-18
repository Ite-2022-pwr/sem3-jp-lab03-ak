package ite.jp.ak.lab03.client.ui.controllers;

import ite.jp.ak.lab03.client.dto.CitizenDto;
import ite.jp.ak.lab03.client.dto.ControllerDto;
import ite.jp.ak.lab03.client.dto.ManagerDto;
import ite.jp.ak.lab03.client.enums.Panes;
import ite.jp.ak.lab03.client.ui.ViewManager;
import ite.jp.ak.lab03.client.web.requests.CitizenApiRequests;
import ite.jp.ak.lab03.client.web.requests.ControllerApiRequests;
import ite.jp.ak.lab03.client.web.requests.ManagerApiRequests;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class StartupViewController {

    @FXML protected TextField inputTextField;
    @FXML protected Button loginButton;
    @FXML protected ComboBox<String> chooseRoleComboBox;

    private String selectedRole;

    private final ViewManager viewManager = ViewManager.getInstance();

    public void roleSelected(ActionEvent event) {
        this.selectedRole = chooseRoleComboBox.getSelectionModel().getSelectedItem();
    }

    public void login(ActionEvent event) {
        String inputUserId = inputTextField.getText();

        if (inputUserId == null || inputUserId.isEmpty()) {
            return;
        }
        if (this.selectedRole == null || this.selectedRole.isEmpty()) {
            return;
        }

        boolean isPesel = false;
        if (inputUserId.matches("^[0-9]{11}$")) {
            isPesel = true;
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText("Nieudana próba logowania");
        alert.setContentText("Nie znaleziono użytkownika: " + inputUserId);

        if (this.selectedRole.equals("Obywatel")) {
            CitizenDto tempCitizen = new CitizenDto();
            CitizenDto newCitizen;

            if (isPesel) {
                tempCitizen.setPesel(inputUserId);
                newCitizen = CitizenApiRequests.getCitizenByPesel(tempCitizen);
            } else {
                tempCitizen.setUsername(inputUserId);
                newCitizen = CitizenApiRequests.getCitizenByUsername(tempCitizen);
            }

            if (newCitizen == null) {
                alert.showAndWait();
                return;
            }

            viewManager.setLoggedInCitizen(newCitizen);
            viewManager.setView(Panes.Citizen);

        } else if (this.selectedRole.equals("Kontroler")) {
            ControllerDto tempController = new ControllerDto();
            ControllerDto newController;

            if (isPesel) {
                tempController.setPesel(inputUserId);
                newController = ControllerApiRequests.getControllerByPesel(tempController);
            } else {
                tempController.setUsername(inputUserId);
                newController = ControllerApiRequests.getControllerByUsername(tempController);
            }

            if (newController == null) {
                alert.showAndWait();
                return;
            }

            viewManager.setLoggedInController(newController);
            viewManager.setView(Panes.Controller);

        } else if (this.selectedRole.equals("Kierownik")) {
            ManagerDto tempManager = new ManagerDto();
            ManagerDto newManager;

            if (isPesel) {
                tempManager.setPesel(inputUserId);
                newManager = ManagerApiRequests.getManagerByPesel(tempManager);
            } else {
                tempManager.setUsername(inputUserId);
                newManager = ManagerApiRequests.getManagerByUsername(tempManager);
            }

            if (newManager == null) {
                alert.showAndWait();
                return;
            }

            viewManager.setLoggedInManager(newManager);
            viewManager.setView(Panes.Manager);

        }
    }
}
