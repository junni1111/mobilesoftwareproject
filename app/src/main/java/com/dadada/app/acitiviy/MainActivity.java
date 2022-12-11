package com.dadada.app.acitiviy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dadada.app.R;
import com.dadada.app.model.DietLog;
import com.dadada.app.recyclerView.DietAdapter;
import com.dadada.app.viewmodel.MainActivityViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private RecyclerView view;
    public static DietAdapter adapter;
    public static MainActivityViewModel mainActivityViewModel;
    private MaterialDatePicker datePicker;
    private String selectedDateString = "";

    private ImageView calendarBtn, addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        view = findViewById(R.id.RVdiet);
        adapter = new DietAdapter(this);
        setAdapter();

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        mainActivityViewModel.dietLogByDay.observe(this, dietLogs -> {
            adapter.setData(dietLogs);
            for (DietLog log : dietLogs) {
                Log.d("FFFFFFFFFFFFF", log.getCategories());
            }
        });

        datePicker = MaterialDatePicker.Builder
                .datePicker()
                .setTitleText("Select date")
                .build();

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selectedDate) {
                TimeZone timeZoneUTC = TimeZone.getDefault();
                int offsetFromUTC = timeZoneUTC.getOffset(new Date().getTime()) * -1;
                SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
                Date date = new Date(selectedDate + offsetFromUTC);

                selectedDateString = simpleFormat.format(date);

                mainActivityViewModel.setInput(selectedDateString);
            }
        });

        calendarBtn = findViewById(R.id.calendarBtn);
        addBtn = findViewById(R.id.addBtn);

        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show(getSupportFragmentManager(), "fragment_tag");
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MapActivity.class);
                startActivity(i);
            }
        });


    }


    void setAdapter() {
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setHasFixedSize(true);
        view.setItemAnimator(new DefaultItemAnimator());
        view.setAdapter(adapter);
    }
}