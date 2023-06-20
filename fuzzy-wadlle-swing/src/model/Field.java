package model;


import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

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
    private List<FieldListener> eventList = new ArrayList<>();
    //private List<BiConsumer<Field, FieldEvent>> consumerList = new ArrayList<>();

    public void registerListeners(FieldListener event){
        eventList.add(event);
    }

    private void notify(FieldEvent event){
        eventList.stream().forEach(listener -> listener.eventListner(this, event ));
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
            if (marked){
                notify(FieldEvent.MARKED);
            }else {
                notify(FieldEvent.MARK_OFF);
            }
        }
    }

    public void toMine() {
        mineField = true;
    }

    public boolean open() {
        if (!open && !marked) {
            open = true;
            if (mineField) {
                notify(FieldEvent.TO_EXPLOSE);
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
        return marked;
    }

    public boolean isOpen() {
        return open;
    }

    void setOpen(boolean open) {
        this.open = open;
        if (open){
            notify(FieldEvent.OPEN);
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

    public long mineOfNeighbor() {
        return neighbors.stream().filter(v -> v.mineField).count();
    }

    public void restart() {
        open = false;
        mineField = false;
        marked = false;
    }

}
