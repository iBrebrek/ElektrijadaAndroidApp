package hr.fer.elektrijada.dal.sql.category;

import java.io.Serializable;

import hr.fer.elektrijada.activities.bluetooth.IComparable;
import hr.fer.elektrijada.activities.bluetooth.IDetails;

/**
 * Created by Ivica Brebrek
 */
public class CategoryFromDb implements Serializable, IComparable<CategoryFromDb>, IDetails {
    private static final long serialVersionUID = 1L;
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

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + (nick==null?"":" ("+nick+")");
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CategoryFromDb) {
            CategoryFromDb other = (CategoryFromDb) o;
            return name.equals(other.name);
        }
        return false;
    }

    @Override
    public boolean detailsSame(CategoryFromDb other) {
        if(nick == null) {
            if(other.nick != null) return false;
        } else if(!nick.equals(other.nick)) return false;
        return isSport == other.isSport && isDuel == other.isDuel;
    }

    @Override
    public String info() {
        return  name;
    }

    @Override
    public String details() {
        StringBuilder sb = new StringBuilder();
        sb.append("nick: ").append(nick).append("\n")
                .append("sport: ").append(isDuel).append("\n")
                .append("duel: ").append(isDuel).append("\n");
        return sb.toString();
    }
}
