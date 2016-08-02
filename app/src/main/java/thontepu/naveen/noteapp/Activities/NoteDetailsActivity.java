package thontepu.naveen.noteapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import thontepu.naveen.noteapp.Utils.Constants;
import thontepu.naveen.noteapp.DBfiles.NoteDB;
import thontepu.naveen.noteapp.DBfiles.NotePojo;
import thontepu.naveen.noteapp.R;
import thontepu.naveen.noteapp.Utils.Utilities;

public class NoteDetailsActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.titleText)
    TextView titleText;
    @Bind(R.id.titleEditText)
    EditText titleEditText;
    @Bind(R.id.descriptionText)
    TextView descriptionText;
    @Bind(R.id.descriptionEditText)
    EditText descriptionEditText;

    boolean newNote = true;
    NotePojo notePojo;
    Menu menu;
    MenuItem edit,delete,done;
    Utilities utilities;
    String id;
    NoteDB noteDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        utilities = new Utilities();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        if (intent.getExtras()!=null) {
            newNote = intent.getBooleanExtra(Constants.CallBackConstants.NEW_NOTE, true);
            id = intent.getStringExtra(Constants.CallBackConstants.NOTE_ID);
            if (!newNote) {
                notePojo = (NotePojo) intent.getSerializableExtra(Constants.CallBackConstants.NOTE_POJO);
            } else {
                notePojo = new NotePojo();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialize();
        setTextViews();
    }

    private void setTextViews() {
        if (!newNote && notePojo !=null){
            titleText.setText(notePojo.getTitle());
            descriptionText.setText(notePojo.getMessage());
        }
    }

    private void initialize() {
        utilities.printLog("new Note = "+newNote);
        noteDB = new NoteDB(this,null,null,0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_details_menu, menu);
        this.menu = menu;
        edit = menu.findItem(R.id.action_edit);
        delete= menu.findItem(R.id.action_delete);
        done = menu.findItem(R.id.action_done);
        setViews();
        return true;
    }

    private void setViews() {
        if (newNote){
            done.setVisible(true);
            edit.setVisible(false);
            delete.setVisible(false);
            titleEditText.setVisibility(View.VISIBLE);
            titleText.setVisibility(View.GONE);
            descriptionEditText.setVisibility(View.VISIBLE);
            descriptionText.setVisibility(View.GONE);
            getFocus(titleEditText);
        }else {
            done.setVisible(false);
            edit.setVisible(true);
            delete.setVisible(true);
            titleEditText.setVisibility(View.GONE);
            titleText.setVisibility(View.VISIBLE);
            descriptionEditText.setVisibility(View.GONE);
            descriptionText.setVisibility(View.VISIBLE);
            titleText.setText(notePojo.getTitle());
            descriptionText.setText(notePojo.getMessage());
        }
    }

    public void getFocus(EditText editText){
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_edit:
                editTheNote();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_delete:
                deleteTheNote();
                return true;
            case R.id.action_done:
                doneEditing();
            default:
                return false;
        }
    }

    private void doneEditing() {
        done.setVisible(false);
        edit.setVisible(true);
        delete.setVisible(true);
        changeText(titleText,titleEditText,View.VISIBLE,View.GONE);
        changeText(descriptionText,descriptionEditText,View.VISIBLE,View.GONE);
        if (notePojo!=null){
            notePojo.setMessage(descriptionText.getText().toString());
            notePojo.setTitle(titleText.getText().toString());
            if (newNote) {
                notePojo.setId(id);
                noteDB.addNote(notePojo);
                newNote = false;
            }else {
                noteDB.updateNote(notePojo);
            }
        }
    }

    private void changeText(TextView textView, EditText editTexView, int tvVis, int etVis) {
        if (tvVis == View.VISIBLE) {
            textView.setText(editTexView.getText());
        }else {
            editTexView.setText(textView.getText());
        }
        textView.setVisibility(tvVis);
        editTexView.setVisibility(etVis);
    }

    private void deleteTheNote() {
        noteDB.deleteNote(notePojo);
        Intent intent = new Intent(this,NoteListActivity.class);
        startActivity(intent);
    }

    private void editTheNote() {
        done.setVisible(true);
        edit.setVisible(false);
        titleEditText.requestFocus();
        changeText(titleText,titleEditText,View.GONE,View.VISIBLE);
        changeText(descriptionText,descriptionEditText,View.GONE,View.VISIBLE);
        getFocus(titleEditText);
    }


}
