package com.example.c195pa;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.c195pa.Entities.Term;
import com.example.c195pa.View.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermScreenActivity extends AppCompatActivity implements RoomAdapter.OnTermListener {

    private TermViewModel mTermViewModel;
    RoomAdapter adapter;
    public static final int NEW_TERM_ACTIVITY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Your Terms");

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new RoomAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mTermViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        mTermViewModel.getAllTerms().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(@Nullable final List<Term> terms) {
                // Update the cached copy of the words in the adapter.
                adapter.setTerms(terms);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(view.getContext(), NewTermActivity.class), NEW_TERM_ACTIVITY_REQUEST_CODE);
            }
        });
/*
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Welcome!");
        builder.setMessage("- Add term in the lower right corner\n" +
                "- Click on term once created to access courses\n" +
                "- Click study session if you want to start a timer that includes mindfulness reminders\n");
        builder.setNeutralButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show(); */
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_TERM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String name = data.getStringExtra(NewTermActivity.EXTRA_REPLY);
            String mStart = data.getStringExtra(NewTermActivity.EXTRA_REPLY2);
            String mEnd = data.getStringExtra(NewTermActivity.EXTRA_REPLY3);

            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date start = null;
            Date end = null;
            try {
                start = sf.parse(mStart);
                end = sf.parse(mEnd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Term term = new Term(name, start, end);
            mTermViewModel.insert(term);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTermClick(int position) {
        //This gets what is clicked and turns it into string. On next screen, send string in query to auto fill data
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        String title1 = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.textView)).getText().toString();

        Toast.makeText(getApplicationContext(), R.string.course_directions, Toast.LENGTH_SHORT).show();
        Term term = adapter.getItem(position);
        int termId = term.getId();
        Date sD = term.getStartDate();
        Date eD = term.getEndDate();
        SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String startDate = sDF.format(sD);
        String endDate = sDF.format(eD);
        Intent intent = new Intent(this, CourseActivity.class);
        intent.putExtra("term", title1);
        intent.putExtra("start", startDate);
        intent.putExtra("end", endDate);
        intent.putExtra("position", termId);
        startActivity(intent);
    }

    public void launchStudy(View view) {
        Intent studyIntent = new Intent(this, StudyActivity.class);
        startActivity(studyIntent);
    }
}






