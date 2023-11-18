package ite.jp.ak.lab03.client.ui;

import ite.jp.ak.lab03.client.ClientApp;
import ite.jp.ak.lab03.client.dto.CitizenDto;
import ite.jp.ak.lab03.client.dto.ControllerDto;
import ite.jp.ak.lab03.client.dto.ManagerDto;
import ite.jp.ak.lab03.client.enums.Panes;
import ite.jp.ak.lab03.client.exceptions.FxmlLoadException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ViewManager {
    private static ViewManager instance;

    @Getter
    @Setter
    private Scene mainScene;

    @Getter
    @Setter
    private CitizenDto loggedInCitizen;

    @Getter
    @Setter
    private ControllerDto loggedInController;

    @Getter
    @Setter
    private ManagerDto loggedInManager;

    private final Map<Panes, Pane> views = new HashMap<>();

    private ViewManager() {
    }

    public Pane loadViewFromFXML(String filename) throws FxmlLoadException {
        try {
            return new FXMLLoader(ClientApp.class.getResource(filename)).load();
        } catch (IOException e) {
            throw new FxmlLoadException("Nie można załadować pliku: " + filename, e);
        }
    }

    public void addView(Panes pane, String filename) {
        try {
            views.put(pane, loadViewFromFXML(filename));
        } catch(FxmlLoadException e) {
            throw new RuntimeException(e);
        }
    }

    public void setView(Panes pane) {
        mainScene.setRoot(views.get(pane));
    }

    public static ViewManager getInstance() {
        if (instance == null) {
            instance = new ViewManager();
        }
        return instance;
    }


}
