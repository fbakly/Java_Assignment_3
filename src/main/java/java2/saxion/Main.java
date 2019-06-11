package java2.saxion;

public class Main {

    public static void main(String[] args) {
        var game = new TextGameController();
        var sudoku = new Sudoku(9, 20);
        game.setGameModel(sudoku);
        game.run();
    }

}