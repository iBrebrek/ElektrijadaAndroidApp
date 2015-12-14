package hr.fer.elektrijada.model.events;

import java.util.Date;

/**
 * Created by Ivica Brebrek
 */
public class KnowledgeEvent extends Event {
    private boolean hasResults;

    /**
     *
     * @param id            id NATJECANJA
     * @param name          ime KATEGORIJE, npr., Informatika
     * @param timeFrom      vrijeme pocetka, ne smije biti null
     * @param timeTo        vrijeme kraja
     * @param hasResults    true ako ima tablice bodove, inace false; ova varijabla oznacava treba li omoguciti link iz prikaza dogadaja na prikaz rezultata
     */
    public KnowledgeEvent(int id, String name, Date timeFrom, Date timeTo, boolean hasResults) {
        super(id, name, timeFrom, timeTo);
        this.hasResults = hasResults;
    }

    /**
     *  bez kraja
     */
    public KnowledgeEvent(int id, String name, Date start, boolean hasResults) {
        super(id, name, start);
        this.hasResults = hasResults;
    }



    public boolean hasResults() {
        return hasResults;
    }
}
