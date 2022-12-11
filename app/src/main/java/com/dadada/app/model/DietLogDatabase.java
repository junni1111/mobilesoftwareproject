package com.dadada.app.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {DietLog.class}, version = 1)
public abstract class DietLogDatabase extends RoomDatabase {

    public abstract DietLogDAO dietLogDAO();

    private static DietLogDatabase instance;

    public static synchronized DietLogDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            DietLogDatabase.class, "diet_log_table")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            InitializeData();
        }
    };

    private static void InitializeData() {
        DietLogDAO dietLogDAO = instance.dietLogDAO();
    }
}
