package ua.a5.mybusinessplan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class NotesMenuActivity extends AppCompatActivity {

    Button btnNotesMenuCreateNote;
    Button btnNotesMenuSearchNotes;
    Button btnNotesMenuMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_menu);


        btnNotesMenuCreateNote = (Button) findViewById(R.id.btnNotesMenuCreateNote);
        btnNotesMenuCreateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesMenuActivity.this, CreateNoteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnNotesMenuSearchNotes = (Button) findViewById(R.id.btnNotesMenuSearchNotes);
        btnNotesMenuSearchNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesMenuActivity.this, SearchNotesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnNotesMenuMainMenu = (Button) findViewById(R.id.btnNotesMenuMainMenu);
        btnNotesMenuMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
