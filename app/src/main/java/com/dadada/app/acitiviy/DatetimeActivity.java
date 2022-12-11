package com.dadada.app.acitiviy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dadada.app.R;
import com.dadada.app.parcelable.DietParcelable;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DatetimeActivity extends AppCompatActivity {
    private DietParcelable data;
    private Button nextBtn;
    private ImageView backBtn;
    private TextView datePickerTxt, timePickerTxt;
    private MaterialDatePicker datePicker;
    private MaterialTimePicker timePicker;
    private String selectedDay, selectedTime;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datetime);

        data = getIntent().getParcelableExtra("data");
        Log.d("datetime", data.getAddress());
        Log.d("datetime", data.getLatlng());
        Log.d("datetime", data.getImagePath());
        Log.d("datetime", data.getName());
        Log.d("datetime", "" + data.getCount());
        Log.d("datetime", "" + data.getCalorie());
        Log.d("datetime", data.getCategory());
        Log.d("datetime", data.getQuantity());

        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
        SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm", Locale.KOREA);
        Date now = new Date();
        selectedDay = dayFormat.format(now);
        int hour = Integer.parseInt(timeFormat.format(now).split(":")[0]);
        int minute = Integer.parseInt(timeFormat.format(now).split(":")[1]);
        selectedTime = "" + hour + ":" + minute;


        backBtn = findViewById(R.id.backBtn);
        nextBtn = findViewById(R.id.nextBtn);
        datePickerTxt = findViewById(R.id.datePickerTxt);
        timePickerTxt = findViewById(R.id.timePickerTxt);

        setNextBtnSelectedMode();
        datePickerTxt.setText(selectedDay);
        timePickerTxt.setText(selectedTime);

        datePicker = MaterialDatePicker.Builder
                .datePicker()
                .setSelection(now.getTime() + 3600 * 9 * 1000)
                .setTitleText("Select date")
                .build();

        timePicker = new MaterialTimePicker.Builder()
                .setHour(hour)
                .setMinute(minute)
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build();

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selectedDate) {
                // user has selected a date
                // format the date and set the text of the input box to be the selected date
                // right now this format is hard-coded, this will change
                ;
                // Get the offset from our timezone and UTC.
                TimeZone timeZoneUTC = TimeZone.getDefault();
                // It will be negative, so that's the -1
                int offsetFromUTC = timeZoneUTC.getOffset(new Date().getTime()) * -1;

                // Create a date format, then a date object with our offset
                SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
                Date date = new Date(selectedDate + offsetFromUTC);

                datePickerTxt.setText(simpleFormat.format(date));
                selectedDay = simpleFormat.format(date);
            }
        });

        timePicker.addOnPositiveButtonClickListener(dialog -> {
            int newHour = timePicker.getHour();
            int newMinute = timePicker.getMinute();
            timePickerTxt.setText(String.format("%d:%d", newHour, newMinute));
            selectedTime = String.format("%d:%d", newHour, newMinute);

        });

        datePickerTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show(getSupportFragmentManager(), "fragment_tag");
            }
        });

        timePickerTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.show(getSupportFragmentManager(), "fragment_tag");
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DatetimeActivity.this, MemoActivity.class);
                data.setDay(selectedDay);
                data.setTime(selectedTime);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void setNextBtnSelectedMode() {
        nextBtn.setBackground(this.getResources().getDrawable(R.drawable.r12_primary_solid));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void setNextBtnUnSelectedMode() {
        nextBtn.setBackground(this.getResources().getDrawable(R.drawable.r12_lightgray_solid));
    }
}