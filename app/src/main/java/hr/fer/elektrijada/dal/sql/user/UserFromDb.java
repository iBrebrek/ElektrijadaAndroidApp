package hr.fer.elektrijada.dal.sql.user;

/**
 * Created by Ivica Brebrek
 */
public class UserFromDb {
    private int id;
    private String name;
    private String sureName;
    private String uniqueId;

    public UserFromDb(int id, String name, String sureName, String uniqueId) {
        if(name == null || sureName == null) {
            throw new IllegalArgumentException("Name and sure name can not be null.");
        }
        this.id = id;
        this.name = name;
        this.sureName = sureName;
        this.uniqueId = uniqueId;
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

    public String getSureName() {
        return sureName;
    }

    public void setSureName(String sureName) {
        this.sureName = sureName;
    }

    @Override
    public String toString() {
        return name + " " + sureName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserFromDb)) return false;

        UserFromDb that = (UserFromDb) o;

        return uniqueId.equals(that.uniqueId);

    }

    @Override
    public int hashCode() {
        return uniqueId.hashCode();
    }
}
