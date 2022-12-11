package com.dadada.app.acitiviy;

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

public class QuantityActivity extends AppCompatActivity {
    private DietParcelable data;
    private RecyclerView quantityView;
    private ArrayList<String> quantities;
    public static ItemAdapter quantityAdapter;
    private Button nextBtn;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantity);

        data = getIntent().getParcelableExtra("data");
        Log.d("quantity", data.getAddress());
        Log.d("quantity", data.getLatlng());
        Log.d("quantity", data.getImagePath());
        Log.d("quantity", data.getName());
        Log.d("quantity", "" + data.getCount());
        Log.d("quantity", "" + data.getCalorie());
        Log.d("quantity", data.getCategory());


        addQuantities();

        quantityView = findViewById(R.id.categoryRecyclerview);
        quantityAdapter = new ItemAdapter(this, quantities, 1);
        setAdapter(quantityView, quantityAdapter);

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
                if (quantityAdapter.getCheckedItemCount() == 0) return;

                Boolean[] isChecked = quantityAdapter.getIsChecked();
                String quantityString = "";

                for (int idx = 0; idx < isChecked.length; idx++) {
                    if (isChecked[idx]) {
                        String quantity = quantities.get(idx);
                        quantityString = quantity;
                    }
                }

                Intent i = new Intent(QuantityActivity.this, DatetimeActivity.class);
                data.setQuantity(quantityString);
                i.putExtra("data", data);
                startActivity(i);
            }
        });
    }


    void addQuantities() {
        quantities = new ArrayList<>();
        quantities.add("가볍게");
        quantities.add("적당히");
        quantities.add("배부르게");
        quantities.add("과하게");
    }

    void setAdapter(RecyclerView view, ItemAdapter adapter) {
        view.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        view.setHasFixedSize(true);
        view.setItemAnimator(new DefaultItemAnimator());
        view.setAdapter(adapter);
    }

}