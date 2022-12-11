package com.dadada.app.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DietLogDAO {

    @Insert
    void insert(DietLog dietLog);

    @Update
    void update(DietLog dietLog);

    @Delete
    void delete(DietLog dietLog);

    @Query("select * from diet_log_table")
    LiveData<List<DietLog>> getAllDietLogs();

    @Query("select * from diet_log_table order by diet_date desc")
    LiveData<List<DietLog>> getAllDietLogsByDate();

    @Query("select sum(diet_food_calorie) from diet_log_table where diet_date >= :date")
    LiveData<Integer> getCaloriesAfterDate(long date);

    @Query("select * from diet_log_table where diet_date_day like '%' || :day || '%' order by diet_date desc")
    LiveData<List<DietLog>> getDietLogByDay(String day);

    @Query("select * from diet_log_table where id==:id")
    LiveData<List<DietLog>> getDietLogById(int id);
}
