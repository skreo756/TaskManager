package com.example.baptiste.taskmanager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.baptiste.taskmanager.db.TaskDbHelper;

public class DisplayTaskActivity extends Activity {

    int from_Where_I_Am_Coming = 0;
    private TaskDbHelper mHelper;

    TextView title;
    TextView start;
    TextView Type;
    TextView Desc;

    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task);


        title = (TextView) findViewById(R.id.editTextTitle);
        start = (TextView) findViewById(R.id.editTextStart);
        Type = (TextView) findViewById(R.id.editTextType);
        Desc = (TextView) findViewById(R.id.editTextDesc);

        mHelper = new TaskDbHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");


            if (Value > 0) {
                Cursor rs = mHelper.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String title_ = rs.getString(rs.getColumnIndex(TaskDbHelper.TASK_TITLE));
                String start_ = rs.getString(rs.getColumnIndex(TaskDbHelper.TASK_DATE));
                String type_ = rs.getString(rs.getColumnIndex(TaskDbHelper.TASK_TYPE));
                String desc_ = rs.getString(rs.getColumnIndex(TaskDbHelper.TASK_DESC));

                if (!rs.isClosed()) {
                    rs.close();
                }



                title.setText((CharSequence)title_);
                title.setFocusable(false);
                title.setClickable(false);

                start.setText((CharSequence)start_);
                start.setFocusable(false);
                start.setClickable(false);

                Type.setText((CharSequence)type_);
                Type.setFocusable(false);
                Type.setClickable(false);

                Desc.setText((CharSequence)desc_);
                Desc.setFocusable(false);
                Desc.setClickable(false);

                Button b = (Button)findViewById(R.id.button1);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.display_task, menu);
            } else{
                getMenuInflater().inflate(R.menu.main_menu, menu);
            }
        }
        return true;
    }



}
