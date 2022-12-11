package com.dadada.app.acitiviy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.dadada.app.R;
import com.dadada.app.model.DietLog;
import com.dadada.app.parcelable.DietParcelable;
import com.dadada.app.viewmodel.MainActivityViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {
    private final int LOCATION_ACCESS_CODE = 1234;
    Button dietBtn, dietBtn2;
    private GoogleMap mMap = null;
    private Marker currentMarker = null;
    private boolean test = false;
    private LocationManager lm = null;
    private LatLng selectedLocation = null;
    public static MainActivityViewModel mainActivityViewModel;
    private MarkerOptions pointedMarker = null;
    private String pointedAddress = "";
    private String pointedLatlng = "";

    private Button nextBtn;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_new);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        LOCATION_ACCESS_CODE);
            }
            return;
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        assert mapFragment != null;
        mapFragment.getMapAsync(MapActivity.this);

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
                if (pointedLatlng == "") return;

                DietParcelable data = new DietParcelable("", "", 0, 0, ""
                        , "", "", "", "", 0, pointedAddress, pointedLatlng);

                Intent mapIntent = new Intent(MapActivity.this, PhotoActivity.class);

                mapIntent.putExtra("data", data);
                startActivity(mapIntent);
            }
        });

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        mainActivityViewModel.getAllDietLogs().observe(this, dietLogs -> {
            for (DietLog dietLog : dietLogs) {
                Log.d("dietLog name", dietLog.getFoodName());
                Log.d("dietLog food count", "" + dietLog.getFoodCount());
                Log.d("dietLog food calorie", "" + dietLog.getFoodCalorie());
                Log.d("dietLog image path", dietLog.getImagePath());
                Log.d("dietLog address", dietLog.getAddress());
                Log.d("dietLog day", dietLog.getDay());
                Log.d("dietLog time", dietLog.getTime());
                Log.d("dietLog date", "" + dietLog.getDate());
            }
        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        mMap = map;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        LOCATION_ACCESS_CODE);
            }
            return;
        }

        map.setMyLocationEnabled(true);
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(getCurrentLatLng(), 18));

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                Toast.makeText(MapActivity.this, "" + getCurrentAddress(point),
                        Toast.LENGTH_SHORT).show();
                pointedAddress = getCurrentAddress(point);
                if (pointedAddress == "") {
                    return;
                }
                if (pointedMarker != null) {
                    map.clear();
                }

                pointedMarker = new MarkerOptions();
                pointedMarker
                        .position(new LatLng(point.latitude, point.longitude));
                map.addMarker(pointedMarker);

                pointedLatlng = point.latitude + "/" + point.longitude;
                setNextBtnSelectedMode();
            }
        });
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
//        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG)
//                .show();

        LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
        Toast.makeText(this, "" + getCurrentAddress(currentPosition), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    public String getCurrentAddress(LatLng latlng) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;
        try {

            addresses = geocoder.getFromLocation(
                    latlng.latitude,
                    latlng.longitude,
                    1);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            //return "지오코더 서비스 사용불가";
            return "";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
//            return "잘못된 GPS 좌표";
            return "";
        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
//            return "주소 미발견";
            return "";

        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0).toString();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_ACCESS_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Intent i = new Intent(MapActivity.this, MapActivity.class);
                startActivity(i);
                finish();
            }
        }
    }

    public LatLng getCurrentLatLng() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        if (location == null) {
//            return new LatLng(37.5666805, 126.9784147);
//        }
        return new LatLng(location.getLatitude(), location.getLongitude());
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