package com.example.c195pa;

import android.content.Intent;
import android.os.Bundle;

import com.example.c195pa.Adapters.CourseAdapter;
import com.example.c195pa.Entities.Course;
import com.example.c195pa.View.CourseViewModel;
import com.example.c195pa.View.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseActivity extends AppCompatActivity implements CourseAdapter.OnCourseListener {

    private CourseViewModel mCourseViewModel;
    public static final int NEW_COURSE_ACTIVITY_REQUEST_CODE = 1;
    int termId;
    String termName;
    String start;
    String end;
    CourseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            termName = extras.getString("term");
            TextView v = findViewById(R.id.Term);
            v.setText(termName);
            start = extras.getString("start");
            TextView v2 = findViewById(R.id.startDateText);
            v2.setText("Start Date: " + start);
            end = extras.getString("end");
            TextView v3 = findViewById(R.id.endDateText);
            v3.setText("End Date: " + end);
            termId = extras.getInt("position");

        }


        RecyclerView recyclerView = findViewById(R.id.recyclerview_course);
        adapter = new CourseAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Try and query course table in db to get associated courses w/term_id
        mCourseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        mCourseViewModel.getCoursesByTermId(termId).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(@Nullable final List<Course> courses) {
                // Update the cached copy of the words in the adapter.
                adapter.setCourses(courses);
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(view.getContext(), AddCourseActivity.class), NEW_COURSE_ACTIVITY_REQUEST_CODE);
            }
        });

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == NEW_COURSE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date start = null;
            Date end = null;
            try {
                start = dateFormat.parse(data.getStringExtra(AddCourseActivity.EXTRA_REPLY2));
                end = dateFormat.parse(data.getStringExtra(AddCourseActivity.EXTRA_REPLY3));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            final String name = data.getStringExtra(AddCourseActivity.EXTRA_REPLY);
            String status = data.getStringExtra(AddCourseActivity.EXTRA_REPLY4);
            String mentorName = data.getStringExtra(AddCourseActivity.EXTRA_REPLY5);
            String mentorPhone = data.getStringExtra(AddCourseActivity.EXTRA_REPLY6);
            String mentorEmail = data.getStringExtra(AddCourseActivity.EXTRA_REPLY7);
            String notes = data.getStringExtra(AddCourseActivity.EXTRA_REPLY8);
            Course course = new Course(name, start, end, mentorName, mentorEmail, mentorPhone, status, termId, notes);
            mCourseViewModel.insert(course);

        } else if (requestCode == NEW_COURSE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_CANCELED) {

            Course course = (Course) data.getSerializableExtra("course");
            Log.d("check this", course.getName());
            mCourseViewModel.updateCourse(course);
        } else if (requestCode == NEW_COURSE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_FIRST_USER) {
            Toast.makeText(getApplicationContext(),
                    "Course Deleted",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onCourseClick(final int position) {
        //This gets what is clicked and turns it into string. On next screen, send string in query to auto fill data
        final RecyclerView recyclerView = findViewById(R.id.recyclerview_course);
        String title1 = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.textView)).getText().toString();
        Toast.makeText(getApplicationContext(), title1, Toast.LENGTH_SHORT).show();
        final Intent intent = new Intent(this, EditCourseActivity.class);

        SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Course course = adapter.getItem(position);
        String name = course.getName();
        Date s = course.getStartDate();
        Date e = course.getEndDate();
        String start = sDF.format(s);
        String end = sDF.format(e);
        String status = course.getStatus();
        String mentor = course.getMentor();
        String mentorEmail = course.getMentorEmail();
        String mentorPhone = course.getMentorPhone();
        String notes = course.getNotes();
        int courseId = course.getId();


        int termId = course.getTermId();
        int id = course.getId();
        //FIX ME - Not deleting from recyclerView
        //adapter.removeAt(position);}
        intent.putExtra("name", name);
        intent.putExtra("start", start);
        intent.putExtra("end", end);
        intent.putExtra("status", status);
        intent.putExtra("mentor", mentor);
        intent.putExtra("mentorEmail", mentorEmail);
        intent.putExtra("mentorPhone", mentorPhone);
        intent.putExtra("notes", notes);
        intent.putExtra("termId", termId);
        intent.putExtra("courseId", courseId);
        startActivityForResult(intent, NEW_COURSE_ACTIVITY_REQUEST_CODE);

    }


    public void DeleteTerm(View view) {

        //Not properly checking if courses are associated with term
        if (adapter.getItemCount() == 0) {
            TermViewModel mTermViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
            Log.d("check termName:", "term is:" + termName);
            mTermViewModel.deleteByTermName(termName);
            Intent intent = new Intent(this, TermScreenActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Cannot delete term while courses are assigned", Toast.LENGTH_SHORT).show();

        }

    }

}

