package hr.fer.elektrijada.dal.sql.stage;

/**
 * Created by Ivica Brebrek
 */
public class StageFromDb {
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

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof StageFromDb && ((StageFromDb) o).id == id) {
            return true;
        }
        return false;
    }
}
