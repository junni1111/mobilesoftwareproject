package com.dadada.app.acitiviy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.dadada.app.R;
import com.dadada.app.parcelable.DietParcelable;
import com.willy.ratingbar.BaseRatingBar;

public class RatingActivity extends AppCompatActivity {
    private DietParcelable data;
    private Button nextBtn;
    private ImageView backBtn;
    private BaseRatingBar ratingBar;
    private int ratingPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        data = getIntent().getParcelableExtra("data");
        Log.d("rating", data.getAddress());
        Log.d("rating", data.getLatlng());
        Log.d("rating", data.getImagePath());
        Log.d("rating", data.getName());
        Log.d("rating", "" + data.getCount());
        Log.d("rating", "" + data.getCalorie());
        Log.d("rating", data.getCategory());
        Log.d("rating", data.getQuantity());
        Log.d("rating", data.getDay());
        Log.d("rating", data.getTime());
        Log.d("rating", data.getMemo());


        backBtn = findViewById(R.id.backBtn);
        nextBtn = findViewById(R.id.nextBtn);
        ratingBar = findViewById(R.id.ratingBar);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ratingPoint == 0) return;

                Intent i = new Intent(RatingActivity.this, FinishActivity.class);
                data.setRating(ratingPoint);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        ratingBar.setOnRatingChangeListener((ratingBar, rating, fromUser) -> {
            ratingPoint = (int) rating;

            if (ratingPoint != 0) {
                setNextBtnSelectedMode();
            } else {
                setNextBtnUnSelectedMode();
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