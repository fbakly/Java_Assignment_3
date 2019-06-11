package java2.saxion;

public interface SudokuGame {

    // get number of lines in the puzzle
    int getLinesNumber();

    // get numeric value at position
    int getValue(int i, int j);

    //set numeric value at position
    void setValue(int i, int j, int value);

    //check if a value is valid at position
    boolean isValueSuitableToPutThere(int i, int j, int value);

    //automatically solve the puzzle
    boolean solve();

    //check if puzzle is completed
    boolean checkIfFinished();

    //create a puzzle anew
    void createPuzzle(int digitsToTakeOff);
}
