package com.example.journal.data.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.journal.data.local.entities.Category;
import com.example.journal.data.local.entities.JournalEntry;
import com.example.journal.data.local.entities.Mood;
import com.example.journal.data.local.repository.JournalRepository;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class JournalViewModel extends AndroidViewModel {
    private final JournalRepository repository;
    private final MutableLiveData<Long> newEntryId = new MutableLiveData<>();

    @Inject
    public JournalViewModel(
            @NonNull Application application,
            JournalRepository repository
    ) {
        super(application);
        this.repository = repository;
    }

    public void createEntry(String title, String content, Mood mood, Category category) {
        JournalEntry entry = new JournalEntry(
                title,
                content,
                new Date(),
                mood,
                category
        );

        repository.insert(entry, id -> newEntryId.postValue(id));
    }

    public LiveData<List<JournalEntry>> getAllEntries() {
        return repository.getAllEntries();
    }

    public LiveData<List<JournalEntry>> getTodayEntries() {
        return repository.getEntriesForDay(new Date());
    }

    public LiveData<Integer> getTotalEntriesCount() {
        return repository.getTotalEntries();
    }

    public LiveData<Integer> getWeeklyEntriesCount() {
        return repository.getThisWeekEntriesCount();
    }

    public LiveData<Long> getNewEntryId() {
        return newEntryId;
    }

    public void clearAllEntries() {
        repository.deleteAllEntries();
    }
}