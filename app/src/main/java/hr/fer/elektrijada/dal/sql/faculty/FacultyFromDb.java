package hr.fer.elektrijada.dal.sql.faculty;

/**
 * Created by Ivica Brebrek
 */
public class FacultyFromDb {
    private int id;
    private String name;
    private String nick;

    public FacultyFromDb(int id, String name, String nick) {
        if(name == null) {
            throw new IllegalArgumentException("Name can not be null");
        }
        this.id = id;
        this.name = name;
        this.nick = nick;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FacultyFromDb)) return false;
        FacultyFromDb that = (FacultyFromDb) o;
        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
