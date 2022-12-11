package com.dadada.app.acitiviy;

import static androidx.core.content.FileProvider.getUriForFile;

import android.Manifest;
import android.app.Activity;
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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.dadada.app.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class CameraActivity extends AppCompatActivity {

    private static final int IMAGE_FROM_CAMERA = 11;
    private static final int IMAGE_FROM_GALLERY = 22;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int MY_GALLERY_PERMISSION_CODE = 101;
    private ImageView imageView;
    private String currentPhotoPath;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        imageView = (ImageView) findViewById(R.id.imageView1);
        Button photoButton = (Button) findViewById(R.id.button1);
        Button galleryButton = (Button) findViewById(R.id.button2);
        Button addDiet = (Button) findViewById(R.id.button3);

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_PERMISSION_CODE);
                    } else {
                        takePicture();
                    }
                }
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_GALLERY_PERMISSION_CODE);
                    } else {
                        getPictureFromGallery();
                    }
                }
            }
        });

        addDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPicture();
            }
        });
    }

    private void addPicture() {
        Log.d("picture", currentPhotoPath);
        Intent i = new Intent(CameraActivity.this, MainActivity.class);
        i.putExtra("imgPath", currentPhotoPath);

//        MainActivity.mainActivityViewModel.addNewDietLog(new DietLog("김밥", 1, 123, currentPhotoPath, "서울특별시", "2020-03-01", "09:00:00"));

        startActivity(i);
        finish();
    }

    private void takePicture() {
        String filename = "photo";
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File fileImage = File.createTempFile(filename, ".jpg", storageDirectory);

            currentPhotoPath = fileImage.getAbsolutePath();

            Uri uri = getUriForFile(CameraActivity.this, "com.dadada.app.fileprovider", fileImage);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, IMAGE_FROM_CAMERA);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getPictureFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_FROM_GALLERY);
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
                getPictureFromGallery();
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

                Glide.with(this).load(Uri.fromFile(file)).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == IMAGE_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            try {
                // 선택한 이미지에서 비트맵 생성
                InputStream in = getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();

                // 이미지 표시
                Glide.with(this).load(data.getData()).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}