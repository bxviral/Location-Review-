package com.example.locationreviewproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<AddressData> addressData;

    public RecyclerViewAdapter(Context context, ArrayList<AddressData> addressData) {
        this.context = context;
        this.addressData = addressData;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        AddressData r = addressData.get(position);
        String latitude = String.valueOf(r.getLatitude());
        String longitude = String.valueOf(r.getLongitude());
        holder.txtLatitude.setText(latitude);
        holder.txtLongitude.setText(longitude);
        holder.txtAddress.setText(r.getAddress());

        if(r.getDescription() == null){
            Log.e("QQQ", "onBindViewHolder: is null " );
        }
        else{
            holder.txtDescription.setText("Description is added");
        }
        if(r.getPath() == null){
            Log.e("QQQ", "onBindViewHolder: is null " );
        }
        else{
            String p = r.getPath();
            File file = new File(p);
            Uri imageUri = Uri.fromFile(file);
            Glide.with(context).load(imageUri).into(holder.imgView1);

        }
        holder.crdCardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,FillingActivity.class);
                intent.putExtra("obj",r);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return addressData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtLatitude, txtLongitude, txtAddress,txtDescription;
        ImageView imgView1;
        CardView crdCardView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLatitude = itemView.findViewById(R.id.txtLatitude);
            txtLongitude = itemView.findViewById(R.id.txtLongitude);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            imgView1 = itemView.findViewById(R.id.imgView1);
            crdCardView2 = itemView.findViewById(R.id.crdCardView2);
            txtDescription = itemView.findViewById(R.id.txtDescription);
        }
    }
}
