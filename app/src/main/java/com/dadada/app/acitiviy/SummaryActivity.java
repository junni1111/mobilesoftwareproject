package com.dadada.app.acitiviy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dadada.app.R;
import com.dadada.app.model.DietLog;
import com.dadada.app.recyclerView.SummaryDietAdapter;
import com.dadada.app.viewmodel.MainActivityViewModel;

import java.util.Date;

public class SummaryActivity extends AppCompatActivity {
    public static MainActivityViewModel mainActivityViewModel;
    private RecyclerView recentDietList;
    private SummaryDietAdapter recentDietAdapter;
    private ImageView recentDietBtn, addBtn;
    private TextView calorieTV;
    private ProgressBar circleProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        addBtn = findViewById(R.id.addBtn);
        recentDietList = findViewById(R.id.recentDietList);
        recentDietBtn = findViewById(R.id.recentDietBtn);
        calorieTV = findViewById(R.id.calorieTV);
        circleProgressBar = findViewById(R.id.circleProgressBar);

        recentDietList = findViewById(R.id.recentDietList);
        recentDietAdapter = new SummaryDietAdapter(this);
        setAdapter();

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getAllDietLogsByDate().observe(this, dietLogs -> {
            recentDietAdapter.setData(dietLogs);
            for (DietLog log : dietLogs) {
                Log.d("FFFFFFFFFFFFF", log.getCategories());
            }
        });

        mainActivityViewModel.getCaloriesAfterDate((new Date().getTime()) - 3600 * 24 * 7 * 1000).observe(this, calories -> {
            if (calories != null) {
                calorieTV.setText(String.format("%d Kcal", calories));
                circleProgressBar.setProgress(calories);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SummaryActivity.this, MapActivity.class);
                startActivity(i);
            }
        });


        recentDietBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

    }

    private void startMainActivity() {
        Intent i = new Intent(SummaryActivity.this, MainActivity.class);
        startActivity(i);
    }

    void setAdapter() {
        recentDietList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recentDietList.setHasFixedSize(true);
        recentDietList.setItemAnimator(new DefaultItemAnimator());
        recentDietList.setAdapter(recentDietAdapter);
    }
}