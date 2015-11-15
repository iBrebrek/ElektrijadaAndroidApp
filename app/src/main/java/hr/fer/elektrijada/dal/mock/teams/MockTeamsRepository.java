package hr.fer.elektrijada.dal.mock.teams;


import java.util.ArrayList;
import java.util.List;

import hr.fer.elektrijada.model.teams.TeamsEntry;
import hr.fer.elektrijada.model.teams.TeamsRepository;

/**
 * Created by Zvone
 */
public class MockTeamsRepository implements TeamsRepository {

    private ArrayList <TeamsEntry> teamsEntryList = new ArrayList<>();

    @Override
    public boolean createTeamsEntry(TeamsEntry teamsEntry) {
        teamsEntryList.add(teamsEntry);
        return true;
    }

    @Override
    public List<TeamsEntry> getTeams() {
        return teamsEntryList;
    }

    @Override
    public TeamsEntry getEntry(int id) {
        for (TeamsEntry teamsEntry : teamsEntryList){
            if (teamsEntry.getId() == id){
                return teamsEntry;
            }
        }
        return null;
    }

    @Override
    public int getTeamsCount() {
        return teamsEntryList.size();
    }

    @Override
    public void close() {

    }
}
