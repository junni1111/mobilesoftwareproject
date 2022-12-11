package com.dadada.app.model;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DietLogRepository {
    private DietLogDAO dietLogDAO;


    private LiveData<List<DietLog>> dietLogs;

    public DietLogRepository(Application application) {
        DietLogDatabase courseDatabase = DietLogDatabase.getInstance(application);
        dietLogDAO = courseDatabase.dietLogDAO();
    }

    public LiveData<List<DietLog>> getAllDietLogs() {
        return dietLogDAO.getAllDietLogs();
    }

    public LiveData<List<DietLog>> getDietLogById(int id) {
        return dietLogDAO.getDietLogById(id);
    }

    public LiveData<List<DietLog>> getDietLogByDay(String day) {
        return dietLogDAO.getDietLogByDay(day);
    }

    public LiveData<List<DietLog>> getAllDietLogsByDate() {
        return dietLogDAO.getAllDietLogsByDate();
    }

    public LiveData<Integer> getCaloriesAfterDate(long date) {
        return dietLogDAO.getCaloriesAfterDate(date);
    }

    public void insertDietLog(DietLog dietLog) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                dietLogDAO.insert(dietLog);
            }
        });
    }

    public void deleteDietLog(DietLog dietLog) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                dietLogDAO.delete(dietLog);
            }
        });
    }

    public void updateDietLog(DietLog dietLog) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                dietLogDAO.update(dietLog);
            }
        });
    }

}
