package hr.fer.elektrijada.dal.sql.category;

/**
 * Created by Ivica Brebrek
 */
public class CategoryFromDb {
    private int id;
    private String name;
    private String nick;
    private boolean isSport;
    private boolean isDuel;

    public CategoryFromDb(int id, String name, String nick, boolean isSport, boolean isDuel) {
        this.id = id;
        this.name = name;
        this.nick = nick;
        this.isSport = isSport;
        this.isDuel = isDuel;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNick() {
        return nick;
    }

    public boolean isSport() {
        return isSport;
    }

    public boolean isDuel() {
        return isDuel;
    }

    @Override
    public String toString() {
        return name + (nick==null?"":" ("+nick+")");
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CategoryFromDb && ((CategoryFromDb) o).id == id) {
            return true;
        }
        return false;
    }
}
