package com.dadada.app.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dadada.app.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {
    private List<String> categories;

    public CategoryAdapter(ArrayList<String> categories) {
        this.categories = categories;
    }


    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_keyword
                , parent, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int i) {
        String category = categories.get(i);
        holder.setDetail(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder {
        private TextView keywordTxt;

        public CategoryHolder(View itemView) {
            super(itemView);
            keywordTxt = itemView.findViewById(R.id.keywordTxt);
        }

        void setDetail(String category) {
            keywordTxt.setText(category);
        }
    }
}