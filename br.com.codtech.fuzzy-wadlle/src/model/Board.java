package model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Board implements CampoObservador {
    private final int lines;
    private final int coluns;
    private final int mines;
    private final List<Field> campos = new ArrayList<>();
    private final List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();

    public int getLines() {
        return lines;
    }

    public int getColuns() {
        return coluns;
    }

    public Board(int lines, int coluns, int mines) {
        this.lines = lines;
        this.coluns = coluns;
        this.mines = mines;
        generatedBoard();
        associetedNeighbord();
        sortedMines();
    }

    public void paraCada(Consumer<Field> funcao) {
        campos.forEach(funcao);
    }

    public void registrarObeservador(Consumer<ResultadoEvento> observador) {
        observadores.add(observador);
    }

    private void notificarObeservadores(Boolean resultado) {
        observadores.stream().forEach(observer -> observer.accept(new ResultadoEvento(resultado)));
    }

    public void open(int line, int colun) {
        campos.stream().filter(field -> field.getLine() == line && field.getColun() == colun)
                .findFirst()
                .ifPresent(Field::open);

    }

    public void alternatMarked(int line, int colun) {
        campos.stream().filter(field -> field.getLine() == line && field.getColun() == colun)
                .findFirst()
                .ifPresent(Field::alternatMarked);
    }

    private void sortedMines() {
        long minesArmed = 0;
        Predicate<Field> undermined = Field::isMineField;

        do {
            int sorted = (int) (Math.random() * campos.size());
            campos.get(sorted).toMine();
            minesArmed = campos.stream().filter(undermined).count();
        } while (minesArmed < mines);
    }

    public boolean objetiveSucces() {
        return campos.stream().allMatch(f -> f.objetiveSucces());
    }

    public void restart() {
        campos.stream().forEach(f -> f.restart());
        sortedMines();
    }

    private void associetedNeighbord() {
        for (Field fieldOne : campos) {
            for (Field fieldTwo : campos) {
                fieldOne.addNeigbor(fieldTwo);
            }
        }
    }

    private void generatedBoard() {
        for (int line = 0; line < lines; line++) {
            for (int colun = 0; colun < coluns; colun++) {
                Field campo = new Field(line, colun);
                campo.resgistrarObservador(this);
                campos.add(campo);
            }
        }
    }

    @Override
    public void eventoOcorreu(Field campo, CampoEvento evento) {
        if (evento == CampoEvento.EXPLODIR) {
            mostraMinas();
//            System.out.println("Perdeu....:("); passsou para o observer
            notificarObeservadores(false);
        } else if (objetiveSucces()) {
//                System.out.println("Ganhou....:)");
            notificarObeservadores(true);
        }
    }

    private void mostraMinas() {
        campos.stream().filter(field -> field.isMineField())
                .forEach(field -> field.setOpen(true));
    }

    /*public String toString() {
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
    }*/
}
