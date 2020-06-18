package com.example.c195pa;

import android.content.Intent;
import android.os.Bundle;

import com.example.c195pa.Adapters.StudySessionAdapter;
import com.example.c195pa.Entities.StudySession;
import com.example.c195pa.View.CourseViewModel;
import com.example.c195pa.View.StudySessionViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class StudyActivity extends AppCompatActivity implements StudySessionAdapter.OnStudySessionListener {

    private StudySessionViewModel mStudySessionViewModel;
    StudySessionAdapter adapter;

    private Button reportButton;

    public static final int NEW_TIMER_ACTIVITY_REQUEST_CODE = 1;
    Date studyDate = new Date();
    String textHolder = "";
    int mId;
    RecyclerView recyclerView;

    List<StudySession> sessionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        reportButton = findViewById(R.id.report_button);

        EditText editText = findViewById(R.id.edittext);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                adapter.filter(text);
                textHolder = text;
            }
        });

        recyclerView = findViewById(R.id.recyclerview_study_session);
        adapter = new StudySessionAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Try and query studySession table in db to get associated studySessions
//        mStudySessions = mStudySessionViewModel.getmAllStudySessions();
        mStudySessionViewModel = ViewModelProviders.of(this).get(StudySessionViewModel.class);
        mStudySessionViewModel.getmAllStudySessions().observe(this, new Observer<List<StudySession>>() {
            @Override
            public void onChanged(@Nullable final List<StudySession> studySession) {
                // Update the cached copy of the words in the adapter.
                adapter.setStudySession(studySession);
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(view.getContext(), TimerActivity.class), NEW_TIMER_ACTIVITY_REQUEST_CODE);
            }
        });
    }


//*****Sort of Working. Will search, but does not actively adapt to what is being typed in and deleted.


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String courseName = data.getStringExtra(TimerActivity.EXTRA_REPLY);
        String studyTime = data.getStringExtra(TimerActivity.EXTRA_REPLY2);
        String studyNotes = data.getStringExtra(TimerActivity.EXTRA_REPLY3);
        String tempDate = data.getStringExtra(TimerActivity.EXTRA_REPLY4);

        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            studyDate = dateFormat.parse(tempDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        StudySession session = new StudySession(studyDate, studyNotes, studyTime, courseName);
        mStudySessionViewModel.insert(session);


        //What to do when study session is over and studySession object is returned
    }

    public void createReport(View view) {
        String courseName = textHolder;
        int totalMins = 0;


        List<StudySession> sessions = adapter.getmStudySessions();

        if (adapter.getItemCount() == 0) {
            Toast.makeText(getApplicationContext(), "No study sessions available", Toast.LENGTH_SHORT).show();
        } else {
            for (StudySession item : sessions) {
                if (item.getCourseName().toLowerCase().trim().contains(courseName.toLowerCase())) {
                    int tempNum = Integer.parseInt(item.getStudyTime());
                    totalMins = totalMins + tempNum;
                }
            }

            String mins = Integer.toString(totalMins);

            Intent intent = new Intent(this, ReportActivity.class);
            intent.putExtra("courseName", courseName);
            intent.putExtra("totalMins", mins);
            startActivityForResult(intent, NEW_TIMER_ACTIVITY_REQUEST_CODE);
        }

    }

    public void DeleteSessions(View view) {

        //Not properly checking if courses are associated with term
        if (adapter.getItemCount() != 0) {
            StudySessionViewModel mStudySessionViewModel = ViewModelProviders.of(this).get(StudySessionViewModel.class);
            mStudySessionViewModel.deleteStudySessione();
            Intent intent = new Intent(this, StudyActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "There's nothing to delete", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onStudySessionClick(int position) {

    }
}
