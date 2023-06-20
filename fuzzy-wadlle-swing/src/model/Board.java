package model;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board  implements FieldListener{
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
        } catch (Exception e) {
            //TODO Ajusta a implementação do metodo abri.
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
                Field field = new Field(line, colun);
                field.registerListeners(this);
                fields.add(new Field(line, colun));
            }
        }
    }

    @Override
    public void eventListner(Field field, FieldEvent event) {
        if (event == FieldEvent.TO_EXPLOSE){
            System.out.println("You lose!!! :(");
        }else if (objetiveSucces()){
            System.out.println("You Win!!! :)");
        }
    }
}
