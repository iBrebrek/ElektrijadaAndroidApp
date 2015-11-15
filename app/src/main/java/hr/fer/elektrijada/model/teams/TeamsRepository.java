package hr.fer.elektrijada.model.teams;

import java.util.Collection;
import java.util.List;

/**
 * Created by Zvone on 14.11.2015..
 */
public interface TeamsRepository {
    public boolean createTeamsEntry (TeamsEntry teamsEntry);
    public List <TeamsEntry> getTeams();
    public TeamsEntry getEntry (int id);
    public int getTeamsCount();

    void close();
}
