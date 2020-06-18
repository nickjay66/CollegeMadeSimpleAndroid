package com.example.c195pa.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(tableName = "study_session_table")
public class StudySession implements Serializable {


    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "session_id")
    private int mId;

    @NonNull
    @ColumnInfo(name = "date")
    private Date mDate;

    @NonNull
    @ColumnInfo(name = "notes")
    private String mNotes;

    @ColumnInfo(name = "study_time")
    private String mStudyTime;

    @NonNull
    @ColumnInfo(name = "course_name")
    private String mCourseName;

    public StudySession(@NonNull Date mDate, String mNotes, String mStudyTime, String mCourseName) {
        this.mDate = mDate;
        this.mNotes = mNotes;
        this.mStudyTime = mStudyTime;
        this.mCourseName = mCourseName;
    }

    public StudySession() {

    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    @NonNull
    public Date getDate() {
        return mDate;
    }

    public void setDate(@NonNull Date mDate) {
        this.mDate = mDate;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String mNotes) {
        this.mNotes = mNotes;
    }

    public String getStudyTime() {
        return mStudyTime;
    }

    public void setStudyTime(String mStudyTime) {
        this.mStudyTime = mStudyTime;
    }

    public String getCourseName() {
        return mCourseName;
    }

    public void setCourseName(String mCourseName) {
        this.mCourseName = mCourseName;
    }

}


