package com.example.c195pa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;


public class ReportActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Study Timer");

        EditText courseText = findViewById(R.id.course_name);
        EditText studyText = findViewById(R.id.study_time);
        EditText timeStamp = findViewById(R.id.timeStamp);

        Date currentTime = Calendar.getInstance().getTime();

        Intent i = this.getIntent();
        String courseName = i.getStringExtra("courseName");
        String mins = i.getStringExtra("totalMins");
        System.out.println(mins);


        if (!courseName.isEmpty()) {
            courseText.setText(courseName);
        } else {
            courseText.setText("All Courses");
        }
        studyText.setText(mins);
        timeStamp.setText(currentTime.toString());


    }

}
