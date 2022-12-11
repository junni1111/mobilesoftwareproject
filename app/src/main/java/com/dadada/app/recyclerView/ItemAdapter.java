package com.dadada.app.recyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dadada.app.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<String> keywords;
    private Boolean[] isChecked;
    private int checkedItemCount;
    private int maxCheckedCount;

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        String keyword = keywords.get(position);
        holder.setDetail(keyword, position);
    }

    @Override
    public int getItemCount() {
        return keywords.size();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_keyword
                , parent, false);
        return new ItemHolder(view);
    }

    public Boolean[] getIsChecked() {
        return this.isChecked;
    }

    public int getCheckedItemCount() {
        return this.checkedItemCount;
    }

    public ItemAdapter(Context context, ArrayList<String> keywords, int maxCheckedCount) {
        this.keywords = keywords;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.isChecked = new Boolean[keywords.size()];
        this.checkedItemCount = 0;
        this.maxCheckedCount = maxCheckedCount;
        Arrays.fill(isChecked, false);
    }

    public void setData(List<String> newData) {
        if (keywords != null) {
            /*PostDiffCallback postDiffCallback = new PostDiffCallback(data, newData);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(postDiffCallback);*/

            keywords.clear();
            keywords.addAll(newData);
            notifyDataSetChanged();
            //diffResult.dispatchUpdatesTo(this);
        } else {
            // first initialization
            keywords = newData;
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        private TextView keywordTxt;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            keywordTxt = itemView.findViewById(R.id.keywordTxt);
        }

        void setDetail(String keyword, int position) {
            keywordTxt.setText(keyword);
            keywordTxt.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("UseCompatLoadingForDrawables")
                @Override
                public void onClick(View v) {
                    if (isChecked[position]) {
                        keywordTxt.setBackground(context.getResources().getDrawable(R.drawable.r12_spinner_stroke_1px));
                        isChecked[position] = false;
                        checkedItemCount -= 1;

                        if (checkedItemCount == 0) {
                            Button nextBtn = ((Activity) context).findViewById(R.id.nextBtn);
                            nextBtn.setBackground(context.getResources().getDrawable(R.drawable.r12_lightgray_solid));
                        }
                    } else {
                        if (checkedItemCount >= maxCheckedCount) return;
                        keywordTxt.setBackground(context.getResources().getDrawable(R.drawable.r12_primary_solid));
                        isChecked[position] = true;
                        checkedItemCount += 1;

                        if (checkedItemCount != 0) {
                            Button nextBtn = ((Activity) context).findViewById(R.id.nextBtn);
                            nextBtn.setBackground(context.getResources().getDrawable(R.drawable.r12_primary_solid));
                        }
                    }
                }
            });
        }
    }


}
