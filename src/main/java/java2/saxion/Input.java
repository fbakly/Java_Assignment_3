package java2.saxion;

import java.util.Scanner;

public class Input {
    private Scanner scanner;

    Input() {
        scanner = new Scanner(System.in);
    }

    int inputInteger() {
        return scanner.nextInt();
    }
}
