module client {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires spring.webflux;
    requires com.fasterxml.jackson.databind;
    requires spring.web;
    requires spring.context;
    requires reactor.core;

    opens ite.jp.ak.lab03.client.ui.controllers to javafx.fxml, javafx.graphics;
    opens ite.jp.ak.lab03.client to javafx.fxml, javafx.graphics;
    opens ite.jp.ak.lab03.client.enums to com.fasterxml.jackson.databind, javafx.fxml;
    opens ite.jp.ak.lab03.client.ui.models to javafx.base;
    opens ite.jp.ak.lab03.client.dto to com.fasterxml.jackson.databind, java.base, javafx.base;
    opens ite.jp.ak.lab03.client.ui to javafx.fxml, javafx.graphics;

}