package hr.fer.elektrijada.dal.mock.news;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import hr.fer.elektrijada.model.news.NewsEntry;
import hr.fer.elektrijada.model.news.NewsRepository;

/**
 * Created by b on 10.11.2015..
 */
public class MockNewsRepository implements NewsRepository {
    @Override
    public boolean createNewsEntry(NewsEntry newsEntry) {
        return false;
    }

    @Override
    public List<NewsEntry> getNews() {
        List<NewsEntry> list = new ArrayList<>();
        //umjesto ovih add-ova u listu dodati vijesti iz baze
        list.add(
                new NewsEntry(
                        "Najstarija vijest ikad",
                        "oldy",
                        new Date(0),
                        7
                )
        );
        StringBuilder test = new StringBuilder();
        for (int i = 0; i < 100; i++) test.append(i + ". red\n");
        list.add(
                new NewsEntry(
                        "Vijest stara nekolko dana koja ima nevjerojatan dug naslov i tekst i ne znam hoće li se cijeli vidjeti, ko zna kolko reda ce prikazati, je li beskonacno????",
                        test.toString(),
                        new Date(Calendar.getInstance().getTime().getTime() - 600000000),
                        7
                )
        );
        for (int i = 0; i < 15; i++) {
            list.add(
                    new NewsEntry(
                            "Naslov br " + i,
                            "Randasdasdjfn asdf nasdflnasd \n asdfjnasa nasdf n\n sdf\n qffa \n sd\n\n aaaa",
                            new Date(Calendar.getInstance().getTime().getTime() - 350000 * i),
                            i
                    )
            );
        }
        //koristi konstruktor koji dodaje TRENUTNO vrijeme
        list.add(new NewsEntry(
                        "Isprobavam el sortira vrijeme",
                        "",
                        555
                )
        );

        //Boris: možda bi bilo bolje da smo umjesto liste išli na TreeSet, onda ne bi bilo u sučelju List<NewsEntry> nego Collection<NewsEntry>
        Collections.sort(list, new Comparator<NewsEntry>() {
            @Override
            public int compare(NewsEntry n1, NewsEntry n2) {
                return n2.getTimeOfCreation().compareTo(n1.getTimeOfCreation());
            }
        });

        return list;
    }

    @Override
    public NewsEntry getNews(int id) {
        return null;
    }

    @Override
    public int getNewsCount() {
        return getNews().size();
    }

    @Override
    public void close() {

    }
}
