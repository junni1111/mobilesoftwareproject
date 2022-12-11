package com.dadada.app.recyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dadada.app.R;
import com.dadada.app.acitiviy.DetailActivity;
import com.dadada.app.model.DietLog;
import com.dadada.app.parcelable.DietParcelable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SummaryDietAdapter extends RecyclerView.Adapter<SummaryDietAdapter.SummaryDietHolder> {
    private Context context;
    private List<DietLog> diets;

    @Override
    public void onBindViewHolder(@NonNull SummaryDietHolder holder, int position) {
        DietLog diet = diets.get(position);
        holder.setDetail(diet);
    }

    @Override
    public int getItemCount() {
        return diets.size();
    }

    @NonNull
    @Override
    public SummaryDietHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_diet
                , parent, false);
        return new SummaryDietHolder(view);
    }

    public SummaryDietAdapter(Context context) {
        diets = new ArrayList<>();
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<DietLog> newData) {
        if (diets != null) {
            /*PostDiffCallback postDiffCallback = new PostDiffCallback(data, newData);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(postDiffCallback);*/

            diets.clear();
            diets.addAll(newData);
            notifyDataSetChanged();
            //diffResult.dispatchUpdatesTo(this);
        } else {
            // first initialization
            diets = newData;
        }
    }

    class SummaryDietHolder extends RecyclerView.ViewHolder {

        private ImageView itemImg;
        private TextView itemName, itemDate, itemCalorie, itemDetail, itemCount;


        public SummaryDietHolder(@NonNull View itemView) {
            super(itemView);
            itemImg = itemView.findViewById(R.id.itemImg);
            itemName = itemView.findViewById(R.id.itemName);
            itemDate = itemView.findViewById(R.id.itemDate);
            itemCalorie = itemView.findViewById(R.id.itemCalorie);
            itemDetail = itemView.findViewById(R.id.itemDetail);
            itemCount = itemView.findViewById(R.id.itemCount);

        }

        void setDetail(DietLog diet) {
            String date = diet.getDay() + " ";
            int hour = Integer.parseInt(diet.getTime().split(":")[0]);
            int minute = Integer.parseInt(diet.getTime().split(":")[1]);
            if (hour < 12) {
                if (hour == 0) hour += 12;
                date += "오전 " + "" + hour + ":" + minute;
            } else {
                if (hour != 12) hour -= 12;
                date += "오후 " + "" + hour + ":" + minute;
            }

            itemName.setText(diet.getFoodName());
            itemDate.setText(date);
            itemCalorie.setText(String.format("%d Kcal", diet.getFoodCalorie()));
            itemCount.setText("" + diet.getFoodCount());

            String hashTag = "";
            for (String category : diet.getCategories().split("[. ]")) {
                if (!category.trim().equals("")) {
                    hashTag += "#" + category;
                    break;
                }
            }
            hashTag += "#" + diet.getQuantity();

            itemDetail.setText(hashTag);


            try {
                File file = new File(diet.getImagePath());
                Glide.with(context).load(Uri.fromFile(file)).into(itemImg);
            } catch (Exception e) {
                e.printStackTrace();
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DietParcelable data = new DietParcelable(diet.getImagePath(), diet.getFoodName(), diet.getFoodCount(),
                            diet.getFoodCalorie(), diet.getCategories(), diet.getQuantity(), diet.getDay(), diet.getTime(),
                            diet.getMemo(), diet.getRating(), diet.getAddress(), diet.getLatlng());

                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("data", data);
                    context.startActivity(i);
                }
            });
        }
    }
}
