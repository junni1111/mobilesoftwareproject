package com.dadada.app.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.dadada.app.BR;


@Entity(tableName = "diet_log_table")
public class DietLog extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "diet_food_name")
    private String foodName;

    @ColumnInfo(name = "diet_food_count")
    private int foodCount;

    @ColumnInfo(name = "diet_food_calorie")
    private int foodCalorie;

    @ColumnInfo(name = "diet_image_path")
    private String imagePath;

    @ColumnInfo(name = "diet_address")
    private String address;

    @ColumnInfo(name = "diet_latlng")
    private String latlng;

    @ColumnInfo(name = "diet_memo")
    private String memo;

    @ColumnInfo(name = "diet_categories")
    private String categories;

    @ColumnInfo(name = "diet_quantitiy")
    private String quantity;

    @ColumnInfo(name = "diet_rating")
    private int rating;

    @ColumnInfo(name = "diet_date_day")
    private String day;

    @ColumnInfo(name = "diet_date_time")
    private String time;

    @ColumnInfo(name = "diet_date")
    private long date;


    public DietLog(String foodName, int foodCount, int foodCalorie, String imagePath
            , String address, String latlng, String memo, String categories
            , String quantity, int rating, String day, String time, long date) {
        this.foodName = foodName;
        this.foodCount = foodCount;
        this.foodCalorie = foodCalorie;
        this.imagePath = imagePath;
        this.address = address;
        this.latlng = latlng;
        this.memo = memo;
        this.categories = categories;
        this.quantity = quantity;
        this.rating = rating;
        this.day = day;
        this.time = time;
        this.date = date;
    }

    @Ignore
    public DietLog() {
    }

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
        notifyPropertyChanged(BR.foodName);
    }

    @Bindable
    public int getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
        notifyPropertyChanged(BR.foodCount);
    }

    @Bindable
    public int getFoodCalorie() {
        return foodCalorie;
    }

    public void setFoodCalorie(int foodCalorie) {
        this.foodCalorie = foodCalorie;
        notifyPropertyChanged(BR.foodCalorie);
    }

    @Bindable
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        notifyPropertyChanged(BR.imagePath);
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    @Bindable
    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
        notifyPropertyChanged(BR.latlng);
    }

    @Bindable
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
        notifyPropertyChanged(BR.memo);
    }

    @Bindable
    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
        notifyPropertyChanged(BR.categories);
    }

    @Bindable
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
        notifyPropertyChanged(BR.quantity);
    }

    @Bindable
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
        notifyPropertyChanged(BR.rating);
    }

    @Bindable
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
        notifyPropertyChanged(BR.day);
    }

    @Bindable
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
        notifyPropertyChanged(BR.time);
    }

    @Bindable
    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
    }
}

