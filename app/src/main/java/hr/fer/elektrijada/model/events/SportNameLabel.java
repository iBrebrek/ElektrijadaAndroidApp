package hr.fer.elektrijada.model.events;

import java.util.Date;

/**
 * bilo bi pametnije da sam se prije sijetio nekak drugacije ovo rijesti jer cudno je da ovo nasljedcuje Event
 *
 * Ovaj razred se koristi samo ako treba biti prikaz iskljuciov dogadaja koji su sport
 *
 * Created by Ivica Brebrek
 */
public class SportNameLabel extends Event {

    public SportNameLabel(String name) {
        super(-1, name, new Date(0));
    }
}
