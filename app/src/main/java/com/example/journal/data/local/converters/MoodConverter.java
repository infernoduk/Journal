package com.example.journal.data.local.converters;

import androidx.room.TypeConverter;

import com.example.journal.data.local.entities.Mood;

public class MoodConverter {
    @TypeConverter
    public static Mood toMood(String value) {
        return value == null ? null : Mood.valueOf(value);
    }

    @TypeConverter
    public static String fromMood(Mood mood) {
        return mood == null ? null : mood.name();
    }
}