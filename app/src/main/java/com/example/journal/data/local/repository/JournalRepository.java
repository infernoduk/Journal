package com.example.journal.data.local.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.journal.data.local.JournalDatabase;
import com.example.journal.data.local.dao.JournalDao;
import com.example.journal.data.local.entities.JournalEntry;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class JournalRepository {
    private final JournalDao journalDao;
    private final Executor executor;

    public JournalRepository(Application application) {
        JournalDatabase database = JournalDatabase.getInstance(application);
        journalDao = database.journalDao();
        executor = Executors.newSingleThreadExecutor();
    }

    // Insert with callback
    public void insert(JournalEntry entry, OnInsertCompleteListener listener) {
        executor.execute(() -> {
            long id = journalDao.insert(entry);
            listener.onComplete(id);
        });
    }

    public LiveData<List<JournalEntry>> getAllEntries() {
        return journalDao.getAllEntries();
    }

    public LiveData<List<JournalEntry>> getEntriesForDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        Date start = cal.getTime();

        cal.add(Calendar.DATE, 1);
        Date end = cal.getTime();

        return journalDao.getEntriesByDate(start, end);
    }

    public LiveData<Integer> getTotalEntries() {
        return journalDao.getTotalEntriesCount();
    }

    public LiveData<Integer> getThisWeekEntriesCount() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        Date start = cal.getTime();

        cal.add(Calendar.DATE, 6);
        Date end = cal.getTime();

        return journalDao.getWeeklyEntriesCount(start, end);
    }

    public void deleteAllEntries() {
        executor.execute(journalDao::deleteAll);
    }

    public interface OnInsertCompleteListener {
        void onComplete(long id);
    }
}