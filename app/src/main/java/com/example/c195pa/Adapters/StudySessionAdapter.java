package com.example.c195pa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.c195pa.Entities.StudySession;
import com.example.c195pa.R;

import java.util.ArrayList;
import java.util.List;


public class StudySessionAdapter extends RecyclerView.Adapter<StudySessionAdapter.StudySessionViewHolder> {

    private final LayoutInflater mInflater;
    private List<StudySession> mStudySessions; //Cached copy of studySessions
    private StudySessionAdapter.OnStudySessionListener mOnStudySessionListener;

    private List<StudySession> filteredList = new ArrayList<>();

    public StudySessionAdapter(Context context, StudySessionAdapter.OnStudySessionListener onStudySessionListener) {
        mInflater = LayoutInflater.from(context);
        this.mOnStudySessionListener = onStudySessionListener;
    }

    @Override
    public StudySessionAdapter.StudySessionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new StudySessionAdapter.StudySessionViewHolder(itemView, mOnStudySessionListener);
    }

    @Override
    public void onBindViewHolder(StudySessionAdapter.StudySessionViewHolder holder, int position) {
        if (!filteredList.isEmpty() && filteredList.size() > position) {
            StudySession current = filteredList.get(position);
            holder.StudySessionItemView.setText("Study Session Id: " + current.getId() +
                    ", \nStudy session for: " + current.getCourseName() +
                    ", \nStudy Date: " + current.getDate().toString() +
                    ", \nNotes: " + current.getNotes());

        } else if (position == 0) {
            // Covers the case of data not being ready yet.
            holder.StudySessionItemView.setText("No Study Session");
        }
    }

    public void setStudySession(List<StudySession> studySessions) {
        mStudySessions = studySessions;
        filteredList = studySessions;
        notifyDataSetChanged();
    }

    public List<StudySession> getmStudySessions() {
        return mStudySessions;
    }


    // getItemCount() is called many times, and when it is first called,
    // mCourse has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (filteredList != null)
            return filteredList.size();
        else return 0;
    }

    class StudySessionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView StudySessionItemView;
        StudySessionAdapter.OnStudySessionListener onStudySessionListener;

        private StudySessionViewHolder(View itemView, StudySessionAdapter.OnStudySessionListener onStudySessionListener) {
            super(itemView);
            StudySessionItemView = itemView.findViewById(R.id.textView);
            this.onStudySessionListener = onStudySessionListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onStudySessionListener.onStudySessionClick(getAdapterPosition());

        }
    }

    public interface OnStudySessionListener {
        void onStudySessionClick(int position);

    }

    public void filter(String text) {
        filteredList = new ArrayList<>();
        //StudySessionAdapter adapter = new StudySessionAdapter(this, this);
        for (StudySession item : mStudySessions) {
            if (item.getCourseName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        notifyDataSetChanged();
    }

    public void filterList(ArrayList<StudySession> filteredList) {
        mStudySessions = filteredList;
        notifyDataSetChanged();
    }
}
