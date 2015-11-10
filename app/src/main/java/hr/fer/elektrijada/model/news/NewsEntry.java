package hr.fer.elektrijada.model.news;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * ovaj razred sluzi za JEDNU vijesti;
 * vijest se moze dobiti metodom getText, a mijenjati sa setText;
 * authorId je ID korisnika koji je napisao vijest, a timeOfCreation je vrijeme nastana vijesti,
 * oboje authorId i timeOfCreation se mogu SAMO procitati sa getAuthorId, tj. sa getTimeOfCreation;
 * postoje 2 konstruktora, jedan prima sva 3 navedena argumenta,
 * dok drugi ne prima vrijeme nego sam dodaje trenutno vrijeme (nisam siguran treba li paziti na vremensku zonu);
 * metoda getTimeToString ovisno o starosti vijesti vraca format "Objavljeno: prije 3 dana" ili "Objavljeno: 25.10.2015."
 * <p/>
 * Created by Ivica Brebrek
 */
public class NewsEntry implements Serializable { //impementiramo Serializable da bi se objekt mogo slat izmedu Activity-a
    /**
     * id vijesti
     */
    private int id;
    /**
     * naslov clanka/vijesti
     **/
    private String title;
    /**
     * id osobe koja je napisala clanak/vijest
     **/
    private int authorId;
    /**
     * tekst clanka/vijesti
     **/
    private String text;
    /**
     * vrijeme nastana clanka/vijesti
     **/
    private Date timeOfCreation;


    //TODO: prilagoditi potrebne konstruktore
    /**
     * DODAN NOVI KONSTUKTOR ZBOG ID-a
     * @param id
     * @param title
     * @param authorId
     * @param text
     * @param timeOfCreation
     */
    public NewsEntry(int authorId, String title, String text, Date timeOfCreation, int id) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.text = text;
        this.timeOfCreation = timeOfCreation;
    }
    public NewsEntry(int id, String title, String text, int authorId) {
        this(authorId, title, text, Calendar.getInstance().getTime(), id);
    }

    /**
     * konstruktor koji prima sva 3 podatka o vijesti,
     * ovaj konstuktor ce se obicno krostiti za vec postojece vijesti
     *
     * @param authorId       id korisnika koji je napisao vijest
     * @param text           tekst vijesti
     * @param timeOfCreation vrijeme kada je vijest napisana
     */
    public NewsEntry(int authorId, String title, String text, Date timeOfCreation) {
        this.title = title;
        this.authorId = authorId;
        this.text = text;
        this.timeOfCreation = timeOfCreation;
    }

    /**
     * konstruktor koji dodaje trenutno vrijeme,
     * ovaj konstruktor ce se koristiti za izradu novih vijesti
     *
     * @param authorId id korisnika koji je napisao vijest
     * @param text     tekst vijesti
     */
    public NewsEntry(int authorId, String title, String text) {
        this(authorId, title, text, Calendar.getInstance().getTime());
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimeOfCreation() {
        return timeOfCreation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    //TODO: dodaj:  public String getAuthor(); treba nam popis userId-a

    /**
     * vraca string "Objavljeno: prije [br_sati]sati i [br_minuta]minuta" ako isti dan;
     * "Objavljeno: prije [br_dana]dana" ako unutar istog tjedna;
     * ili vraca datum kada se dogodilo ako starije od jednog tjedna "Objavljeno: [timeOfCreation]"
     */
    public String getTimeToString() {
        final long MINUTES_PER_WEEK = 10080;
        final long MINUTES_PER_DAY = 1440;
        final long MINUTES_PER_HOUR = 60;
        int tempPassedTime;
        long minutes = ((Calendar.getInstance().getTime().getTime() - timeOfCreation.getTime()) / 60000);
        String passedTime;

        /*
        odredivanje zapisa datuma, tj. starost vijesti
         */
        if (MINUTES_PER_WEEK < minutes) { //starije od tjedan dana, datum nastanka
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.", Locale.getDefault());
            passedTime = dateFormat.format(timeOfCreation);
        } else {
            passedTime = "prije "; //zajednicko za sve vijesti novije od tjedan dana
            if (MINUTES_PER_DAY < minutes) { //izmedu 1 i 7 dana, broj dana od nastanka
                tempPassedTime = 0;
                while (MINUTES_PER_DAY < minutes) {
                    tempPassedTime++;
                    minutes -= MINUTES_PER_DAY;
                }
                passedTime += tempPassedTime + " dana";

            } else {
                if (MINUTES_PER_HOUR < minutes) { //izmedu 1 i 24 sata, broj sati i minuta od nastanka
                    tempPassedTime = 0;
                    while (MINUTES_PER_HOUR < minutes) {
                        tempPassedTime++;
                        minutes -= MINUTES_PER_HOUR;
                    }
                    passedTime += tempPassedTime + " h";
                    if (minutes > 0) { //da ne bi pisalo npr prije 5 sati i 0 minuta
                        passedTime += " i " + minutes + " min";
                    }
                } else { //inace je manje od sat vremena, broj minuta
                    passedTime += minutes + " min";
                }
            }
        }
        return passedTime;
    }
}
