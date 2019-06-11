package java2.saxion;

public class Output {

    void askForCoordinates() {
        System.out.println("What cell you want to change?\n" +
                "Input the coordinate x and press Enter.\n" +
                "Input the coordinate y and press Enter.\n");
    }

    void askForInput() {
        System.out.println("What value would you like to put there?\n(Put 11 to get a solved Sudoku.)\n");
    }

    void solvedSudokuMessage() {
        System.out.println("Solved Sudoku:\n");
    }

    void validateChangeMessage() {
        System.out.println("Correct! Changed!");
    }

    void noChangeMessage() {
        System.out.println("No changes done!");
    }

    void confirmAutosolve() {
        System.out.println("\n-------AUTOSOLVING!------\n");
    }

    void congratulationsMessage() {
        System.out.println("FINISHED!");
    }

    void matrixNotSolvable() {
        System.out.println("This Sudoku cannot be solved!\n");
    }

    void printSudoku(SudokuGame sudoku) {
        System.out.println("Actual Sudoku:\n");
        var noLines = sudoku.getLinesNumber();
        for (var i = 0; i < noLines; i++) {
            for (var j = 0; j < noLines; j++) {
                System.out.print(( sudoku.getValue(i, j) != 0? sudoku.getValue(i, j) : " " ) + " ");
                if ((j + 1) % 3 == 0)
                    System.out.print("| ");
            }
            System.out.println();
            if (i % 3 == 2) {
                System.out.println("---------------------");
            }
        }
    }

}
