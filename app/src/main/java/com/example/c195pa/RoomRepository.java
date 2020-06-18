package com.example.c195pa;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.c195pa.DAO.AssessmentDao;
import com.example.c195pa.DAO.CourseDao;
import com.example.c195pa.DAO.StudySessionDao;
import com.example.c195pa.DAO.TermDao;
import com.example.c195pa.Entities.Assessment;
import com.example.c195pa.Entities.Course;
import com.example.c195pa.Entities.StudySession;
import com.example.c195pa.Entities.Term;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RoomRepository {

    private static RoomRepository sInstance;
    private static TermDao mTermDao;
    private static CourseDao mCourseDao;
    private static AssessmentDao mAssessmentDao;
    private static StudySessionDao mStudySessionDao;
    private LiveData<List<Term>> mAllTerms;
    private LiveData<List<Course>> mAllCourses;
    private LiveData<List<Assessment>> mAllAssessments;
    private LiveData<List<StudySession>> mAllStudySessions;
    private RoomDatabase mRoomDB;
    private String name;
    private String start;
    private String end;
    private String mentor;
    private String phone;
    private String email;

    //public RoomRepository(final RoomDatabase database) { mRoomDB = database;}

    public RoomRepository(Application application) {
        RoomDatabase db = RoomDatabase.getDatabase(application);
        mTermDao = db.termDao();
        mCourseDao = db.courseDao();
        mAssessmentDao = db.assessmentDao();
        mStudySessionDao = db.studySessionDao();

        mAllTerms = mTermDao.getAllTerms();
        mAllCourses = mCourseDao.getAllCourses();
        mAllAssessments = mAssessmentDao.getAllAssessments();
        mAllStudySessions = mStudySessionDao.getAllStudySessions();

    }

    /*/
        //Global Singleton
        public static RoomRepository getInstance(final RoomDatabase database) {
            if (sInstance == null) {
                synchronized (RoomRepository.class) {
                    if (sInstance == null) {
                        sInstance = new RoomRepository(database);
                    }
                }
            }
            return sInstance;
        }
    /*/
    //Term Methods
    public LiveData<List<Term>> getAllTerms() {
        return mTermDao.getAllTerms();
    }

    public LiveData<Term> getTermByTermId(int mId) {
        return mTermDao.getTermByTermId(mId);
    }

    public void insert(Term term) {
        new insertAsyncTask(mTermDao).execute(term);
    }

    public void deleteByTermName(String mName) { new deleteAsyncTaskForTerm(mTermDao).execute(mName); }



    //Course Methods
    public LiveData<List<Course>> getAllCourses() {
        return mCourseDao.getAllCourses();
    }

    public LiveData<Course> getCourseByCourseId(int mId) { return mCourseDao.getCourseByCourseId(mId); }

    public LiveData<List<Course>> getCoursesByTermId(int mTermId) { return mCourseDao.getCoursesByTermId(mTermId); }

    public void insertCourse(Course course) { new insertAsyncTaskForCourse(mCourseDao).execute(course); }

    public LiveData<Course> getCourseByCourseName(String name) { return mCourseDao.getCourseByCourseName(name); }

    public void deleteCourse(int id) { new deleteAsyncTaskForCourse(mCourseDao).execute(id); }

   public void upDateCourse(Course course) { new updateAsyncTaskCourse(mCourseDao).execute(course); }


   //Assessment Methods
    public LiveData<List<Assessment>> getAllAssessments() { return mAssessmentDao.getAllAssessments(); }

    public LiveData<List<Assessment>> getAssessmentsByCourseId(int mId) { return mAssessmentDao.getAssessmentsByCourseId(mId); }

    public void insertAssessment(Assessment assessment) { new insertAsyncTaskForAssessment(mAssessmentDao).execute(assessment); }

    public void deleteAssessment(int id) {new deleteAsyncTaskForAssessment(mAssessmentDao).execute(id); }

    public void updateAssessment(Assessment assessment) {new updateAsyncTaskAssessment(mAssessmentDao).execute(assessment); }


    //StudySession Methods
    public LiveData<List<StudySession>> getlAllStudySessions() {return mStudySessionDao.getAllStudySessions();}

    public void insertStudySession(StudySession studySession) {new insertAsyncTaskForStudySession(mStudySessionDao).execute(studySession);}

    public void deleteStudySession() { new deleteAsyncTaskForStudySession(mStudySessionDao).execute();}

    public void updateStudySession(StudySession studySession) { new updateAsyncTaskStudySession(mStudySessionDao).execute(studySession); }

    private static class insertAsyncTask extends AsyncTask<Term, Void, Void> {

        private TermDao mAsyncTaskDao;

        insertAsyncTask(TermDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Term... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }

    }

    private static class deleteAsyncTaskForTerm extends AsyncTask<String, Void, Void> {

        private TermDao mTermDao;

        deleteAsyncTaskForTerm(TermDao dao) {
            mTermDao = dao;
        }
        @Override
        protected Void doInBackground(String... strings) {
            mTermDao.deleteByTermName(strings[0]);
            return null;
        }
    }

    //Course Async Tasks
    private static class insertAsyncTaskForCourse extends AsyncTask<Course, Void, Void> {

        private CourseDao mCourseAsyncTaskDao;

        insertAsyncTaskForCourse(CourseDao dao) {
            mCourseAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Course... params) {
            mCourseAsyncTaskDao.insertCourse(params[0]);
            return null;
        }

    }


    private static class updateAsyncTaskCourse extends AsyncTask<Course, Void, Void> {

        private CourseDao mCourseAsyncTaskDao;

        updateAsyncTaskCourse(CourseDao dao) {
            mCourseAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(Course... params) {
            mCourseAsyncTaskDao.updateCourse(params[0]);
            return null;
        }
    }


    private static class deleteAsyncTaskForCourse extends AsyncTask<Integer, Void, Void> {

        private CourseDao mCourseDao;

        deleteAsyncTaskForCourse(CourseDao dao) { mCourseDao = dao; }
        @Override
        protected Void doInBackground(Integer... integers) {
            mCourseDao.deleteCourse(integers[0]);
            return null;
        }
    }


    //Assessment Async Tasks
    private static class insertAsyncTaskForAssessment extends AsyncTask<Assessment, Void, Void> {

        private AssessmentDao mAssessmentDao;

        insertAsyncTaskForAssessment(AssessmentDao dao) {
            mAssessmentDao = dao;
        }

        @Override
        protected Void doInBackground(final Assessment... params) {
            mAssessmentDao.insertAssessment(params[0]);
            return null;
        }

    }

    private static class deleteAsyncTaskForAssessment extends AsyncTask<Integer, Void, Void> {

        private AssessmentDao mAssessmentDao;

        deleteAsyncTaskForAssessment(AssessmentDao dao) { mAssessmentDao = dao; }
        @Override
        protected Void doInBackground(Integer... integers) {
            mAssessmentDao.deleteAssessment(integers[0]);
            return null;
        }
    }

    private static class updateAsyncTaskAssessment extends AsyncTask<Assessment, Void, Void> {

        private AssessmentDao mAssessmentAsyncTaskDao;

        updateAsyncTaskAssessment(AssessmentDao dao) {
            mAssessmentAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(Assessment... params) {
            mAssessmentAsyncTaskDao.updateAssessment(params[0]);
            return null;
        }
    }


    //StudySession Async Tasks
    private static class insertAsyncTaskForStudySession extends AsyncTask<StudySession, Void, Void> {

        private StudySessionDao mStudySessionDao;

        insertAsyncTaskForStudySession(StudySessionDao dao) {
            mStudySessionDao = dao;
        }

        @Override
        protected Void doInBackground(final StudySession... params) {
            mStudySessionDao.insertStudySession(params[0]);
            return null;
        }

    }

    private static class deleteAsyncTaskForStudySession extends AsyncTask<Integer, Void, Void> {

        private StudySessionDao mStudySessionDao;

        deleteAsyncTaskForStudySession(StudySessionDao dao) { mStudySessionDao = dao; }
        @Override
        protected Void doInBackground(Integer... integers) {
            mStudySessionDao.deleteSession();
            return null;
        }
    }

    private static class updateAsyncTaskStudySession extends AsyncTask<StudySession, Void, Void> {

        private StudySessionDao mStudySessionDao;

        updateAsyncTaskStudySession(StudySessionDao dao) {
            mStudySessionDao = dao;
        }


        @Override
        protected Void doInBackground(StudySession... params) {
            mStudySessionDao.updateStudySession(params[0]);
            return null;
        }
    }
}

