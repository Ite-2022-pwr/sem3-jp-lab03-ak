package ite.jp.ak.lab03.client;

import ite.jp.ak.lab03.client.dto.*;
import ite.jp.ak.lab03.client.enums.Panes;
import ite.jp.ak.lab03.client.ui.ViewManager;
import ite.jp.ak.lab03.client.web.requests.*;

import java.io.IOException;
import java.util.Arrays;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientApp extends Application {

    private final ViewManager viewManager = ViewManager.getInstance();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApp.class.getResource("startup-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        viewManager.setMainScene(scene);
        viewManager.addView(Panes.Startup, "startup-view.fxml");
        viewManager.addView(Panes.Citizen, "citizen-view.fxml");
        viewManager.addView(Panes.Controller, "controller-view.fxml");
        viewManager.addView(Panes.Manager, "manager-view.fxml");
        stage.setTitle("System zgłaszania chęci wycinki drzew");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
