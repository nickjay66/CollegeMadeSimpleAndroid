package com.example.c195pa;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.c195pa.DAO.AssessmentDao;
import com.example.c195pa.DAO.CourseDao;
import com.example.c195pa.DAO.StudySessionDao;
import com.example.c195pa.DAO.TermDao;
import com.example.c195pa.Entities.Assessment;
import com.example.c195pa.Entities.Course;
import com.example.c195pa.Entities.StudySession;
import com.example.c195pa.Entities.Term;


@Database(entities = {Term.class, Course.class, Assessment.class, StudySession.class}, version = 18, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class RoomDatabase extends androidx.room.RoomDatabase {

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract AssessmentDao assessmentDao();
    public abstract StudySessionDao studySessionDao();
    private static RoomDatabase INSTANCE;

    //Create Singleton Instance
    public static RoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDatabase.class) {
                if (INSTANCE == null) {
                    //Create Database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDatabase.class, "term_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()//.addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static androidx.room.RoomDatabase.Callback sRoomDatabaseCallback =
            new androidx.room.RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };


                private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

                    private final TermDao mDao;
                    private final CourseDao mCourseDao;
                    private final AssessmentDao mAssessmentDao;
                    private final StudySessionDao mStudySessionDao;
                    String[] Terms = {"Term 1", "Term 2", "Term 3"};

                    PopulateDbAsync(RoomDatabase db) {

                        mDao = db.termDao();
                        mCourseDao = db.courseDao();
                        mAssessmentDao = db.assessmentDao();
                        mStudySessionDao = db.studySessionDao();
                    }



                    @Override
                    protected Void doInBackground(final Void... params) {
                        // Start the app with a clean database every time.
                        // Not needed if you only populate the database
                        // when it is first created
                        mDao.deleteAll();
                        mCourseDao.deleteAll();
                        mAssessmentDao.deleteAll();
                        mStudySessionDao.deleteALL();
                       /*/ SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        Date start = Calendar.getInstance().getTime();
                        Date end = Calendar.getInstance().getTime();

                        for (int i = 0; i <= Terms.length - 1; i++) {
                            Term Term = new Term(Terms[i], start, end);
                            mDao.insert(Term);
                        }/*/

                        return null;
                    }
                }
            };

