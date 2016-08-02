package thontepu.naveen.noteapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mac on 8/2/16.
 */
public class SessionSharedPrefs {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    private final String PREF_FILENAME = "thontepu.naveen.noteapp";
    public final String NOTES="Notes";
    public final String NOTE_ID = "noteId";

    public SessionSharedPrefs(Context context){
        this._context = context;
        pref=_context.getSharedPreferences(PREF_FILENAME,Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.apply();
    }

    public void setNotes(String Notes){
        editor.putString( NOTES, Notes);
        editor.commit();
    }

    public String getNotes(){
        return pref.getString(NOTES,"");
    }

    public void setNoteId(String NoteId){
        editor.putString(NOTE_ID , NoteId);
        editor.commit();
    }

    public String getNoteId(){
        return pref.getString(NOTE_ID,"1");
    }


}
