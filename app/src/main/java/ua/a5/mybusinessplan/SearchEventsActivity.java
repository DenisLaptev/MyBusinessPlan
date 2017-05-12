package ua.a5.mybusinessplan;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import ua.a5.mybusinessplan.DAO.DBHelper;

public class SearchEventsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String LOG_TAG = "log";

    public static final String[] WORDS = {"today", "tomorrow", "day before"};

    //Spinner spinnerSelectByMonth;

    EditText etTypeMonth;

    Button btnSelectEventsByMonth;
    Button btnSelectAllEvents;

    TextView tvSearchEventsEvent;

    Button btnSearchEventsBack;
    Button btnDeleteTableEvents;


    //для работы с БД.
    DBHelper dbHelper;

    String eventMonth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_events);

        etTypeMonth = (EditText) findViewById(R.id.et_typemonth);
        //eventMonth = etTypeMonth.getText().toString();
/*

        spinnerSelectByMonth = (Spinner) findViewById(R.id.spinnerSelectByMonth);
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.Months, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSelectByMonth.setAdapter(adapter);
        spinnerSelectByMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] array = getResources().getStringArray(R.array.Months);
                eventMonth = array[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

*/

        btnSelectEventsByMonth = (Button) findViewById(R.id.btnSelectEventsByMonth);
        btnSelectEventsByMonth.setOnClickListener(this);

        btnSelectAllEvents = (Button) findViewById(R.id.btnSelectAllEvents);
        btnSelectAllEvents.setOnClickListener(this);


        tvSearchEventsEvent = (TextView) findViewById(R.id.event_tvSearchEventsEvent);


        btnSearchEventsBack = (Button) findViewById(R.id.btnSearchEventsBack);
        btnSearchEventsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchEventsActivity.this, EventsMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btnDeleteTableEvents = (Button) findViewById(R.id.btnDeleteTableEvents);
        btnDeleteTableEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SearchEventsActivity.this, R.style.MyAlertDialogStyle);
                builder.setTitle("Delete Events Table?");
                builder.setMessage("Do You Really Want To Delete Events Table?");

                //positive button.
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //в любом случае вернётся существующая, толькочто созданная или обновлённая БД.
                        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
                        int clearCount = sqLiteDatabase.delete(DBHelper.TABLE_EVENTS_NAME, null, null);
                        Log.d(LOG_TAG, "deleted rows count = " + clearCount);
                        dbHelper.close();
                    }
                });

                //negative button.
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                });
                builder.show();

            }
        });


        /*
        tvSearchEventsEvent = (TextView) findViewById(R.id.event_tvSearchEventsEvent);
        tvSearchEventsEvent.setMovementMethod(LinkMovementMethod.getInstance());
        */

        /*
        btnSearchEventsBack = (Button)findViewById(R.id.btnSearchEventsBack);
        btnSearchEventsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        */
    }



/*

    //метод, для убирания клавиатуры EditText при нажатии на пустое пространство.
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
*/

    @Override
    public void onClick(View v) {
        SQLiteDatabase sqLiteDatabase;
        Cursor cursor;
        String orderBy;
        String strConsoleOutput = "";

        switch (v.getId()) {
            case R.id.btnSelectEventsByMonth: {
                eventMonth = etTypeMonth.getText().toString();
                if (eventMonth.length() == 1
                        && Integer.parseInt(eventMonth) <= 9
                        && Integer.parseInt(eventMonth) > 0
                        ) {
                    eventMonth = "0".concat(eventMonth);
                }

//////////////////---------------------->

                //для работы с БД.
                dbHelper = new DBHelper(SearchEventsActivity.this);


                //класс SQLiteDatabase предназначен для управления БД SQLite.
                //если БД не существует, dbHelper вызовет метод onCreate(),
                //если версия БД изменилась, dbHelper вызовет метод onUpgrade().

                //в любом случае вернётся существующая, толькочто созданная или обновлённая БД.
                sqLiteDatabase = dbHelper.getWritableDatabase();

                //метод rawQuery() возвращает объект типа Cursor,
                //его можно рассматривать как набор строк с данными.

                cursor = sqLiteDatabase.rawQuery(
                        "SELECT * FROM "
                                + dbHelper.TABLE_EVENTS_NAME
                                + " WHERE beginmonth = ? ", new String[]{eventMonth});


                //метод cursor.moveToFirst() делает 1-ю запись в cursor активной
                //и проверяет, есть ли в cursor что-то.
                if (cursor.moveToFirst()) {
                    //получаем порядковые номера столбцов по их именам.
                    int idIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_ID);
                    int titleIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_TITLE);
                    int locationIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_LOCATION);
                    int importanceIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_IMPORTANCE);
                    int begindayIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_BEGIN_DAY);
                    int beginmonthIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_BEGIN_MONTH);
                    int beginhourIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_BEGIN_HOUR);
                    int beginminuteIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_BEGIN_MINUTE);
                    int enddayIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_END_DAY);
                    int endmonthIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_END_MONTH);
                    int endhourIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_END_HOUR);
                    int endminuteIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_END_MINUTE);
                    int descriptionIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_DESCRIPTION);


                    //с помощью метода .moveToNext() перебираем все строки в cursor-е.
                    do {
                        Log.d(
                                "log", "id " + cursor.getInt(idIndex)
                                        + " title " + cursor.getString(titleIndex)
                                        + " location " + cursor.getString(locationIndex)
                                        + " importance " + cursor.getString(importanceIndex)
                                        + " beginday " + cursor.getString(begindayIndex)
                                        + " beginmonth " + cursor.getString(beginmonthIndex)
                                        + " beginhour " + cursor.getString(beginhourIndex)
                                        + " beginminute " + cursor.getString(beginminuteIndex)
                                        + " endday " + cursor.getString(enddayIndex)
                                        + " endmonth " + cursor.getString(endmonthIndex)
                                        + " endhour " + cursor.getString(endhourIndex)
                                        + " endminute " + cursor.getString(endminuteIndex)
                                        + " description " + cursor.getString(descriptionIndex)
                        );

                        /*
                        strConsoleOutput += "Event{"
                                + "title = " + cursor.getString(titleIndex)
                                + ", location = " + cursor.getString(locationIndex)
                                + ", importance = " + cursor.getString(importanceIndex)
                                + ", beginday = " + cursor.getString(begindayIndex)
                                + ", beginmonth = " + cursor.getString(beginmonthIndex)
                                + ", beginhour = " + cursor.getString(beginhourIndex)
                                + ", beginminute = " + cursor.getString(beginminuteIndex)
                                + ", endday = " + cursor.getString(enddayIndex)
                                + ", endmonth = " + cursor.getString(endmonthIndex)
                                + ", endhour = " + cursor.getString(endhourIndex)
                                + ", endminute = " + cursor.getString(endminuteIndex)
                                + ", description = " + cursor.getString(descriptionIndex)
                                + "}" + "\n" + "\n";
                        */


                        strConsoleOutput +=
                                cursor.getString(titleIndex)
                                        + "\n"
                                        + "Location : "
                                        + cursor.getString(locationIndex)
                                        + "\n"
                                        + "Beginning : "
                                        + cursor.getString(begindayIndex)
                                        + "."
                                        + cursor.getString(beginmonthIndex)
                                        + " at "
                                        + cursor.getString(beginhourIndex)
                                        + ":"
                                        + cursor.getString(beginminuteIndex)
                                        + "\n"
                                        + "End : "
                                        + cursor.getString(enddayIndex)
                                        + "."
                                        + cursor.getString(endmonthIndex)
                                        + " at "
                                        + cursor.getString(endhourIndex)
                                        + ":"
                                        + cursor.getString(endminuteIndex)
                                        + "\n"
                                        + "Description : "
                                        + cursor.getString(descriptionIndex)
                                        + "\n" + "\n";

                    } while (cursor.moveToNext());

                } else {
                    Log.d(LOG_TAG, "0 rows");
                }
                System.out.println(strConsoleOutput);
                tvSearchEventsEvent.setText(strConsoleOutput);

                //в конце закрываем cursor. Освобождаем ресурс.
                cursor.close();

                //закрываем соединение с БД.
                dbHelper.close();

//////////////////---------------------->
                break;
            }


            case R.id.btnSelectAllEvents: {
                //////////////////---------------------->
                //считываем БД.


                //для работы с БД.
                dbHelper = new DBHelper(SearchEventsActivity.this);


                //класс SQLiteDatabase предназначен для управления БД SQLite.
                //если БД не существует, dbHelper вызовет метод onCreate(),
                //если версия БД изменилась, dbHelper вызовет метод onUpgrade().

                //в любом случае вернётся существующая, толькочто созданная или обновлённая БД.
                sqLiteDatabase = dbHelper.getWritableDatabase();

                //группировку и (сортировку-используем) не используем, поэтому используем везде null.
/*

//--сортировка данных по результату.
                orderBy = null;
                //сортировка по результату.
                Log.d(LOG_TAG, "--- Сортировка по категории ---");
                orderBy = dbHelper.TABLE_EVENTS_KEY_BEGIN_MONTH;
                //"result";
//--сортировка данных по категории.
*/

                //метод query() возвращает объект типа Cursor,
                //его можно рассматривать как набор строк с данными.
                //cursor = sqLiteDatabase.query(dbHelper.TABLE_EVENTS_NAME, null, null, null, null, null, orderBy);
                cursor = sqLiteDatabase.query(dbHelper.TABLE_EVENTS_NAME, null, null, null, null, null, null);

                System.out.println(cursor);


                strConsoleOutput = "";
                //метод cursor.moveToFirst() делает 1-ю запись в cursor активной
                //и проверяет, есть ли в cursor что-то.
                if (cursor.moveToFirst()) {
                    //получаем порядковые номера столбцов по их именам.
                    int idIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_ID);
                    int titleIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_TITLE);
                    int locationIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_LOCATION);
                    int importanceIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_IMPORTANCE);
                    int begindayIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_BEGIN_DAY);
                    int beginmonthIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_BEGIN_MONTH);
                    int beginhourIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_BEGIN_HOUR);
                    int beginminuteIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_BEGIN_MINUTE);
                    int enddayIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_END_DAY);
                    int endmonthIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_END_MONTH);
                    int endhourIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_END_HOUR);
                    int endminuteIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_END_MINUTE);
                    int descriptionIndex = cursor.getColumnIndex(dbHelper.TABLE_EVENTS_KEY_DESCRIPTION);


                    //с помощью метода .moveToNext() перебираем все строки в cursor-е.
                    do {
                        Log.d(
                                "log", "id " + cursor.getInt(idIndex)
                                        + " title " + cursor.getString(titleIndex)
                                        + " location " + cursor.getString(locationIndex)
                                        + " importance " + cursor.getString(importanceIndex)
                                        + " beginday " + cursor.getString(begindayIndex)
                                        + " beginmonth " + cursor.getString(beginmonthIndex)
                                        + " beginhour " + cursor.getString(beginhourIndex)
                                        + " beginminute " + cursor.getString(beginminuteIndex)
                                        + " endday " + cursor.getString(enddayIndex)
                                        + " endmonth " + cursor.getString(endmonthIndex)
                                        + " endhour " + cursor.getString(endhourIndex)
                                        + " endminute " + cursor.getString(endminuteIndex)
                                        + " description " + cursor.getString(descriptionIndex)
                        );
                        /*
                        strConsoleOutput += "Event{"
                                + "title = " + cursor.getString(titleIndex)
                                + ", location " + cursor.getString(locationIndex)
                                + ", importance " + cursor.getString(importanceIndex)
                                + ", beginday " + cursor.getString(begindayIndex)
                                + ", beginmonth " + cursor.getString(beginmonthIndex)
                                + ", beginhour " + cursor.getString(beginhourIndex)
                                + ", beginminute " + cursor.getString(beginminuteIndex)
                                + ", endday " + cursor.getString(enddayIndex)
                                + ", endmonth " + cursor.getString(endmonthIndex)
                                + ", endhour " + cursor.getString(endhourIndex)
                                + ", endminute " + cursor.getString(endminuteIndex)
                                + ", description " + cursor.getString(descriptionIndex)
                                + "}" + "\n" + "\n";
                        */

                        strConsoleOutput +=
                                cursor.getString(titleIndex)
                                        + "\n"
                                        + "Location : "
                                        + cursor.getString(locationIndex)
                                        + "\n"
                                        + "Beginning : "
                                        + cursor.getString(begindayIndex)
                                        + "."
                                        + cursor.getString(beginmonthIndex)
                                        + " at "
                                        + cursor.getString(beginhourIndex)
                                        + ":"
                                        + cursor.getString(beginminuteIndex)
                                        + "\n"
                                        + "End : "
                                        + cursor.getString(enddayIndex)
                                        + "."
                                        + cursor.getString(endmonthIndex)
                                        + " at "
                                        + cursor.getString(endhourIndex)
                                        + ":"
                                        + cursor.getString(endminuteIndex)
                                        + "\n"
                                        + "Description : "
                                        + cursor.getString(descriptionIndex)
                                        + "\n" + "\n";

                    } while (cursor.moveToNext());

                } else {
                    Log.d(LOG_TAG, "0 rows");
                }
                System.out.println(strConsoleOutput);
                tvSearchEventsEvent.setText(strConsoleOutput);
                //в конце закрываем cursor. Освобождаем ресурс.
                cursor.close();

                //закрываем соединение с БД.
                dbHelper.close();

//////////////////---------------------->
                break;
            }
        }
    }
}
