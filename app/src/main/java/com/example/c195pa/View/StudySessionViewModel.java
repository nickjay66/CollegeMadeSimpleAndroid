package com.example.c195pa.View;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.c195pa.Entities.StudySession;
import com.example.c195pa.RoomRepository;

import java.util.List;

public class StudySessionViewModel extends AndroidViewModel {

    private RoomRepository mRepository;
    private com.example.c195pa.DAO.StudySessionDao studySessionDao;
    private LiveData<List<StudySession>> mAllStudySessions;




    public StudySessionViewModel(Application application) {
        super(application);
        mRepository = new RoomRepository(application);
        mAllStudySessions = mRepository.getlAllStudySessions();
    }

    public LiveData<List<StudySession>> getmAllStudySessions() {
        return mAllStudySessions;
    }

    public void insert(StudySession studySession) { mRepository.insertStudySession(studySession); }

    public void updateStudySession(StudySession studySession) {mRepository.updateStudySession(studySession);}

    public void deleteStudySessione() {mRepository.deleteStudySession();}


}

