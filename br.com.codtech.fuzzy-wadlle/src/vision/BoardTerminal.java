package vision;

import exception.ExceptionExplosed;
import exception.ExitException;
import model.Board;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class BoardTerminal {
    private Board board;
    private Scanner scanner = new Scanner(System.in);

    public BoardTerminal(Board board) {
        this.board = board;
        playGamer();
    }

    private void playGamer() {
        try {
            boolean toBeContinue = true;
            while (toBeContinue) {
                gameCicle();
                System.out.println("Do you play again? (S/n) ");
                String answer = scanner.nextLine();
                if ("n".equalsIgnoreCase(answer)) {
                    toBeContinue = false;
                } else {
                    board.restart();
                }
            }
        } catch (ExitException e) {
            System.out.println("Bay bay !!!!!");
        } finally {
            scanner.close();
        }
    }

    private void gameCicle() {
        try {
            while (!board.objetiveSucces()) {
                System.out.println(board);
                String digit = captureValueIn(" Insert ( X, Y): ");
                Iterator<Integer> xy = Arrays.stream(digit.split(","))
                        .map(e -> Integer.parseInt(e.trim()))
                        .iterator();
                digit = captureValueIn("1 open or 2 (Mark off) to marked");
                if ("1".equalsIgnoreCase(digit)) {
                    board.open(xy.next(), xy.next());
                } else if ("2".equalsIgnoreCase(digit)) {
                    board.open(xy.next(), xy.next());
                }
            }
            System.out.println("YOU WIN!!!");

        } catch (ExceptionExplosed e) {
            System.out.println(board);
            System.out.println("YOU LOSE!!!");
        }
    }

    private String captureValueIn(String value) {
        System.out.println(value);
        String digiteIn = scanner.nextLine();
        if ("exit".equalsIgnoreCase(digiteIn)) {
            throw new ExitException();
        }
        return digiteIn;
    }

}
