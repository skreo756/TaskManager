package com.example.baptiste.taskmanager;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.baptiste.taskmanager.db.TaskDbHelper;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TaskDbHelper mHelper;
    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;
    public static final String ID_DISPLAY_TASK = "DISPLAY_TASK";

    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  toolbar = (Toolbar) findViewById(R.id.tool_bar);
       // setSupportActionBar(toolbar);

        mHelper = new TaskDbHelper(this);


        SQLiteDatabase db = mHelper.getReadableDatabase();
        ArrayList array_list = mHelper.getAllTasks();



        ArrayAdapter mAdapter = new ArrayAdapter<>(this, R.layout.item_todo, R.id.task_title,  array_list);

        mTaskListView = (ListView) findViewById(R.id.list_todo);
        mTaskListView.setAdapter(mAdapter);


        mTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int id_To_Search = position + 1;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);

                Intent intent = new Intent(getApplicationContext(), DisplayTaskActivity.class);
                intent.putExtra(ID_DISPLAY_TASK, id_To_Search);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.main_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case R.id.item1:Bundle dataBundle = new Bundle();
            dataBundle.putInt("id", 0);

            Intent intent = new Intent (getApplicationContext(), CreateActivity.class);
            intent.putExtras(dataBundle);

            startActivity(intent);
            return true;

            case R.id.sport:
                Intent SportIntent = new Intent(getApplicationContext(), SportActivity.class);
                startActivity(SportIntent);
                return true;

            case R.id.speech:
                promptSpeechInput();
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Dites quelque chose");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Reconnaissance vocale non supportée",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if ((( result.get(0).contains("sport")) || ( result.get(0).contains("sportive"))
                    ||( result.get(0).contains("entraînement")))&&( (result.get(0).contains("démarrer")) ||
                            (result.get(0).contains("commencer")))) {
                        Intent sportIntent = new Intent(this, SportActivity.class);
                        startActivity(sportIntent);
                    }
                    Log.w(TAG, result.get(0));
                }
                break;
            }

        }
    }





}


