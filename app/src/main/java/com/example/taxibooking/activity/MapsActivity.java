package com.example.taxibooking.activity;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import com.example.taxibooking.R;
import com.example.taxibooking.model.VehicleModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.taxibooking.databinding.ActivityMapsBinding;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private List<VehicleModel> vehicleModelList;
    private ArrayList<LatLng> locationArrayList = new ArrayList<>();
    private double booklat;
    private double booklang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent i = getIntent();
        vehicleModelList = (List<VehicleModel>) i.getSerializableExtra("LIST");
        booklat = i.getExtras().getDouble("Lat");
        booklang = i.getExtras().getDouble("Lang");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        for (int x=0;x<vehicleModelList.size();x++){
            LatLng sydney = new LatLng(vehicleModelList.get(x).getVehicle_Lat(), vehicleModelList.get(x).getVehicle_Longi());
            locationArrayList.add(sydney);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (int i = 0; i < locationArrayList.size(); i++) {
            // below line is use to add marker to each location of our array list.
            if(vehicleModelList.get(i).getVehicle_Lat()==booklat && vehicleModelList.get(i).getVehicle_Longi()==booklang){
                int finalI = i;
                double conv = (vehicleModelList.get(i).getVehicle_Time()/60);
                String time = String.format("%.2f", conv);
                mMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)).title(vehicleModelList.get(i).getVehicle_Name()+" arrive in "+time+":min"));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final GroundOverlay groundOverlay1=googleMap.addGroundOverlay(new GroundOverlayOptions()
                                .position(locationArrayList.get(finalI), 3000)
                                .transparency(0.1f)
                                .image(BitmapDescriptorFactory.fromResource(R.drawable.locate_icon)));
                        OverLay(groundOverlay1);
                    }
                }, 2000);
            }else {
                double conv = (vehicleModelList.get(i).getVehicle_Time()/60);
                String time = String.format("%.2f", conv);
                mMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)).title(vehicleModelList.get(i).getVehicle_Name()+" arrive in "+time+":min")
                        // below line is use to add custom marker on our map.
                        .icon(BitmapFromVector(getApplicationContext(), R.drawable.black_car_24x)));
            }

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(vehicleModelList.get(i).getVehicle_Lat(), vehicleModelList.get(i).getVehicle_Longi()), 10));
        }
    }

    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public void OverLay(final GroundOverlay groundOverlay){
        ValueAnimator vAnimator = ValueAnimator.ofInt(0, 2000);
        int r=9999;
        vAnimator.setRepeatCount(r);
        vAnimator.setDuration(2000);
        vAnimator.setEvaluator(new IntEvaluator());
        vAnimator.setInterpolator(new LinearInterpolator());
        vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = valueAnimator.getAnimatedFraction();
                Integer i = (Integer) valueAnimator.getAnimatedValue();
                groundOverlay.setDimensions(i);
            }
        });
        vAnimator.start();
    }

}