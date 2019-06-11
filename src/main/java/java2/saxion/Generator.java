package java2.saxion;

class Generator {
    private int[][] matrix;
    private int numberOfLines;
    private int sqrtOfNrOfLines;
    private int digitsToTakeOff;

    // Constructor that takes as parameters:
    //  - numberOfLines - shows how big the Sudoku Table will be
    //  - digitsToTakeOff - which digits need to be replaced by 0 from the solution (Higher number == Higher Game difficulty)
    Generator(int numberOfLines, int digitsToTakeOff) {
        this.numberOfLines = numberOfLines;
        this.digitsToTakeOff = digitsToTakeOff;

        // Compute square root of numberOfLines
        sqrtOfNrOfLines = (int) Math.sqrt(numberOfLines);

        matrix = new int[numberOfLines][numberOfLines];
    }

    // Sudoku Generator
    public void fillValues() {
        // Fill the diagonal of Sudoku matrices
        fillDiagonal();

        // Fill remaining blocks of the matrices
        fillRemaining(0, sqrtOfNrOfLines);

        // Remove Randomly digitsToTakeOff digits to make game
        removeKDigits();
    }

    // Fill the diagonal sqrtOfNrOfLines number of matrices
    private void fillDiagonal() {
        for (var i = 0; i < numberOfLines; i = i + sqrtOfNrOfLines)
            // for diagonal box, start coordinates->i==j
            fillBox(i, i);
    }

    // Returns false if given 3 x 3 block contains number
    boolean unUsedInBox(int rowStart, int colStart, int num) {
        for (var i = 0; i < sqrtOfNrOfLines; i++)
            for (var j = 0; j < sqrtOfNrOfLines; j++)
                if (matrix[rowStart + i][colStart + j] == num)
                    return false;

        return true;
    }

    // Fill a 3 x 3 matrix
    private void fillBox(int row, int col) {
        int num;
        for (var i = 0; i < sqrtOfNrOfLines; i++) {
            for (var j = 0; j < sqrtOfNrOfLines; j++) {
                do {
                    num = randomGenerator(numberOfLines);
                }
                while (!unUsedInBox(row, col, num));

                matrix[row + i][col + j] = num;
            }
        }
    }

    // Random generator
    private int randomGenerator(int num) {
        return (int) Math.floor((Math.random() * num + 1));
    }

    // Check if safe to put in cell
    private boolean CheckIfSafe(int i, int j, int num) {
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i - i % sqrtOfNrOfLines, j - j % sqrtOfNrOfLines, num));
    }

    // check in the row for existence
    private boolean unUsedInRow(int i, int num) {
        for (var j = 0; j < numberOfLines; j++)
            if (matrix[i][j] == num)
                return false;
        return true;
    }

    // check in the row for existence
    private boolean unUsedInCol(int j, int num) {
        for (var i = 0; i < numberOfLines; i++)
            if (matrix[i][j] == num)
                return false;
        return true;
    }

    // A recursive function to fill remaining matrix
    private boolean fillRemaining(int i, int j) {
        if (j >= numberOfLines && i < numberOfLines - 1) {
            i++;
            j = 0;
        }
        if (i >= numberOfLines && j >= numberOfLines)
            return true;

        if (i < sqrtOfNrOfLines) {
            if (j < sqrtOfNrOfLines)
                j = sqrtOfNrOfLines;
        } else if (i < numberOfLines - sqrtOfNrOfLines) {
            if (j == (i / sqrtOfNrOfLines) * sqrtOfNrOfLines)
                j = j + sqrtOfNrOfLines;
        } else {
            if (j == numberOfLines - sqrtOfNrOfLines) {
                i = i + 1;
                j = 0;
                if (i >= numberOfLines)
                    return true;
            }
        }

        for (var num = 1; num <= numberOfLines; num++) {
            if (CheckIfSafe(i, j, num)) {
                matrix[i][j] = num;
                if (fillRemaining(i, j + 1))
                    return true;

                matrix[i][j] = 0;
            }
        }
        return false;
    }

    // Remove the digitsToTakeOff no. of digits to complete game
    private void removeKDigits() {
        var count = digitsToTakeOff;
        while (count != 0) {
            var cellId = randomGenerator(numberOfLines * numberOfLines - 1);
            // extract coordinates i and j
            var i = (cellId / numberOfLines);
            var j = cellId % 9;
            if (j != 0)
                j = j - 1;

            if (matrix[i][j] != 0) {
                count--;
                matrix[i][j] = 0;
            }
        }
    }

    //Get Sudoku matrix
    int[][] getMatrix() {
        return matrix;
    }
}
