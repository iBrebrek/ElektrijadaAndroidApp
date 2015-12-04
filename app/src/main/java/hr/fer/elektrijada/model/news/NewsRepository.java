package hr.fer.elektrijada.model.news;

import java.util.Collection;

/**
 * Created by Boris Milašinović on 10.11.2015..
 */
public interface NewsRepository {
    public boolean createNewsEntry(NewsEntry newsEntry);
    public Collection<NewsEntry> getNews();
    public NewsEntry getNews(int id);
    public int getNewsCount();

    void close();
}
