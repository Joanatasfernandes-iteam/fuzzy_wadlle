package model;

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

    private List<Field> neighbors = new ArrayList<>();
    private List<CampoObservador> observadores = new ArrayList<>();
//    private List<BiConsumer<Field, CampoEvento>> consumers = new ArrayList<>();

    public void resgistrarObservador(CampoObservador observador) {
        observadores.add(observador);
    }

    private void notificarObeservadores(CampoEvento evento) {
        observadores.stream().forEach(observer -> observer.eventoOcorreu(this, evento));
    }

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
            if (marked) {
                notificarObeservadores(CampoEvento.MARCAR);
            } else {
                notificarObeservadores(CampoEvento.DESMARCAR);
            }
        }
    }

    public void toMine() {
        mineField = true;
    }

    public boolean open() {
        if (!open && !marked) {

            if (mineField) {
                notificarObeservadores(CampoEvento.EXPLODIR);
                return true;
            }
            setOpen(true);
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

    public boolean isMineField() {
        return mineField;
    }

    public boolean isMarked() {
        return marked = true;
    }

    public boolean isOpen() {
        return open;
    }

    void setOpen(boolean open) {
        this.open = open;
        if (open) {
            notificarObeservadores(CampoEvento.ABRI);
        }
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

    boolean objetiveSucces() {
        boolean unraveled = !mineField && open;
        boolean protect = mineField && marked;
        return unraveled || protect;
    }

    public int mineOfNeighbor() {
        return (int) neighbors.stream().filter(v -> v.mineField).count();
    }

    public void restart() {
        open = false;
        mineField = false;
        marked = false;
        notificarObeservadores(CampoEvento.RESTART);
    }

 /*   public String toString() {
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
    }*/
}
