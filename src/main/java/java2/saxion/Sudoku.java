package java2.saxion;

public class Sudoku implements SudokuGame {

    private int[][] matrix;
    private int numberOfLines;

    public Sudoku(int numberOfLines, int digitsToTakeOff) {
        this.numberOfLines = numberOfLines;
        this.createPuzzle(digitsToTakeOff);
    }

    public Sudoku(int matrix[][]) {
        this.matrix = matrix;
    }

    public void createPuzzle(int digitsToTakeOff) {
        var generator = new Generator(this.numberOfLines, digitsToTakeOff);
        generator.fillValues();
        this.matrix = generator.getMatrix();
    }

    public boolean solve() {
        return this.backtrackSolve();
    }

    public boolean isValueSuitableToPutThere(int i, int j, int value) {
        if (value < 1 || value > 9) {
            return false;
        }

        // Is 'x' used in row.
        for (var jj = 0; jj < numberOfLines; jj++) {
            if (matrix[i][jj] == value) {
                return false;
            }
        }

        // Is 'x' used in column.
        for (var ii = 0; ii < numberOfLines; ii++) {
            if (matrix[ii][j] == value) {
                return false;
            }
        }

        // Is 'x' used in matrix 3x3 box.
        int boxRow = i - i % 3;
        int boxColumn = j - j % 3;

        for (var ii = 0; ii < 3; ii++) {
            for (var jj = 0; jj < 3; jj++) {
                if (matrix[boxRow + ii][boxColumn + jj] == value) {
                    return false;
                }
            }
        }

        // Everything looks good.
        return true;
    }

    private boolean backtrackSolve() {
        var i = 0;
        var j = 0;
        boolean isThereEmptyCell = false;

        for (var y = 0; y < numberOfLines && !isThereEmptyCell; y++) {
            for (var x = 0; x < numberOfLines && !isThereEmptyCell; x++) {
                if (matrix[y][x] == 0) {
                    isThereEmptyCell = true;
                    i = y;
                    j = x;
                }
            }
        }

        // We've done here.
        if (!isThereEmptyCell) {
            return true;
        }

        for (var x = 1; x < 10; x++) {

            if (isValueSuitableToPutThere(i, j, x)) {
                matrix[i][j] = x;

                if (backtrackSolve()) {
                    return true;
                }

                matrix[i][j] = 0; // We've failed.
            }

        }

        return false; // Backtracking
    }

    public boolean checkIfFinished() {
        boolean done = true;
        for (var i = 0; i < 9; i++) {
            for (var j = 0; j < 9; j++) {
                if (matrix[i][j] == 0)
                    done = false;
            }
        }
        return done;
    }

    public void setValue(int i, int j, int value) {
        matrix[i][j] = value;
    }

    public int getValue(int i, int j) {
        return matrix[i][j];
    }

    public int getLinesNumber() {
        return numberOfLines;
    }

}