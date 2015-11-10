package hr.fer.elektrijada.model.news;

import java.util.List;

/**
 * Created by Boris Milašinović on 10.11.2015..
 */
public interface NewsRepository {
    public boolean createNewsEntry(NewsEntry newsEntry);
    public List<NewsEntry> getNews();
    public int getNewsCount();
}
