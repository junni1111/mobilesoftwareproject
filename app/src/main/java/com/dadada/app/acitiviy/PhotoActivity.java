package com.dadada.app.acitiviy;

import static androidx.core.content.FileProvider.getUriForFile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dadada.app.R;
import com.dadada.app.parcelable.DietParcelable;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PhotoActivity extends AppCompatActivity {

    private static final int IMAGE_FROM_CAMERA = 11;
    private static final int IMAGE_FROM_GALLERY = 22;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int MY_GALLERY_PERMISSION_CODE = 101;
    private String currentPhotoPath = "";
    private boolean isPhotoSelected = false;
    private DietParcelable data;

    ImageView photoBtn, backBtn;
    Button takePhotoBtn, selectPhotoBtn, nextBtn;
    BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        data = getIntent().getParcelableExtra("data");
        Log.d("photo", data.getAddress());
        Log.d("photo", data.getLatlng());


        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.bottom_sheet_group_photo, null, false);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        photoBtn = findViewById(R.id.memoryImage);
        backBtn = findViewById(R.id.backBtn);
        nextBtn = findViewById(R.id.nextBtn);
        takePhotoBtn = bottomSheetDialog.findViewById(R.id.takePhotoBtn);
        selectPhotoBtn = bottomSheetDialog.findViewById(R.id.selectPhotoBtn);

        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.show();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.cancel();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                            checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_PERMISSION_CODE);
                    } else {
                        takePicture();
                    }
                }
            }
        });

        selectPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.cancel();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_GALLERY_PERMISSION_CODE);
                    } else {
                        selectPhoto();
                    }
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPhotoPath.equals("") || !isPhotoSelected) return;
                Intent searchIntent = new Intent(PhotoActivity.this, DietActivity.class);

                data.setImagePath(currentPhotoPath);
                searchIntent.putExtra("data", data);
                startActivity(searchIntent);
            }
        });

    }

    void takePicture() {
        String filename = "photo";
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File fileImage = File.createTempFile(filename, ".jpg", storageDirectory);

            currentPhotoPath = fileImage.getAbsolutePath();

            Uri uri = getUriForFile(PhotoActivity.this, "com.dadada.app.fileprovider", fileImage);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, IMAGE_FROM_CAMERA);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void selectPhoto() {
        String filename = "photo";
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File fileImage = File.createTempFile(filename, ".jpg", storageDirectory);

            currentPhotoPath = fileImage.getAbsolutePath();

            Uri uri = getUriForFile(PhotoActivity.this, "com.dadada.app.fileprovider", fileImage);

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, IMAGE_FROM_GALLERY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                takePicture();
            }
        }

        if (requestCode == MY_GALLERY_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectPhoto();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_FROM_CAMERA && resultCode == Activity.RESULT_OK) {
            try {
                File file = new File(currentPhotoPath);
                Log.d("file", currentPhotoPath);

                Glide.with(this).load(Uri.fromFile(file)).into(photoBtn);

                // 다음 버튼 색 조절
                setNextBtnSelectedMode();
                isPhotoSelected = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == IMAGE_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            try {
                // 선택한 이미지에서 비트맵 생성
                InputStream in = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                in.close();

                // 비트맵을 임시 파일에 저장
                File file = new File(currentPhotoPath);
                FileOutputStream output = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);

                // 이미지 표시
                Glide.with(this).load(Uri.fromFile(file)).into(photoBtn);

                // 다음 버튼 색 조절
                setNextBtnSelectedMode();
                isPhotoSelected = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void setNextBtnSelectedMode() {
        nextBtn.setBackground(this.getResources().getDrawable(R.drawable.r12_primary_solid));
    }
}