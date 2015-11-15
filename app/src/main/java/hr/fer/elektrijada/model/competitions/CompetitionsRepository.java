package hr.fer.elektrijada.model.competitions;

import java.util.List;

/**
 * Created by Zvone
 */
public interface CompetitionsRepository {
    public boolean createCompetitionsEntry (CompetitionsEntry competitionsEntry);
    public List<CompetitionsEntry> getCompetitions();
    public CompetitionsEntry getEntry (int id);
    public int getTeamsCount();

    void close();
}
