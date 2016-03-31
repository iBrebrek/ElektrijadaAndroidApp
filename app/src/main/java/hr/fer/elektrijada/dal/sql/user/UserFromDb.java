package hr.fer.elektrijada.dal.sql.user;

/**
 * Created by Ivica Brebrek
 */
public class UserFromDb {
    private int id;
    private String name;
    private String sureName;

    public UserFromDb(int id, String name, String sureName) {
        if(name == null || sureName == null) {
            throw new IllegalArgumentException("Name and sure name can not be null.");
        }
        this.id = id;
        this.name = name;
        this.sureName = sureName;
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
    public boolean equals(Object o) { //TODO: uredi kad ce bit sinkronizacija
        if (this == o) return true;
        if (!(o instanceof UserFromDb)) return false;

        UserFromDb that = (UserFromDb) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
