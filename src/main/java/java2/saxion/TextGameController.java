package java2.saxion;

public class TextGameController {
    private SudokuGame sudoku;

    public void setGameModel(SudokuGame sudoku){
        this.sudoku = sudoku;
    }

    void run() {
        var output = new Output();
        output.printSudoku(sudoku);
        var done = false;
        var x = 0;
        var y = 0;
        var value = 0;
        do {
            output.askForCoordinates();
            var input = new Input();

            x = input.inputInteger();
            y = input.inputInteger();
            output.askForInput();
            value = input.inputInteger();

            output.solvedSudokuMessage();
            if (sudoku.isValueSuitableToPutThere(y, x, value)) {
                sudoku.setValue(y, x, value);
                output.validateChangeMessage();
            } else {
                output.noChangeMessage();
            }
            if (value == 11)
            {
                output.confirmAutosolve();
                if(!sudoku.solve())
                    output.matrixNotSolvable();
                else
                    output.printSudoku(sudoku);
            }

            done = sudoku.checkIfFinished();
            if (done)
                output.congratulationsMessage();
            else
                output.printSudoku(sudoku);
        } while((x != -1 || y != -1 || value != -1) && (!done));
    }
}
