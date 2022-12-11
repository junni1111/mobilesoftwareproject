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

public class MemoActivity extends AppCompatActivity {
    private DietParcelable data;
    private Button nextBtn;
    private ImageView backBtn;
    private EditText contentEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        data = getIntent().getParcelableExtra("data");
        Log.d("memo", data.getAddress());
        Log.d("memo", data.getLatlng());
        Log.d("memo", data.getImagePath());
        Log.d("memo", data.getName());
        Log.d("memo", "" + data.getCount());
        Log.d("memo", "" + data.getCalorie());
        Log.d("memo", data.getCategory());
        Log.d("memo", data.getQuantity());
        Log.d("memo", data.getDay());
        Log.d("memo", data.getTime());


        backBtn = findViewById(R.id.backBtn);
        nextBtn = findViewById(R.id.nextBtn);
        contentEdt = findViewById(R.id.contentEdt);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contentText = contentEdt.getText().toString().trim();
//                if (contentText.equals("")) {
//                    return;
//                }
                Intent i = new Intent(MemoActivity.this, RatingActivity.class);
                data.setMemo(contentText);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        contentEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력난에 변화가 있을 시 조치
//                Log.d("search", "'" + s.toString().trim() + "'");

                String input = s.toString().trim();
                if (input.equals("")) {
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