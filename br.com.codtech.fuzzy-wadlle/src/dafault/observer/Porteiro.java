package dafault.observer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Porteiro {
    private List<ChegadaAniversarianteObserver> observers = new ArrayList<>();

    public void addObserver(ChegadaAniversarianteObserver observer) {
        observers.add(observer);
    }

    public void monitor() {
        System.out.println("O aniversariante Chegou?");
        Scanner entrada = new Scanner(System.in);
        String valor = "";
        while (!"sair".equalsIgnoreCase(valor)) {
            valor = entrada.nextLine();
            if ("sim".equalsIgnoreCase(valor)) {
                EventChegadaAniversariante evento = new EventChegadaAniversariante(new Date());
                observers.stream().forEach(observer -> observer.chegou(evento));
                valor = "sair";

            } else {
                System.out.println("Alarme falso");
            }
        }
        entrada.close();
    }
}
