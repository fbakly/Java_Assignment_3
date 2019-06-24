package java2.saxion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static javafx.application.Application.launch;

public class Controller implements Initializable {

    private ObservableList<String> difficulties = FXCollections.observableArrayList("Easy", "Medium", "Hard");
    private Sudoku sudoku;
    private TextField[][] textFields = new TextField[9][9];
    private String[][] solvedSudoku = new String[9][9];
    private boolean gotSolvedSudoku = false;
    private boolean timerActive = false;
    private Executor executor = Executors.newCachedThreadPool();
    private Timer timer = new Timer();
    private int numHintsGiven = 0;
    private int maxNumHints;

    @FXML
    private Label availableHintsLabel;
    @FXML
    private Button clearButton;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button solveButton;
    @FXML
    private ChoiceBox difficultyChoiceBox;
    @FXML
    private GridPane gridPane;
    @FXML
    private Button checkButton;
    @FXML
    private Button hintButton;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        difficultyChoiceBox.setValue("Medium");
        difficultyChoiceBox.setItems(difficulties);
        generatePuzzle();

        timer.getExpiredProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.stopGame();
            }
        });
    }

    public void giveHint() {
        var cont = true;

        if (numHintsGiven >= maxNumHints) {
            var hintsExceeded = new PopUp("Hints Exceeded", "Maximum number of\nhints has been reached");
            hintsExceeded.creatPopUp();
        } else {
            for (var row = 0; row < 9 && cont; row++) {
                for (var column = 0; column < 9 && cont; column++) {
                    if (textFields[row][column].getText().isEmpty()) {
                        if (!gotSolvedSudoku)
                            getSolvedSudoku();
                        textFields[row][column].setText(solvedSudoku[row][column]);
                        textFields[row][column].setStyle("-fx-background-color: Pink");
                        textFields[row][column].setEditable(false);
                        cont = false;
                        numHintsGiven++;
                    }
                }
            }
            availableHintsLabel.setText("Hints Left: " + (maxNumHints - numHintsGiven));
        }
    }

    public void stopGame() {
        check();
        Arrays.stream(textFields).forEach(e -> {
            Arrays.stream(e)
                    .forEach(i -> {
                        i.setEditable(false);
                    });
        });
        disableButtons();
        var popUp = new PopUp("You lost", "You ran out of time");
    }

    private AtomicBoolean isGridFull() {
        var noBoxesEmpty = new AtomicBoolean(true);

        if (!gotSolvedSudoku)
            getSolvedSudoku();

        Arrays.stream(textFields).forEach(fields -> {
            Arrays.stream(fields)
                    .forEach(field -> {
                        if (field.getText().isEmpty())
                            noBoxesEmpty.set(false);
                    });
        });
        return noBoxesEmpty;
    }

    @FXML
    public void check() {
        var correct = true;

        if (isGridFull().get()) {

            for (var row = 0; row < 9; row++) {
                for (var column = 0; column < 9; column++) {
                    if (!solvedSudoku[row][column].equals(textFields[row][column].getText())) {
                        correct = false;
                        textFields[row][column].setStyle("-fx-background-color: red");
                    } else {
                        if (textFields[row][column].isEditable()) {
                            textFields[row][column].setStyle("-fx-background-color: green");
                            textFields[row][column].setEditable(false);
                        }
                    }
                }
            }

            if (correct == true) {
                var popUp = new PopUp("Congratulations", "You won");
                disableButtons();
                popUp.creatPopUp();
                timer.stopTimer();
            }
        } else {
            var popUp = new PopUp("Please fill grid", "Complete the puzzle\nfirst");
            popUp.creatPopUp();
        }
    }


    public void disableButtons() {
        solveButton.setDisable(true);
        checkButton.setDisable(true);
        clearButton.setDisable(true);
        hintButton.setDisable(true);
    }

    public void enableButtons() {
        solveButton.setDisable(false);
        checkButton.setDisable(false);
        clearButton.setDisable(false);
        hintButton.setDisable(false);
    }


    public void solve() {
        if (!gotSolvedSudoku)
            getSolvedSudoku();
        resetGrid();
        for (var row = 0; row < 9; row++) {
            for (var column = 0; column < 9; column++) {
                var tempField = new TextField(solvedSudoku[row][column]);
                tempField.setEditable(false);
                tempField.setAlignment(Pos.CENTER);

                if (textFields[row][column].isEditable())
                    tempField.setStyle("-fx-background-color: Orange");

                gridPane.add(tempField, row, column);
                textFields[row][column] = tempField;
            }
        }

        var popUp = new PopUp("Solved", "Too hard for you");
        popUp.creatPopUp();
        disableButtons();
        timer.stopTimer();
    }

    private void resetGrid() {
        gridPane.getChildren().removeAll(gridPane.getChildren());
    }

    private int getNumOfRemovals() {
        var difficulty = difficultyChoiceBox.getValue().toString();
        var numOfRemovals = 0;

        switch (difficulty) {
            case "Easy":
                numOfRemovals = 10;
                break;
            case "Medium":
                numOfRemovals = 20;
                break;
            case "Hard":
                numOfRemovals = 30;
                break;
            default:
                break;
        }

        return numOfRemovals;
    }

    private void getSolvedSudoku() {
        this.sudoku.solve();

        for (var row = 0; row < 9; row++) {
            for (var column = 0; column < 9; column++)
                solvedSudoku[row][column] = String.valueOf(sudoku.getValue(row, column));
        }

        gotSolvedSudoku = true;
    }


    @FXML
    public void clearGrid() {
        Arrays.stream(textFields).forEach(e -> {
            Arrays.stream(e)
                    .forEach(i -> {
                        if (i.isEditable()) {
                            i.setText("");
                            i.setStyle("-fx-text-fill: Blue");
                        }
                    });
        });
    }

    private void fillGrid() {
        for (var row = 0; row < 9; row++) {
            for (var column = 0; column < 9; column++) {
                if (sudoku.getValue(row, column) != 0) {
                    var field = new TextField(String.valueOf(sudoku.getValue(row, column)));
                    gridPane.add(field, row, column);
                    field.setEditable(false);
                    field.setStyle("-fx-text-fill: black");
                    field.setAlignment(Pos.CENTER);
                    textFields[row][column] = field;
                } else {
                    var field = new TextField("");
                    gridPane.add(field, row, column);
                    field.setAlignment(Pos.CENTER);
                    field.setStyle("-fx-text-fill: blue");
                    field.setEditable(true);
                    textFields[row][column] = field;
                }
            }
        }
    }

    @FXML
    public void generatePuzzle() {

        var numOfRemovals = getNumOfRemovals();

        numHintsGiven = 0;
        maxNumHints = numOfRemovals / 4;
        availableHintsLabel.setText("Hints Left: " + maxNumHints);

        resetGrid();
        enableButtons();
        if (timerActive) {
            anchorPane.getChildren().remove(timer);
            timer.stopTimer();
        }

        timer.startTimer(numOfRemovals * 20);
        anchorPane.getChildren().add(timer);

        this.sudoku = new Sudoku(9, numOfRemovals);

        fillGrid();

        gotSolvedSudoku = false;
        timerActive = true;
    }


}
