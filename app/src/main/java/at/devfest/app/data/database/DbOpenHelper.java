package at.devfest.app.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import at.devfest.app.data.database.model.SelectedSession;
import at.devfest.app.data.database.model.Session;
import at.devfest.app.data.database.model.Speaker;

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String NAME = "devfest.db";
    private static final int VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createSpeakersTable(db);
        createSessionsTable(db);
        createSelectedSessionsTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void createSpeakersTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Speaker.TABLE + " (" +
                Speaker.ID + " INTEGER PRIMARY KEY," +
                Speaker.NAME + " VARCHAR," +
                Speaker.TITLE + " VARCHAR," +
                Speaker.BIO + " VARCHAR," +
                Speaker.WEBSITE + " VARCHAR," +
                Speaker.TWITTER + " VARCHAR," +
                Speaker.GITHUB + " VARCHAR," +
                Speaker.GPLUS + " VARCHAR," +
                Speaker.XING + " VARCHAR," +
                Speaker.LINKEDIN + " VARCHAR," +
                Speaker.PHOTO + " VARCHAR);");
    }

    private void createSessionsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Session.TABLE + " (" +
                Session.ID + " INTEGER PRIMARY KEY," +
                Session.START_AT + " VARCHAR," +
                Session.DURATION + " INTEGER," +
                Session.ROOM_ID + " INTEGER," +
                Session.SPEAKERS_IDS + " VARCHAR," +
                Session.TITLE + " VARCHAR," +
                Session.DESCRIPTION + " VARCHAR," +
                Session.PHOTO + " VARCHAR);");
    }

    private void createSelectedSessionsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + SelectedSession.TABLE + " (" +
                SelectedSession.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SelectedSession.SLOT_TIME + " VARCHAR," +
                SelectedSession.SESSION_ID + " INTEGER);");
    }
}
