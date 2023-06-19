package model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board {
    private int lines;
    private int coluns;
    private int mines;
    private final List<Field> fields = new ArrayList<>();

    public Board(int lines, int coluns, int mines) {
        this.lines = lines;
        this.coluns = coluns;
        this.mines = mines;
        generatedBoard();
        associetedNeighbord();
        sortedMines();
    }

    private void sortedMines() {
        long minesArmed = 0;
        Predicate<Field> undermined = Field::isMineField;

        do {
            minesArmed = fields.stream().filter(undermined).count();
            int sorted = (int) (Math.random() * fields.size());
            fields.get(sorted).toMine();
        } while (minesArmed < mines);
    }

    public boolean objetiveSucces() {
        return fields.stream().allMatch(f -> f.objetiveSucces());
    }

    public void restart() {
        fields.stream().forEach(f -> f.restart());
        sortedMines();
    }

    private void associetedNeighbord() {
        for (Field fieldOne : fields) {
            for (Field fieldTwo : fields) {
                fieldOne.addNeigbor(fieldTwo);
            }
        }
    }

    private void generatedBoard() {
        for (int line = 0; line < lines; line++) {
            for (int colun = 0; colun < coluns; colun++) {
                fields.add(new Field(line, colun));
            }
        }
    }
}
