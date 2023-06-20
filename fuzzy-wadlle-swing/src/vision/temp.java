package vision;

import model.Board;

public class temp {
    public static void main(String[] args) {
        Board board = new Board(3,3,9);
        board.alternatMarked(0,0);
        board.alternatMarked(0,1);
        board.alternatMarked(0,2);
        board.alternatMarked(1,0);
        board.alternatMarked(1,1);
        board.alternatMarked(1,2);
        board.alternatMarked(2,0);
        board.alternatMarked(2,1);
        board.alternatMarked(2,2);
    }
}
