package thontepu.naveen.noteapp.DBfiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import thontepu.naveen.noteapp.Utils.SessionSharedPrefs;
import thontepu.naveen.noteapp.Utils.Utilities;

/**
 * Created by mac on 8/2/16.
 */
public class NoteDB extends SQLiteOpenHelper {

    private static int DATABASE_VERSION=1;
    private static final String DATABASE_NAME = "Notes.db";
    private static final String TABLE_NOTE = "Notes";
    private static final String COLUMN_TITLE = "Title";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_MESSAGE = "message";
    SessionSharedPrefs sessionSharedPrefs;
    Utilities utilities;


    public NoteDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,DATABASE_NAME, factory,DATABASE_VERSION);
        utilities = new Utilities();
        sessionSharedPrefs = new SessionSharedPrefs(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ TABLE_NOTE +"( "+
                COLUMN_ID + " TEXT PRIMARY KEY, " +
                COLUMN_TITLE+" TEXT, "+
                COLUMN_MESSAGE+" TEXT );";
        db.execSQL(query);
    }

    public void addNote(NotePojo notePojo){
        ContentValues v= getContentValue(notePojo);
        utilities.printLog("the note id = "+notePojo.getId());
        utilities.printLog("the id before = "+sessionSharedPrefs.getNoteId());
        sessionSharedPrefs.setNoteId(""+(Integer.parseInt(sessionSharedPrefs.getNoteId())+1));
        utilities.printLog("the id = "+sessionSharedPrefs.getNoteId());
        SQLiteDatabase db =getReadableDatabase();
        db.insert(TABLE_NOTE,null,v);
        db.close();
        utilities.printLog("Addition Done");
    }
    private ContentValues getContentValue(NotePojo notePojo){
        ContentValues v= new ContentValues();
        v.put(COLUMN_ID,notePojo.getId());
        v.put(COLUMN_TITLE,notePojo.getTitle());
        v.put(COLUMN_MESSAGE,notePojo.getMessage());
        return v;
    }
    public void updateNote(NotePojo notePojo){
        ContentValues v = getContentValue(notePojo);
        SQLiteDatabase db = getReadableDatabase();
        db.update(TABLE_NOTE,v,COLUMN_ID+"="+notePojo.getId(),null);
    }

    public void deleteNote(NotePojo notePojo){
        ContentValues v = getContentValue(notePojo);
        SQLiteDatabase db = getReadableDatabase();
        db.delete(TABLE_NOTE,COLUMN_ID+"="+notePojo.getId(),null);
    }

    public void deletingDatabase(){
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE + ";");
        onCreate(db);
    }

    public List<NotePojo> getAllNotes(){
        SQLiteDatabase db = getReadableDatabase();
        List<NotePojo> pojoList = new ArrayList<>();
        NotePojo temp;

        String query = "SELECT * FROM "+ TABLE_NOTE +" WHERE 1";

        Cursor c = db.rawQuery(query,null);
        c.moveToLast();
        while (!c.isBeforeFirst()){
            temp = new NotePojo(c.getString(c.getColumnIndex(COLUMN_MESSAGE)),
                    c.getString(c.getColumnIndex(COLUMN_ID)),
                    c.getString(c.getColumnIndex(COLUMN_TITLE)));
            pojoList.add(temp);
            c.moveToPrevious();
        }
        utilities.printLog("the size = "+pojoList.size());
        if (pojoList.size()!=0){
            return pojoList;
        }
        return Collections.emptyList();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS "+ TABLE_NOTE);
        DATABASE_VERSION = newVersion;
        onCreate(db);
    }
}
