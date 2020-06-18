package com.example.c195pa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c195pa.Entities.StudySession;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText mEditTextInput;
    private TextView mTextViewCountDown;
    private Button mButtonSet;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private EditText mCourseName;
    private EditText mNotes;
    private Date mDate;
    private EditText mStudyTime;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;

    private String studyTime;

    private int tickTime = 0;
    int millisTick = 0;

    Spinner spinner;

    public static final String EXTRA_REPLY =
            "com.example.android.roomtermsssample.REPLY";
    public static final String EXTRA_REPLY2 =
            "com.example.android.roomtermsssample.REPLY2";
    public static final String EXTRA_REPLY3 =
            "com.example.android.roomtermsssample.REPLY3";
    public static final String EXTRA_REPLY4 =
            "com.example.android.roomtermsssample.REPLY4";


    //StudySession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mEditTextInput = findViewById(R.id.edit_text_input);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        mButtonSet = findViewById(R.id.button_set);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);

        mCourseName = findViewById(R.id.course_text_input);
        mNotes = findViewById(R.id.study_notes);
        mStudyTime = findViewById(R.id.edit_text_input);

        spinner = (Spinner) findViewById(R.id.mindful_spinner);
        spinner.setOnItemSelectedListener(this);


        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                studyTime = mStudyTime.getText().toString();

                String input = mEditTextInput.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(TimerActivity.this, "Must set a time", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0) {
                    Toast.makeText(TimerActivity.this, "Enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }
                setTime(millisInput);
                mEditTextInput.setText("");
            }
        });

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }

    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
    }

    private void startTimer() {

        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();

                tickTime = tickTime + 1;
                System.out.println(millisTick);

                if (mTimeLeftInMillis > 1000) {

                    if (millisTick == 60) {
                        if (tickTime % 60 == 0) {
                            pauseTimer();
                            String mindful = "Stand, stretch, and take 5 deep breaths";
                            Toast.makeText(getApplicationContext(), mindful, Toast.LENGTH_LONG).show();
                        }
                    } else if (millisTick == 300) {
                        if (tickTime % 300 == 0) {
                            pauseTimer();
                            String mindful = "Stand, stretch, and take 5 deep breaths";
                            Toast.makeText(getApplicationContext(), mindful, Toast.LENGTH_LONG).show();
                        }
                    } else if (millisTick == 600) {
                        if (tickTime % 600 == 0) {
                            pauseTimer();
                            String mindful = "Stand, stretch, and take 5 deep breaths";
                            Toast.makeText(getApplicationContext(), mindful, Toast.LENGTH_LONG).show();
                        }
                    } else if (millisTick == 900) {
                        if (tickTime % 900 == 0) {
                            pauseTimer();
                            String mindful = "Stand, stretch, and take 5 deep breaths";
                            Toast.makeText(getApplicationContext(), mindful, Toast.LENGTH_LONG).show();
                        }
                    } else if (millisTick == 1800) {
                        if (tickTime % 1800 == 0) {
                            pauseTimer();
                            String mindful = "Stand, stretch, and take 5 deep breaths";
                            Toast.makeText(getApplicationContext(), mindful, Toast.LENGTH_LONG).show();
                        }
                    }
                }

            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateWatchInterface();

                //Create values to send back to prior screen
                String courseName = mCourseName.getText().toString();
                String notes = mNotes.getText().toString();

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                mDate = new Date();
                String sDate = dateFormat.format(mDate);

                //Attempt to return to main screen when time is finished
                Intent replyIntent = new Intent();
                replyIntent.putExtra(EXTRA_REPLY, courseName);
                replyIntent.putExtra(EXTRA_REPLY2, studyTime);
                replyIntent.putExtra(EXTRA_REPLY3, notes);
                replyIntent.putExtra(EXTRA_REPLY4, sDate);

                setResult(RESULT_OK, replyIntent);
                finish();
            }
        }.start();

        mTimerRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateWatchInterface();
    }

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);

        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);

        }

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void updateWatchInterface() {
        if (mTimerRunning) {
            mEditTextInput.setVisibility(View.INVISIBLE);
            mButtonSet.setVisibility(View.INVISIBLE);
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText("pause");
        } else {
            mEditTextInput.setVisibility(View.VISIBLE);
            mButtonSet.setVisibility(View.VISIBLE);
            mButtonStartPause.setText("Start");
            if (mTimeLeftInMillis < 1000) {
                mButtonStartPause.setVisibility(View.INVISIBLE);
            } else {
                mButtonStartPause.setVisibility(View.VISIBLE);
            }

            if (mTimeLeftInMillis < mStartTimeInMillis) {
                mButtonReset.setVisibility(View.VISIBLE);
            } else {
                mButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);


        updateCountDownText();
        updateWatchInterface();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = (long) 0;
                mTimerRunning = false;
                updateCountDownText();
                updateWatchInterface();
            } else {
                startTimer();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();


        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

        switch (position) {
            case 0:
                millisTick = 60;
                break;
            case 1:
                millisTick = 300;
                break;
            case 2:
                millisTick = 600;
                break;
            case 3:
                millisTick = 900;
                break;
            case 4:
                millisTick = 1800;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

