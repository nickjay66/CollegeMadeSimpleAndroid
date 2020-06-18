package com.example.c195pa.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c195pa.Entities.StudySession;

import java.util.List;

@Dao
public interface StudySessionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertStudySession (StudySession studySession);

    @Query("DELETE FROM study_session_table")
    void deleteALL();

    @Query("DELETE FROM study_session_table")
    void deleteSession();

    @Query("SELECT * FROM study_session_table ORDER BY course_name ASC")
    LiveData<List<StudySession>> getAllStudySessions();

    @Query("SELECT * FROM study_session_table WHERE course_name = :mCourseName")
    LiveData<List<StudySession>> getStudySessionByCourseName(String mCourseName);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateStudySession(StudySession studySession);

}
