package com.project.project2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ContractDetailController implements Initializable {

    public Label license_plates;
    public AnchorPane pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setLabel(List<String> strings){
        String[] sm = {""};
        strings.forEach(s -> {
            sm[0] += s.trim().concat(", ");
        });

        license_plates.setText(sm[0]);
    }

    public void setGoBackBtn(ActionEvent actionEvent) throws IOException {
        AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/CreateContract.fxml")));
        pane.getChildren().setAll(dashboard);
    }
}
