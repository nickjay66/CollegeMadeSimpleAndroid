package com.example.c195pa;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.c195pa.Entities.Course;
import com.example.c195pa.Entities.Term;
import com.example.c195pa.Exceptions.DateException;
import com.example.c195pa.View.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class AddCourseActivity extends AppCompatActivity {

    public static final int NEW_COURSE_ACTIVITY_REQUEST_CODE = 1;
    private EditText mCourseName;
    private EditText mStartDate;
    private EditText mEndDate;
    private EditText mStatus;
    private EditText mMentorName;
    private EditText mMentorPhone;
    private EditText mMentorEmail;
    private EditText mNotes;




    public static final String EXTRA_REPLY =
            "com.example.android.roomtermsssample.REPLY";
    public static final String EXTRA_REPLY2 =
            "com.example.android.roomtermsssample.REPLY2";
    public static final String EXTRA_REPLY3 =
            "com.example.android.roomtermsssample.REPLY3";
    public static final String EXTRA_REPLY4 =
            "com.example.android.roomtermsssample.REPLY4";
    public static final String EXTRA_REPLY5 =
            "com.example.android.roomtermsssample.REPLY5";
    public static final String EXTRA_REPLY6 =
            "com.example.android.roomtermsssample.REPLY6";
    public static final String EXTRA_REPLY7 =
            "com.example.android.roomtermsssample.REPLY7";
    public static final String EXTRA_REPLY8 =
            "com.example.android.roomtermsssample.REPLY8";




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Course");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCourseName = findViewById(R.id.courseNameEditText1);
        mStartDate = findViewById(R.id.startDateEditText1);
        mEndDate = findViewById(R.id.endDatEditText1);
        mStatus = findViewById(R.id.statusEditText1);
        mMentorName = findViewById(R.id.mentorNameEditText1);
        mMentorPhone = findViewById(R.id.mentorPhoneEditText1);
        mMentorEmail = findViewById(R.id.mentorEmailEditText1);
        mNotes = findViewById(R.id.noteEditText1);

       /*/ Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            Intent i = getIntent();
            Course course = (Course) i.getSerializableExtra("course");
            mCourseName.setText(course.getName());
            mStartDate.setText(course.getStartDate().toString());
            mEndDate.setText(course.getEndDate().toString());
            mStatus.setText(course.getStatus());
            mMentorName.setText(course.getMentor());
            mMentorPhone.setText(course.getMentorPhone());
            mMentorEmail.setText(course.getMentorEmail());
            mNotes.setText(course.getNotes());

        }

        */


        final Button button = findViewById(R.id.button21);
        //Write code to save course info, but not return to the next screen.
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                String startDate = mStartDate.getText().toString();
                String endDate = mEndDate.getText().toString();

                if (TextUtils.isEmpty(mCourseName.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String name = mCourseName.getText().toString();

                    //Convert String to date type.
                    String status = mStatus.getText().toString();
                    String mentorName = mMentorName.getText().toString();
                    String mentorPhone = mMentorPhone.getText().toString();
                    String mentorEmail = mMentorEmail.getText().toString();
                    String notes = mNotes.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, name);
                    replyIntent.putExtra(EXTRA_REPLY2, startDate);
                    replyIntent.putExtra(EXTRA_REPLY3, endDate);
                    replyIntent.putExtra(EXTRA_REPLY4, status);
                    replyIntent.putExtra(EXTRA_REPLY5, mentorName);
                    replyIntent.putExtra(EXTRA_REPLY6, mentorPhone);
                    replyIntent.putExtra(EXTRA_REPLY7, mentorEmail);
                    replyIntent.putExtra(EXTRA_REPLY8, notes);

                    setResult(RESULT_OK, replyIntent);
                }

                //Add more switch statements to throw alert messages
                if (startDate.contains("/") || endDate.contains("/")) {
                    makeToast(0);
                } else if (mStartDate.getText().toString().isEmpty() || mEndDate.getText().toString().isEmpty()) {
                    makeToast(1);
                    //Toast.makeText(getApplicationContext(), "Please format date mm-dd-yyyy", Toast.LENGTH_LONG).show();
                } else if (Integer.parseInt(startDate.substring(0,3)) > Integer.parseInt(endDate.substring(0,3))) {
                    makeToast(2);
                    //Toast.makeText(getApplicationContext(), "Please format date mm-dd-yyyy", Toast.LENGTH_LONG).show();
                } else {
                    finish();
                }

            }
        });


/*/
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(view.getContext(), AddCourseActivity.class), NEW_COURSE_ACTIVITY_REQUEST_CODE);
            }
        });
        //Change date strings to date type once working properly
/*/
    }


       public void makeToast(int id) {
            String toastString = "";
            if (id == 1) {
                toastString = "Please add both dates in the correct format";
            } else if(id == 2) {
                toastString = "Start date cannot be greater than end date";
            } else if (id == 0) {
                toastString = "Please format date yyyy-MM-dd";
            }
           AlertDialog alertDialog = new AlertDialog.Builder(AddCourseActivity.this).create();
           alertDialog.setTitle("Error");
           alertDialog.setMessage(toastString);

           alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {
                   Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
               }
           });

           alertDialog.show();
       }




}
