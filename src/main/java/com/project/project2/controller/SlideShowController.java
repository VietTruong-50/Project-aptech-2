package com.project.project2.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class SlideShowController implements Initializable {
    @FXML
    private ImageView imgView;

    private int count;

    @FXML
    public void slideShow() {
        ArrayList<Image> imgs = new ArrayList<>();
        imgs.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/project/project2/Img/22.jpg"))));
        imgs.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/project/project2/Img/33.png"))));
        imgs.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/project/project2/Img/44.jpg"))));
        imgs.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/project/project2/Img/11.jpg"))));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), event -> {
            imgView.setImage(imgs.get(count));
            count++;
            if (count == 4) count = 0;
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        slideShow();
    }
}
