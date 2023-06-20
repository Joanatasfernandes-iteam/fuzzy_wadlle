package dafault.observer;

public class Namorada implements ChegadaAniversarianteObserver {
    @Override
    public void chegou(EventChegadaAniversariante evento) {
        System.out.println("Avisar os convidados...");
        System.out.println("Apagar as luzes...");
        System.out.println("Espera um pouco...");
        System.out.println("e ...Surpresa!!......");
    }
}
