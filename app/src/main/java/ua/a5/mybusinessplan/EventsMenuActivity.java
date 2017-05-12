package ua.a5.mybusinessplan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EventsMenuActivity extends AppCompatActivity {

    Button btnEventsMenuCreateEvent;
    Button btnEventsMenuSearchEvents;
    Button btnEventsMenuStartMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_menu);


        btnEventsMenuCreateEvent = (Button) findViewById(R.id.btnEventsMenuCreateEvent);
        btnEventsMenuCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventsMenuActivity.this, CreateEventActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnEventsMenuSearchEvents = (Button) findViewById(R.id.btnEventsMenuSearchEvents);
        btnEventsMenuSearchEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventsMenuActivity.this, SearchEventsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnEventsMenuStartMenu = (Button) findViewById(R.id.btnEventsMenuStartMenu);
        btnEventsMenuStartMenu.setOnClickListener(new View.OnClickListener() {
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
