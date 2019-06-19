package java2.saxion;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopUp {

    private String message;
    private String title;

    public PopUp(String title, String message) {
        this.message = message;
        this.title = title;
    }

    public void creatPopUp() {
        Stage winPopUp = new Stage();

        winPopUp.initModality(Modality.APPLICATION_MODAL);
        winPopUp.setTitle(title);
        winPopUp.setResizable(false);
        winPopUp.setMinHeight(50);
        winPopUp.setMinWidth(160);

        Label label = new Label();
        label.setText(message);
        label.setAlignment(Pos.CENTER);
        VBox layout = new VBox(10);
        layout.getChildren().add(label);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        winPopUp.setScene(scene);
        winPopUp.show();

    }
}
