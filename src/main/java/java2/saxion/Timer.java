package java2.saxion;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class Timer extends Pane {
    private Timeline animation;
    private Label label;
    private int tmp;
    private BooleanProperty expired;
    private TranslateTransition transition;

    public Timer() {

        label = new Label();
        expired = new SimpleBooleanProperty(false);

        label.setFont(Font.font(20));
        label.setTranslateX(50);
        label.setTranslateY(20);

        getChildren().add(label);

        transition = new TranslateTransition();
        transition.setToX(400);
        transition.setDuration(Duration.seconds(3));
        transition.setAutoReverse(true);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setNode(label);

    }

    public BooleanProperty getExpiredProperty() {
        return this.expired;
    }

    private void timelabel() {
        if (tmp > 0)
            tmp--;
        if (tmp == 0) {
            expired.setValue(true);
        }
        var timeFormat = "Time Left: " + String.valueOf(tmp / 60) + ":" + String.valueOf((tmp % 60 > 9) ? tmp % 60 : "0" + (tmp % 60));
        label.setText(timeFormat);
        label.setTextFill(Paint.valueOf(String.valueOf(Color.valueOf(Color.color(Math.random(), Math.random(), Math.random()).toString()))));
    }

    public void startTimer(int time) {
        label.setTranslateX(50);
        tmp = time;
        expired.setValue(false);
        animation = new Timeline(new KeyFrame(Duration.seconds(1), e -> timelabel()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
        transition.play();
    }

    public void stopTimer() {
        animation.stop();
        transition.stop();
        label.setTranslateX(200);
    }

    public int getTimeLeft() {
        return tmp;
    }

}
