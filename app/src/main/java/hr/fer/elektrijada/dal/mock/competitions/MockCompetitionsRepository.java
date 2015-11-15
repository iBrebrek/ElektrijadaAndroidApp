package hr.fer.elektrijada.dal.mock.competitions;

import java.util.ArrayList;
import java.util.List;

import hr.fer.elektrijada.model.competitions.CompetitionsEntry;
import hr.fer.elektrijada.model.competitions.CompetitionsRepository;

/**
 * Created by Zvone
 */
public class MockCompetitionsRepository implements CompetitionsRepository {

    private ArrayList<CompetitionsEntry> competitionsEntryList = new ArrayList<>();

    @Override
    public boolean createCompetitionsEntry(CompetitionsEntry competitionsEntry) {
        competitionsEntryList.add(competitionsEntry);
        return true;
    }

    @Override
    public List<CompetitionsEntry> getCompetitions() {
        return competitionsEntryList;
    }

    @Override
    public CompetitionsEntry getEntry(int id) {
        for (CompetitionsEntry competitionsEntry : competitionsEntryList){
            if (competitionsEntry.getId() == id){
                return competitionsEntry;
            }
        }
        return null;
    }

    @Override
    public int getTeamsCount() {
        return competitionsEntryList.size();
    }

    @Override
    public void close() {

    }
}
