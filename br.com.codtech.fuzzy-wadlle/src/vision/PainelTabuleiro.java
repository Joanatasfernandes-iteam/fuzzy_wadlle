package vision;

import model.Board;

import javax.swing.*;
import java.awt.*;

public class PainelTabuleiro extends JPanel {
    public PainelTabuleiro(Board tabuleiro) {
        setLayout(new GridLayout(tabuleiro.getLines(), tabuleiro.getColuns()));
       /* int total = tabuleiro.getLines() * tabuleiro.getColuns();
        for (int i = 0; i < total; i++) {
            add(new ButaoCampo(null));
        }*/

        tabuleiro.paraCada(campo -> add(new ButaoCampo(campo)));

        tabuleiro.registrarObeservador(evento -> {

            SwingUtilities.invokeLater(() -> {
                if (evento.isGanhou()) {
                    JOptionPane.showMessageDialog(this, "GANHOU :)!!!!");
                } else {
                    JOptionPane.showMessageDialog(this, "PERDEU :(!!!!");

                }
                tabuleiro.restart();
            });

        });
    }
}
