package hr.fer.elektrijada.dal.sql.user;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import hr.fer.elektrijada.dal.sql.DbHelper;

/**
 * Created by Ivica Brebrek
 */
public class SqlUserRepository {
    private SQLiteOpenHelper dbHelper;
    private final Context context;

    public SqlUserRepository(Context context) {
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    //TODO: popravi kad ce se raditi korisnici
    public UserFromDb getUser(int id) {
        return new UserFromDb(1, "Test", "Testić");
    }
}
