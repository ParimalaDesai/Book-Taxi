package com.example.taxibooking.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.taxibooking.R;
import com.example.taxibooking.adapter.VehicleBookAdapter;
import com.example.taxibooking.client.AppController;
import com.example.taxibooking.model.VehicleModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<VehicleModel> vehicleModelList = new ArrayList<>();
    private RecyclerView rcv_taxi;

    private String tag_json_obj = "json_obj_req";

    private int employee = 10;
    private int gates = 10;
    String[] strAr1=new String[] {"C", "C", "C", "C", "C", "C", "C", "C", "C", "C"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        rcv_taxi = findViewById(R.id.rcv_taxi);
        ImageView imageView_back = findViewById(R.id.imageView_back);
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });

        printthePattern();

        /*requestLocationPermission();

        fetchInfo();*/

    }

    private void printthePattern() {
        for(int i=0;i<employee;i++){
            switch(gates){
                //Case statements
                case 1: if(strAr1[i].equals("C")){

                }else {

                }
                    System.out.println("10");
                    break;
                case 2: System.out.println("20");
                    break;
                case 3: System.out.println("30");
                    break;
                //Default case statement
                default:System.out.println("Not in 10, 20 or 30");
            }
        }
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    public void fetchInfo(){
        vehicleModelList.clear();
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, "https://fake-poi-api.mytaxi.com/?p1Lat=53.694865&p1Lon=9.757589&p2Lat=53.394655&p2Lon=10.099891", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("poiList");
                            for(int i=0; i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                JSONObject jsonObject1 = jsonObject.getJSONObject("coordinate");
                                VehicleModel vehicleModel = new VehicleModel();
                                vehicleModel.setVehicle_Id(jsonObject.getInt("id"));
                                vehicleModel.setVehicle_Name(jsonObject.getString("fleetType"));
                                vehicleModel.setVehicle_Time(jsonObject.getDouble("heading"));

                                vehicleModel.setVehicle_Lat(jsonObject1.getDouble("latitude"));
                                vehicleModel.setVehicle_Longi(jsonObject1.getDouble("longitude"));
                                vehicleModelList.add(vehicleModel);
                            }
                            setAdapter();
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
        );
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    private void setAdapter() {
        VehicleBookAdapter adapter = new VehicleBookAdapter(MainActivity.this, vehicleModelList);
        rcv_taxi.setHasFixedSize(true);
        rcv_taxi.setLayoutManager(new LinearLayoutManager(this));
        rcv_taxi.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

}