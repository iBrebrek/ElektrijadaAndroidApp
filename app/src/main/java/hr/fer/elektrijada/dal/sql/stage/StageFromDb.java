package hr.fer.elektrijada.dal.sql.stage;

import java.io.Serializable;

import hr.fer.elektrijada.activities.bluetooth.IComparable;

/**
 * Created by Ivica Brebrek
 */
public class StageFromDb implements Serializable, IComparable<StageFromDb> {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;

    public StageFromDb(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof StageFromDb) {
            StageFromDb other = (StageFromDb) o;
            if(name == null) {
                if(other.name != null) return false;
            } else if(!name.equals(other.name)) return false;
            return true;
        }
        return false;
    }

    @Override
    public boolean detailsSame(StageFromDb other) {
        return true; //nema se kaj gledat
    }
}
