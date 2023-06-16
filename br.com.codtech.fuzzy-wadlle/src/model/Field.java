package model;

import esception.ExceptionExplosed;

import java.util.ArrayList;
import java.util.List;

public class Field {
    public Field(int line, int colun) {
        this.line = line;
        this.colun = colun;
    }

    private int line;
    private int colun;
    private boolean mineField;
    private boolean open;
    private boolean marked;

    private final List<Field> neighbors = new ArrayList<>();


    public boolean addNeigbor(Field neighbor) {
        boolean diferentnLine = line != neighbor.line;
        boolean diferentColun = colun != neighbor.colun;
        boolean diagonal = diferentnLine && diferentColun;

        int deltaLine = Math.abs(line - neighbor.line);
        int deltaColun = Math.abs(line - neighbor.colun);
        int generalDelta = deltaColun + deltaLine;

        if (generalDelta == 1 && !diagonal) {
            neighbors.add(neighbor);
            return true;
        } else if (generalDelta == 2 && diagonal) {
            neighbors.add(neighbor);
            return true;
        } else {
            return false;
        }
    }

    public void alternatMarked() {
        if (!open) {
            marked = !marked;
        }
    }

    public void toMine() {
        mineField = true;
    }

    public boolean open() {
        if (!open && !marked) {
            open = true;
            if (mineField) {
                throw new ExceptionExplosed();
            }

            if (safeNeighbord()) {
                neighbors.forEach(v -> v.open());
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean safeNeighbord() {
        return neighbors.stream().noneMatch(v -> v.mineField);
    }

    public boolean isMarked() {
        return marked;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isClosed() {
        return !isOpen();
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColun() {
        return colun;
    }

    public void setColun(int colun) {
        this.colun = colun;
    }

    public boolean objetiveSucces() {
        boolean unraveled = !mineField && open;
        boolean protect = mineField && marked;
        return unraveled || protect;
    }

    public long mineOfNeighbor() {
        return neighbors.stream().filter(v -> v.mineField).count();
    }

    public void restart() {
        open = false;
        mineField = false;
        marked = false;
    }

    public String toString() {
        if (marked) {
            return "X";
        } else if (open && mineField) {
            return "*";
        } else if (open && mineOfNeighbor() > 0) {
            return Long.toString(mineOfNeighbor());
        } else if (open) {
            return " ";
        } else {
            return "?";
        }
    }
}
