package database.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "quiz.db";
    private static final int DATABASE_VERSION = 1;


    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Créer les tables  en exécutant les requêtes SQL
        db.execSQL("CREATE TABLE Utilisateurs (ID_Utilisateur INTEGER PRIMARY KEY, Nom TEXT);");
        db.execSQL("CREATE TABLE Categories (ID_Categorie INTEGER PRIMARY KEY, Nom_Categorie TEXT);");
        db.execSQL("CREATE TABLE Questions (ID_Question INTEGER PRIMARY KEY, ID_Categorie INTEGER, Texte_Question TEXT, Type_Reponse TEXT, FOREIGN KEY (ID_Categorie) REFERENCES Categories(ID_Categorie));");
        db.execSQL("CREATE TABLE Reponses (ID_Reponse INTEGER PRIMARY KEY, ID_Question INTEGER, Texte_Reponse TEXT, Est_Reponse_Exacte INTEGER, FOREIGN KEY (ID_Question) REFERENCES Questions(ID_Question));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(MyDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        // Supprimer les tables et recréer
        db.execSQL("DROP TABLE IF EXISTS Utilisateurs;");
        db.execSQL("DROP TABLE IF EXISTS Categories;");
        db.execSQL("DROP TABLE IF EXISTS Questions;");
        db.execSQL("DROP TABLE IF EXISTS Reponses;");
        onCreate(db);
    }


}
