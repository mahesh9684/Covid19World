package com.info.covid19.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.info.covid19.R;
import com.squareup.picasso.Picasso;

public class CountryDataAdapter extends RecyclerView.Adapter<CountryDataAdapter.MyViewHolder> {
    Context context;
    JsonArray array;

    public CountryDataAdapter(Context context, JsonArray array) {

        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public CountryDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_recycler_data, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryDataAdapter.MyViewHolder holder, int position) {

        JsonObject object = array.get(position).getAsJsonObject();

        holder.locationName.setText(object.get("state_name").toString().replace("\"", ""));
        holder.locationName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        holder.locationConfirmed.setText(object.get("total_cases").toString().replace("\"", ""));
        holder.locationRecovered.setText(object.get("active_cases").toString().replace("\"", ""));
//        holder.locationRecovered.setText(object.get("total_recovered").toString().replace("\"", ""));
        holder.locationDeceased.setText(object.get("total_deaths").toString().replace("\"", ""));

        holder.flag.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView locationName, locationConfirmed, locationRecovered, locationDeceased;
        ImageView flag;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            locationName = itemView.findViewById(R.id.locationName);
            locationConfirmed = itemView.findViewById(R.id.locationConfirmed);
            locationRecovered = itemView.findViewById(R.id.locationRecovered);
            locationDeceased = itemView.findViewById(R.id.locationDeceased);
            flag = itemView.findViewById(R.id.flag);

        }
    }
}
