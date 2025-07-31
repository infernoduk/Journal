package com.example.journal.data.dependecyinjection;

import android.app.Application;

import com.example.journal.data.local.JournalDatabase;
import com.example.journal.data.local.dao.JournalDao;
import com.example.journal.data.local.repository.JournalRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class AppModule {
    @Provides
    @Singleton
    public static JournalDatabase provideDatabase(Application app) {
        return JournalDatabase.getInstance(app);
    }

    @Provides
    @Singleton
    public static JournalDao provideJournalDao(JournalDatabase db) {
        return db.journalDao();
    }

    @Provides
    @Singleton
    public static JournalRepository provideRepository(
            Application app,
            JournalDao dao
    ) {
        return new JournalRepository(app);
    }
}