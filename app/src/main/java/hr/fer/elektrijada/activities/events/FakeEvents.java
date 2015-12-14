package hr.fer.elektrijada.activities.events;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import hr.fer.elektrijada.model.events.Event;
import hr.fer.elektrijada.model.events.KnowledgeEvent;
import hr.fer.elektrijada.model.events.SportEvent;

/**
 * Created by Ivica Brebrek
 */
public class FakeEvents {
    private static ArrayList<Event> list;
    private FakeEvents(){};
    private static void create() {
        list = new ArrayList<>();
        final long MILISEC_QUARTER_OF_DAY = 2160000;
        for (int i=0; i<20; i+=2) {
            list.add(
                    new SportEvent(
                            i,
                            i+". Košarkaška(Ž) utakmica",
                            new Date(Calendar.getInstance().getTime().getTime() - (i+1)*MILISEC_QUARTER_OF_DAY),
                            "Mali zap", "Veliki zap"

                    )
            );
        }
        for (int i=1; i<20; i+=2) {
            list.add(
                    new SportEvent(
                            i,
                            i+". Nogometna(M) utakmica",
                            new Date(Calendar.getInstance().getTime().getTime() - (i+1)*MILISEC_QUARTER_OF_DAY),
                            new Date(Calendar.getInstance().getTime().getTime() - (i)*MILISEC_QUARTER_OF_DAY),
                            "Manchester United",  0,
                            "Real Madrid", 0
                    )
            );
        }
        for (int i=0; i<20; i+=2) {
            list.add(
                    new KnowledgeEvent(
                            i,
                            i+". Teorija",
                            new Date(Calendar.getInstance().getTime().getTime() - (i+1)*MILISEC_QUARTER_OF_DAY),
                            new Date(Calendar.getInstance().getTime().getTime() - (i)*MILISEC_QUARTER_OF_DAY),
                            true
                    )
            );
        }
        for (int i=1; i<10; i+=2) {
            list.add(
                    new KnowledgeEvent(
                            i,
                            i+". Natjecanje u znanju\n123\n123",
                            new Date(Calendar.getInstance().getTime().getTime() - (i+1)*MILISEC_QUARTER_OF_DAY),
                            new Date(Calendar.getInstance().getTime().getTime() - (i)*MILISEC_QUARTER_OF_DAY),
                            false
                    )
            );
        }
    }
    public static ArrayList<Event> getAllTheEvents() {
        if(list == null) {
            create();
        }
        return list;
    }
}
