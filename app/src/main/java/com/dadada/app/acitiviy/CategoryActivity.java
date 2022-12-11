package com.dadada.app.acitiviy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dadada.app.R;
import com.dadada.app.parcelable.DietParcelable;
import com.dadada.app.recyclerView.ItemAdapter;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private DietParcelable data;
    private RecyclerView categoryView;
    private ArrayList<String> categories;
    public static ItemAdapter categoryAdapter;
    private Button nextBtn;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        data = getIntent().getParcelableExtra("data");
        Log.d("category", data.getAddress());
        Log.d("category", data.getLatlng());
        Log.d("category", data.getImagePath());
        Log.d("category", data.getName());
        Log.d("category", "" + data.getCount());
        Log.d("category", "" + data.getCalorie());

        addCategories();

        categoryView = findViewById(R.id.categoryRecyclerview);
        categoryAdapter = new ItemAdapter(this, categories, categories.size());
        setAdapter(categoryView, categoryAdapter);

        backBtn = findViewById(R.id.backBtn);
        nextBtn = findViewById(R.id.nextBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryAdapter.getCheckedItemCount() == 0) return;

                Boolean[] isChecked = categoryAdapter.getIsChecked();
                StringBuilder categoryString = new StringBuilder();

                for (int idx = 0; idx < isChecked.length; idx++) {
                    if (isChecked[idx]) {
                        String category = categories.get(idx);
                        categoryString.append(category).append(".");
                    }
                }

                Intent i = new Intent(CategoryActivity.this, QuantityActivity.class);
                data.setCategory(categoryString.toString());
                i.putExtra("data", data);
                startActivity(i);
            }
        });
    }

    void addCategories() {
        categories = new ArrayList<>();
        categories.add("아침");
        categories.add("아점");
        categories.add("점저");
        categories.add("저녁");
        categories.add("간식");
        categories.add("야식");
        categories.add("후식");
        categories.add("술");
        categories.add("음료");
        categories.add("영양제/보충제");
    }

    void setAdapter(RecyclerView view, ItemAdapter adapter) {
        view.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        view.setHasFixedSize(true);
        view.setItemAnimator(new DefaultItemAnimator());
        view.setAdapter(adapter);
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