import model.Board;
import vision.BoardTerminal;

public class Aplication {
    public static void main(String[] args) {
        Board board = new Board(6, 6, 3);
        new BoardTerminal(board);


    }

}
