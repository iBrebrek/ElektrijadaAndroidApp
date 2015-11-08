package hr.fer.elektrijada.vijesti;

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
public class News implements Serializable { //impementiramo Serializable da bi se objekt mogo slat izmedu Activity-a
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

    /**
     * konstruktor koji prima sva 3 podatka o vijesti,
     * ovaj konstuktor ce se obicno krostiti za vec postojece vijesti
     *
     * @param authorId       id korisnika koji je napisao vijest
     * @param text           tekst vijesti
     * @param timeOfCreation vrijeme kada je vijest napisana
     */
    public News(int authorId, String title, String text, Date timeOfCreation) {
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
    public News(int authorId, String title, String text) {
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

    /**
     * sprema podatke iz ovog objekta na uredaj
     *
     * @return true ako je spremanje uspijesno, false ako nije
     */
    protected boolean saveToInternalStorage() {
        //TODO: spremi objekt na uredaj
        return false;
    }

    /**
     * sprema podatke iz ovog obijekta u bazu podataka
     *
     * @return true ako je spremanje uspijesno, false ako nije
     */
    protected boolean saveToDatabase() {
        //TODO: spremi objekt news u bazu podataka
        return false;
    }

    /**
     * mijenja postojecu vijesti na uredaju s ovim objektom
     *
     * @return true ako je promijenio podatke, false ako nije (i false ako ne postoji vijest s tim id-em)
     */
    protected boolean editInInternalStorage() {
        //TODO: promijeni podatke postojece vijesti na uredaju
        return false;
    }

    /**
     * mijenja postojecu vijesti u bazi s ovim objektom
     *
     * @return true ako je promijenio podatke, false ako nije (false ako ne postoji vijest s tim id-em)
     */
    protected boolean editInDatabase() {
        //TODO: promijeni podatke postojece vijesti u bazi podataka
        return false;
    }

    /**
     * brise ovaj objekt iz uredaja
     *
     * @return true ako je izbrisao, inace false
     */
    protected boolean deleteFromInternlStorage() {
        //TODO: izbrisi ovaj objekt (s tim id-em) iz uredaja
        return false;
    }

    /**
     * brise ovaj objekt iz baze podataka
     *
     * @return true ako je izbrisao, inace false
     */
    protected boolean deleteFromDatabase() {
        //TODO: izbrisi ovaj objekt (s tim id-em) iz baze podataka
        return false;
    }


}
