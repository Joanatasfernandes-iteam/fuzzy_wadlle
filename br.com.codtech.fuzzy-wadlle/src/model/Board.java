package model;

import exception.ExceptionExplosed;

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

    public void open(int line, int colun) {
        try {
            fields.stream().filter(field -> field.getLine() == line && field.getColun() == colun)
                    .findFirst()
                    .ifPresent(Field::open);
        } catch (ExceptionExplosed e) {
            fields.forEach(field -> field.setOpen(true));
            throw e;
        }
    }

    public void alternatMarked(int line, int colun) {
        fields.stream().filter(field -> field.getLine() == line && field.getColun() == colun)
                .findFirst()
                .ifPresent(Field::alternatMarked);
    }

    private void sortedMines() {
        long minesArmed = 0;
        Predicate<Field> undermined = Field::isMineField;

        do {
            int sorted = (int) (Math.random() * fields.size());
            fields.get(sorted).toMine();
            minesArmed = fields.stream().filter(undermined).count();
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

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  ");

        for (int colun = 0; colun < coluns; colun++) {
            stringBuilder.append(" ");
            stringBuilder.append(colun);
            stringBuilder.append(" ");
        }
        stringBuilder.append("\n");

        int aux = 0;
        for (int line = 0; line < lines; line++) {
            stringBuilder.append(line);
            stringBuilder.append(" ");

            for (int colun = 0; colun < coluns; colun++) {
                fields.add(new Field(line, colun));
                stringBuilder.append(" ");
                stringBuilder.append(fields.get(aux));
                stringBuilder.append(" ");
                aux++;
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
