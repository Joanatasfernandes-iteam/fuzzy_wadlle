package vision;

import model.Board;

import javax.swing.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {

        Board tabuleiro = new Board(16, 30, 50);
//        PainelTabuleiro painelTabuleiro = new PainelTabuleiro(tabuleiro);

        add(new PainelTabuleiro(tabuleiro));

        setTitle("Campo Minado");
        setSize(690, 438);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaPrincipal();
    }
}
