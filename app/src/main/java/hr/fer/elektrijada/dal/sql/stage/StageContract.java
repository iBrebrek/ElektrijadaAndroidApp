package hr.fer.elektrijada.dal.sql.stage;

import android.provider.BaseColumns;

/**
 * Razred koji nudi osnovni statički opis tablice Stage.
 */
public class StageContract {

    /** Konstruktor je sakriven. */
    private StageContract(){}

    /** Konstanta koja označuje da je tip atributa tekst. */
    private static final String TEXT_TYPE = " TEXT";

    /** SQL za stvaranje i brisanje tablice Stage */
    public static final String SQL_CREATE_STAGE =
            "CREATE TABLE " + StageEntry.TABLE_NAME + " (" +
                    StageEntry._ID + " INTEGER PRIMARY KEY," +
                    StageEntry.COLUMN_NAME_NAME + TEXT_TYPE +
                    " )";

    /** SQL za brisanje tablice Stage */
    public static final String SQL_DELETE_STAGE =
            "DROP TABLE IF EXISTS " + StageEntry.TABLE_NAME;

    /**
     * Vraća index stupca u kojemu se nalazi dani atribut.
     * Indexi počinju od 0.
     *
     * @param columnName    atribut
     * @return index stupca u kojemu je {@code columnName}
     *
     * @exception RuntimeException
     *        ako {@code columnName} nije atribut u ovome entitetu
     */
    public static  int getColumnPos(String columnName){
        switch(columnName){
            case StageEntry._ID:
                return 0;
            case StageEntry.COLUMN_NAME_NAME:
                return 1;
            default:
                throw new RuntimeException(String.format(
                    "Invalid column:%s for table %s",
                    columnName, StageEntry.TABLE_NAME));
        }
    }

    /**
     * Statički ugniježđeni razred koji sadrži ime entieta
     * {@link #TABLE_NAME} i atribute toga entiteta
     */
    public static abstract class StageEntry implements BaseColumns {
        public static final String TABLE_NAME = "stage";
        public static final String COLUMN_NAME_NAME = "name";

    }
}
