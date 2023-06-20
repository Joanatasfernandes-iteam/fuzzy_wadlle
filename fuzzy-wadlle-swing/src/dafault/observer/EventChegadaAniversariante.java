package dafault.observer;

import java.util.Date;

public class EventChegadaAniversariante {
    private final Date time;

    EventChegadaAniversariante(Date time) {
        this.time = time;
    }

    public Date getTime() {
        return time;
    }
}
