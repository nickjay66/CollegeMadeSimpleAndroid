package com.example.c195pa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewTermActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY =
            "com.example.android.roomtermsssample.REPLY";
    public static final String EXTRA_REPLY2 =
            "com.example.android.roomtermsssample.REPLY2";
    public static final String EXTRA_REPLY3 =
            "com.example.android.roomtermsssample.REPLY3";

    private EditText mEditTermView;
    private EditText mEditStartDate;
    private EditText mEditEndDate;
    String start = null;
    String end = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_term);
        mEditTermView = findViewById(R.id.edit_word);
        mEditStartDate = findViewById(R.id.startDate);
        mEditEndDate = findViewById(R.id.endDate);


        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditTermView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String word = mEditTermView.getText().toString();
                    start = mEditStartDate.getText().toString();
                    end = mEditEndDate.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, word);
                    replyIntent.putExtra(EXTRA_REPLY2, start);
                    replyIntent.putExtra(EXTRA_REPLY3, end);
                    setResult(RESULT_OK, replyIntent);
                }
                //Add more switch statements to throw alert messages
                if (start.contains("/") || end.contains("/") || !start.contains("-") || !end.contains("-")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(NewTermActivity.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Please format date yyyy-MM-dd");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                        }
                    });

                    alertDialog.show();
                    //Toast.makeText(getApplicationContext(), "Please format date mm-dd-yyyy", Toast.LENGTH_LONG).show();
                }

                if (Integer.parseInt(start.substring(0, 3)) > Integer.parseInt(end.substring(0,3))) {
                    AlertDialog alertDialog = new AlertDialog.Builder(NewTermActivity.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("start date cannot be after end date");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                        }
                    });

                    alertDialog.show();
                    //Toast.makeText(getApplicationContext(), "Please format date mm-dd-yyyy", Toast.LENGTH_LONG).show();
                } else {
                    finish();
                }
            }
        });
    }
}