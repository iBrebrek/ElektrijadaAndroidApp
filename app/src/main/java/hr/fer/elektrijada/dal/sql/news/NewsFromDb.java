package hr.fer.elektrijada.dal.sql.news;

import java.io.Serializable;

import hr.fer.elektrijada.activities.bluetooth.IComparable;
import hr.fer.elektrijada.activities.bluetooth.IDetails;
import hr.fer.elektrijada.dal.sql.user.UserFromDb;

/**
 * Created by Ivica Brebrek
 */
public class NewsFromDb implements Serializable, IComparable<NewsFromDb>, IDetails {
    private static final long serialVersionUID = 1L;
    private int id;
    private String title;
    private String text;
    private String timeOfCreation;
    private UserFromDb author;

    public NewsFromDb(int id, String title, String text, String timeOfCreation, UserFromDb author) {
        if(title == null || author == null || timeOfCreation == null) {
            throw new IllegalArgumentException("Title, author or time of creation can not be null.");
        }
        this.id = id;
        this.title = title;
        this.text = text;
        this.timeOfCreation = timeOfCreation;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(String timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    public UserFromDb getAuthor() {
        return author;
    }

    public void setAuthor(UserFromDb author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewsFromDb)) return false;

        NewsFromDb that = (NewsFromDb) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return text != null ? text.equals(that.text) : that.text == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        return result;
    }

    @Override
    public boolean detailsSame(NewsFromDb other) {
        return author.equals(other.author);
    }

    @Override
    public String info() {
        return "Vijest: " + title;
    }

    @Override
    public String details() {
        return "autor: "+author.toString();
    }
}
