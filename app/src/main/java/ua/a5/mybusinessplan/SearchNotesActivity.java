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
import android.widget.Spinner;
import android.widget.TextView;

import ua.a5.mybusinessplan.DAO.DBHelper;

public class SearchNotesActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String LOG_TAG = "log";

    Spinner spinnerSelectByCategory;

    Button btnSelectBy;
    Button btnSelectAll;

    TextView tvOpenNote;

    Button btnBack;
    Button btnDeleteTableNotes;


    //для работы с БД.
    DBHelper dbHelper;

    String noteCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_notes);

        spinnerSelectByCategory = (Spinner) findViewById(R.id.spinnerSelectByCategory);
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.Categories, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSelectByCategory.setAdapter(adapter);
        spinnerSelectByCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] array = getResources().getStringArray(R.array.Categories);
                noteCategory = array[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnSelectBy = (Button) findViewById(R.id.btnSelectNotesByCategory);
        btnSelectBy.setOnClickListener(this);

        btnSelectAll = (Button) findViewById(R.id.btnSelectAllNotes);
        btnSelectAll.setOnClickListener(this);


        tvOpenNote = (TextView) findViewById(R.id.tvOpenNote);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchNotesActivity.this, NotesMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btnDeleteTableNotes = (Button) findViewById(R.id.btnDeleteTableNotes);
        btnDeleteTableNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SearchNotesActivity.this, R.style.MyAlertDialogStyle);
                builder.setTitle("Delete Notes Table?");
                builder.setMessage("Do You Really Want To Delete Notes Table?");

                //positive button.
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //в любом случае вернётся существующая, толькочто созданная или обновлённая БД.
                        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
                        int clearCount = sqLiteDatabase.delete(dbHelper.TABLE_NOTES_NAME, null, null);
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
    }

/*

    //метод, для убирания клавиатуры EditText при нажатии на пустое пространство.
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
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
            case R.id.btnSelectNotesByCategory: {

//////////////////---------------------->

                //для работы с БД.
                dbHelper = new DBHelper(SearchNotesActivity.this);


                //класс SQLiteDatabase предназначен для управления БД SQLite.
                //если БД не существует, dbHelper вызовет метод onCreate(),
                //если версия БД изменилась, dbHelper вызовет метод onUpgrade().

                //в любом случае вернётся существующая, толькочто созданная или обновлённая БД.
                sqLiteDatabase = dbHelper.getWritableDatabase();

                //метод rawQuery() возвращает объект типа Cursor,
                //его можно рассматривать как набор строк с данными.

                cursor = sqLiteDatabase.rawQuery(
                        "SELECT * FROM "
                                + dbHelper.TABLE_NOTES_NAME
                                + " WHERE category = ? ", new String[]{noteCategory});


                //метод cursor.moveToFirst() делает 1-ю запись в cursor активной
                //и проверяет, есть ли в cursor что-то.
                if (cursor.moveToFirst()) {
                    //получаем порядковые номера столбцов по их именам.
                    int idIndex = cursor.getColumnIndex(dbHelper.TABLE_NOTES_KEY_ID);
                    int categoryIndex = cursor.getColumnIndex(dbHelper.TABLE_NOTES_KEY_CATEGORY);
                    int titleIndex = cursor.getColumnIndex(dbHelper.TABLE_NOTES_KEY_TITLE);
                    int importanceIndex = cursor.getColumnIndex(dbHelper.TABLE_NOTES_KEY_IMPORTANCE);
                    int notetextIndex = cursor.getColumnIndex(dbHelper.TABLE_NOTES_KEY_NOTETEXT);


                    //с помощью метода .moveToNext() перебираем все строки в cursor-е.
                    do {
                        Log.d(
                                "log", "id " + cursor.getInt(idIndex)
                                        + " category " + cursor.getString(categoryIndex)
                                        + " title " + cursor.getString(titleIndex)
                                        + " importance " + cursor.getString(importanceIndex)
                                        + " notetext " + cursor.getString(notetextIndex)
                        );

                        /*
                        strConsoleOutput += "Note{"
                                + "category = " + cursor.getString(categoryIndex)
                                + ", title = " + cursor.getString(titleIndex)
                                + ", importance = " + cursor.getString(importanceIndex)
                                + ", notetext = " + cursor.getString(notetextIndex)
                                + "}" + "\n" + "\n";
                        */

                        strConsoleOutput +=
                                "Category : "
                                        + cursor.getString(categoryIndex)
                                        + "\n"
                                        + "Title : "
                                        + cursor.getString(titleIndex)
                                        + "\n"
                                        + cursor.getString(notetextIndex)
                                        + "\n" + "\n";


                    } while (cursor.moveToNext());

                } else {
                    Log.d(LOG_TAG, "0 rows");
                }
                System.out.println(strConsoleOutput);
                tvOpenNote.setText(strConsoleOutput);

                //в конце закрываем cursor. Освобождаем ресурс.
                cursor.close();

                //закрываем соединение с БД.
                dbHelper.close();

//////////////////---------------------->
                break;
            }


            case R.id.btnSelectAllNotes: {
                //////////////////---------------------->
                //считываем БД.


                //для работы с БД.
                dbHelper = new DBHelper(SearchNotesActivity.this);


                //класс SQLiteDatabase предназначен для управления БД SQLite.
                //если БД не существует, dbHelper вызовет метод onCreate(),
                //если версия БД изменилась, dbHelper вызовет метод onUpgrade().

                //в любом случае вернётся существующая, толькочто созданная или обновлённая БД.
                sqLiteDatabase = dbHelper.getWritableDatabase();

                //группировку и (сортировку-используем) не используем, поэтому используем везде null.

//--сортировка данных по результату.
                orderBy = null;
                //сортировка по результату.
                Log.d(LOG_TAG, "--- Сортировка по категории ---");
                orderBy = dbHelper.TABLE_NOTES_KEY_CATEGORY;
                //"result";
//--сортировка данных по категории.

                //метод query() возвращает объект типа Cursor,
                //его можно рассматривать как набор строк с данными.
                cursor = sqLiteDatabase.query(dbHelper.TABLE_NOTES_NAME, null, null, null, null, null, orderBy);

                strConsoleOutput = "";
                //метод cursor.moveToFirst() делает 1-ю запись в cursor активной
                //и проверяет, есть ли в cursor что-то.
                if (cursor.moveToFirst()) {
                    //получаем порядковые номера столбцов по их именам.
                    int idIndex = cursor.getColumnIndex(dbHelper.TABLE_NOTES_KEY_ID);
                    int categoryIndex = cursor.getColumnIndex(dbHelper.TABLE_NOTES_KEY_CATEGORY);
                    int titleIndex = cursor.getColumnIndex(dbHelper.TABLE_NOTES_KEY_TITLE);
                    int importanceIndex = cursor.getColumnIndex(dbHelper.TABLE_NOTES_KEY_IMPORTANCE);
                    int notetextIndex = cursor.getColumnIndex(dbHelper.TABLE_NOTES_KEY_NOTETEXT);


                    //с помощью метода .moveToNext() перебираем все строки в cursor-е.
                    do {
                        Log.d(
                                //"log", "id " //+ cursor.getInt(idIndex)
                                "log", "id " + cursor.getInt(idIndex)
                                        + " category " + cursor.getString(categoryIndex)
                                        + " title " + cursor.getString(titleIndex)
                                        + " importance " + cursor.getString(importanceIndex)
                                        + " notetext " + cursor.getString(notetextIndex)
                        );


                       /*
                        strConsoleOutput += "Note{"
                                + "category = " + cursor.getString(categoryIndex)
                                + ", title = " + cursor.getString(titleIndex)
                                + ", importance = " + cursor.getString(importanceIndex)
                                + ", notetext = " + cursor.getString(notetextIndex)
                                + "}" + "\n" + "\n";
                        */

                        strConsoleOutput +=
                                "Category : "
                                        + cursor.getString(categoryIndex)
                                        + "\n"
                                        + "Title : "
                                        + cursor.getString(titleIndex)
                                        + "\n"
                                        + cursor.getString(notetextIndex)
                                        + "\n" + "\n";


                    } while (cursor.moveToNext());

                } else {
                    Log.d(LOG_TAG, "0 rows");
                }
                System.out.println(strConsoleOutput);
                tvOpenNote.setText(strConsoleOutput);
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
