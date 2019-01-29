package com.example.baptiste.taskmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.baptiste.taskmanager.db.TaskDbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TaskDbHelper mHelper;
    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;
    public static final String ID_DISPLAY_TASK = "DISPLAY_TASK";

    TextView title;
    TextView start;
    TextView Type;
    TextView Desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mHelper = new TaskDbHelper(this);


        SQLiteDatabase db = mHelper.getReadableDatabase();
        ArrayList array_list = mHelper.getAllTasks();


      //  ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list);
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





}


