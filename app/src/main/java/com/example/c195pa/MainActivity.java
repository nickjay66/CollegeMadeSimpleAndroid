package com.example.c195pa;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.c195pa.Entities.Assessment;
import com.example.c195pa.Entities.Course;
import com.example.c195pa.View.AssessmentViewModel;
import com.example.c195pa.View.CourseViewModel;
import com.example.c195pa.View.HelpActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    String CHANNEL_ID = "1";
    CourseViewModel mCourseViewModel;
    AssessmentViewModel mAssessmentViewModel;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    String currentDate = sdf.format(Calendar.getInstance().getTime());
    List<Course> Courses;
    List<Assessment> Assessments;
    public static final int HELP_ACTIVITY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.help);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(view.getContext(), HelpActivity.class), HELP_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        //Check to see if current date is start or end date of course. Send notification
        mCourseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);

        mCourseViewModel.getAllCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                Courses = courses;
                if (!Courses.isEmpty()) {
                    for (int i = 0; i < Courses.size(); i++) {
                        Course courseCheck = Courses.get(i);
                        Date sDate = courseCheck.getStartDate();
                        Date eDate = courseCheck.getEndDate();
                        String startDate = sdf.format(sDate);
                        String endDate = sdf.format(eDate);
                        if (startDate.equals(currentDate) || endDate.equals(currentDate)) {
                            addNotification(1);
                        }
                    }
                }
            }
        });

        mAssessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);

        mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(List<Assessment> assessments) {
                Assessments = assessments;
                if (!Assessments.isEmpty()) {
                    for (int i = 0; i < Assessments.size(); i++) {
                        Assessment assessmentCheck = Assessments.get(i);
                        Date date = assessmentCheck.getDueDate();
                        String dueDate = sdf.format(date);
                        if (dueDate.equals(currentDate)) {
                            addNotification(2);
                        }
                    }
                }
            }
        });
    }

    // Creates and displays a notification
    private void addNotification(int i) {
        // Builds your notification
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_assignment_black_24dp)
                .setContentTitle("Course Alert")
                .setContentText("You have a course either starting or ending soon");

        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_assignment_black_24dp)
                .setContentTitle("Assessment Alert")
                .setContentText("You have an assessment coming up");

        // Creates the intent needed to show the notification
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        if (i == 1) {
            // Add as notification
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        } else {
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder2.build());
        }
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Personal Notifications";
            String description = "Include all the personal notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);

            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void launchTerm(View view) {
        Intent intent = new Intent(this, TermScreenActivity.class);
        startActivity(intent);

    }

}
