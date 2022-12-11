package com.dadada.app.acitiviy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.dadada.app.R;
import com.dadada.app.parcelable.DietParcelable;

public class CalorieActivity extends AppCompatActivity {
    private DietParcelable data;
    private ImageView backBtn;
    private Button nextBtn;
    private EditText calorieEdt;
    private String calorieInput = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie);

        Intent i = getIntent();
        data = i.getParcelableExtra("data");
        Log.d("calorie", data.getAddress());
        Log.d("calorie", data.getLatlng());
        Log.d("calorie", data.getImagePath());
        Log.d("calorie", data.getName());
        Log.d("calorie", "" + data.getCount());

        backBtn = findViewById(R.id.backBtn);
        calorieEdt = findViewById(R.id.calorieEdt);
        nextBtn = findViewById(R.id.nextBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        calorieEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력난에 변화가 있을 시 조치
//                Log.d("search", "'" + s.toString().trim() + "'");

                calorieInput = s.toString().trim();
                if (calorieInput.equals("")) {
                    setNextBtnUnSelectedMode();
                } else {
                    setNextBtnSelectedMode();
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때 조치
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에 조치
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calorieInput.equals("")) return;

                int calorie = Integer.valueOf(calorieInput);
                Intent i = new Intent(CalorieActivity.this, CategoryActivity.class);
                data.setCalorie(calorie);
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