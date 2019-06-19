package java2.saxion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        Parent root = loader.load();

        var controller = (Controller)loader.getController();
        var sudoku = new Sudoku(9, 10 );

        // TODO: bind the model to the controller here

        primaryStage.setTitle("Sudoku");
        primaryStage.setScene(new Scene(root, 640, 360));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
//        var game = new TextGameController();
//        var sudoku = new Sudoku(9, 20);
//        game.setGameModel(sudoku);
//        game.run();
        launch(args);
    }

}
