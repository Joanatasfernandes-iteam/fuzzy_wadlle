package dafault.observer;

public class FestaSurpresa {
    public static void main(String[] args) {
        Namorada namorada = new Namorada();
        Porteiro porteiro = new Porteiro();
        porteiro.addObserver(namorada);

        porteiro.addObserver(evento -> {
            System.out.println("Supresa via lambda!");
            System.out.println("Hora do evento:" + evento.getTime());
        });
        porteiro.monitor();
    }

}
