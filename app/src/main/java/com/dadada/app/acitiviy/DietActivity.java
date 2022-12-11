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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dadada.app.R;
import com.dadada.app.parcelable.DietParcelable;

public class DietActivity extends AppCompatActivity {
    private DietParcelable data;
    private ImageView backBtn, minusBtn, plusBtn;
    private EditText dietEdt;
    private TextView dietCnt;
    private int dietCount = 0;
    private Button nextBtn;
    private String nameInput = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

        Intent i = getIntent();
        data = i.getParcelableExtra("data");
        Log.d("diet", data.getAddress());
        Log.d("diet", data.getLatlng());
        Log.d("diet", data.getImagePath());

        backBtn = findViewById(R.id.backBtn);
        dietEdt = findViewById(R.id.dietEdt);
        nextBtn = findViewById(R.id.nextBtn);
        minusBtn = findViewById(R.id.minusBtn);
        plusBtn = findViewById(R.id.plusBtn);
        dietCnt = findViewById(R.id.dietCnt);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dietEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력난에 변화가 있을 시 조치
//                Log.d("search", "'" + s.toString().trim() + "'");

                nameInput = s.toString().trim();
                if (!nameInput.equals("") && dietCount != 0) {
                    setNextBtnSelectedMode();
                } else {
                    setNextBtnUnSelectedMode();
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

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
            @Override
            public void onClick(View v) {
                if (dietCount == 0) return;

                dietCount -= 1;
                dietCnt.setText("" + dietCount);

                if (dietCount == 0) {
                    setNextBtnUnSelectedMode();
                    minusBtn.setBackground(getResources().getDrawable(R.drawable.ic_minus_gray));
                }
            }
        });

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
            @Override
            public void onClick(View v) {
                if (dietCount == 0) {
                    minusBtn.setBackground(getResources().getDrawable(R.drawable.ic_minus_blue));
                    if (!nameInput.equals("")) {
                        setNextBtnSelectedMode();
                    }
                }

                dietCount += 1;
                dietCnt.setText("" + dietCount);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameInput.equals("") || dietCount == 0) return;

                Intent i = new Intent(DietActivity.this, CalorieActivity.class);
                data.setName(nameInput);
                data.setCount(dietCount);
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
