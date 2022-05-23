package com.example.taxibooking.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxibooking.R;
import com.example.taxibooking.activity.MapsActivity;
import com.example.taxibooking.model.VehicleModel;

import java.io.Serializable;
import java.util.List;

public class VehicleBookAdapter extends RecyclerView.Adapter<VehicleBookAdapter.ViewHolder>{
    private List<VehicleModel> vehicleModelList;
    private Context mContext;

    public VehicleBookAdapter(Context c1, List<VehicleModel> vehicleModels) {
        this.mContext = c1;
        this.vehicleModelList = vehicleModels;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.itemview_vehicle_search, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final VehicleModel vehicleModel = vehicleModelList.get(position);

        holder.textView_vehicle_name.setText(vehicleModel.getVehicle_Name());
        if(vehicleModel.getVehicle_Name().equals("TAXI")){
            holder.img_vehicle_type.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.new_black_car));
        }else if(vehicleModel.getVehicle_Name().equals("POOLING")){
            holder.img_vehicle_type.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.new_taxi_pool));
        }

        double conv = (vehicleModel.getVehicle_Time()/60);
        String time = String.format("%.2f", conv);

        holder.textView_vehicle_arrive.setText(time+":"+"Min");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MapsActivity.class);
                intent.putExtra("LIST", (Serializable) vehicleModelList);
                intent.putExtra("Lat", vehicleModel.getVehicle_Lat());
                intent.putExtra("Lang", vehicleModel.getVehicle_Longi());
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return vehicleModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_vehicle_type;
        public TextView textView_vehicle_name;
        public TextView textView_vehicle_arrive;
        public ViewHolder(View itemView) {
            super(itemView);
            this.img_vehicle_type = (ImageView) itemView.findViewById(R.id.img_vehicle_type);
            this.textView_vehicle_name = (TextView) itemView.findViewById(R.id.textView_vehicle_name);
            this.textView_vehicle_arrive = (TextView) itemView.findViewById(R.id.textView_vehicle_arrive);
        }
    }
}