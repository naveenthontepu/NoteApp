package thontepu.naveen.noteapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import thontepu.naveen.noteapp.Utils.Constants;
import thontepu.naveen.noteapp.RecyclerViewFiles.ItemClickInterface;
import thontepu.naveen.noteapp.RecyclerViewFiles.NoteAdapter;
import thontepu.naveen.noteapp.DBfiles.NoteDB;
import thontepu.naveen.noteapp.DBfiles.NotePojo;
import thontepu.naveen.noteapp.R;
import thontepu.naveen.noteapp.Utils.SessionSharedPrefs;
import thontepu.naveen.noteapp.Utils.Utilities;

public class NoteListActivity extends AppCompatActivity implements ItemClickInterface {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.addNewNote)
    TextView addNewNote;
    @Bind(R.id.noteItems)
    RecyclerView noteItems;

    List<NotePojo> notePojos;
    NoteAdapter noteAdapter;
    Utilities utilities;
    SessionSharedPrefs sessionSharedPrefs;
    NoteDB noteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(toolbar.getWindowToken(),0);
        initialize();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete_all){
            noteDB.deletingDatabase();
            initialize();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initialize() {
        utilities = new Utilities();
        sessionSharedPrefs = new SessionSharedPrefs(this);
        noteDB = new NoteDB(this,null,null,0);
        notePojos = noteDB.getAllNotes();
        if (notePojos.size()<=0){
            addNewNote.setVisibility(View.VISIBLE);
            noteItems.setVisibility(View.GONE);
        }else {
            addNewNote.setVisibility(View.GONE);
            noteItems.setVisibility(View.VISIBLE);
        }
        noteAdapter = new NoteAdapter(this,notePojos,noteItems);
        noteAdapter.setItemClickInterface(this);
        noteItems.setLayoutManager(new LinearLayoutManager(this));
        noteItems.setAdapter(noteAdapter);
    }
    @OnClick(R.id.fab)
    public void onClick() {
        Intent noteIntent = new Intent(this, NoteDetailsActivity.class);
        utilities.printLog("the id being sent = "+sessionSharedPrefs.getNoteId());
        noteIntent.putExtra(Constants.CallBackConstants.NEW_NOTE, true);
        noteIntent.putExtra(Constants.CallBackConstants.NOTE_ID,sessionSharedPrefs.getNoteId());
        startActivity(noteIntent);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        utilities.printLog("the on click listener");
        Intent intent = new Intent(this,NoteDetailsActivity.class);
        intent.putExtra(Constants.CallBackConstants.NEW_NOTE,false);
        intent.putExtra(Constants.CallBackConstants.NOTE_POJO,notePojos.get(position));
        intent.putExtra(Constants.CallBackConstants.NOTE_ID,notePojos.get(position).getId());
        startActivity(intent);
    }
}
