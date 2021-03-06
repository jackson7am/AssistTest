package app.testbuilder.br.com.TestBuilder.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDAO extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "vemepa2.db";
    public static final int DATABASE_VERSION = 1;

    /**
     * *************** TABELA_USUARIO ************************
     */
    public static final String CREATE_USUARIO = "CREATE TABLE usuario (id integer PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "avaliador VARCHAR(100) NOT NULL," +
            "cumpridor VARCHAR(100) NOT NULL," +
            "idade INTEGER NOT NULL," +
            "genero CHAR(1) NOT NULL," +
            "dt_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";

    public static final String CREATE_TESTE = "CREATE TABLE teste (id integer PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "usuario_id  integer NOT NULL," +
            "tipo        char(1) NOT NULL DEFAULT 1," +
            "status      char(1) NOT NULL DEFAULT 1);";

    public static final String CREATE_ASSIST = "CREATE TABLE assist (id integer PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "teste_id  integer NOT NULL," +
            "p1 varchar(10) DEFAULT 0000000000," +
            "p2 varchar(10) DEFAULT 0000000000," +
            "p3 varchar(10) DEFAULT 0000000000," +
            "p4 varchar(50) DEFAULT 0000000000," +
            "p5 varchar(10) DEFAULT 0000000000," +
            "p6 varchar(10) DEFAULT 0000000000," +
            "p7 varchar(10) DEFAULT 0000000000," +
            "p8 varchar(10) DEFAULT 0000000000," +
            "obs text);";

    public BaseDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USUARIO);
        db.execSQL(CREATE_TESTE);
        db.execSQL(CREATE_ASSIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USUARIO");
        db.execSQL("DROP TABLE IF EXISTS TESTE");
        db.execSQL("DROP TABLE IF EXISTS ASSIST");
        onCreate(db);
    }
}
