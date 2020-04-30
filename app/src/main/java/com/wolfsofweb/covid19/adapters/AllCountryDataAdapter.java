package com.wolfsofweb.covid19.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.wolfsofweb.covid19.R;
import com.squareup.picasso.Picasso;

public class AllCountryDataAdapter extends RecyclerView.Adapter<AllCountryDataAdapter.MyViewHolder> {
    Context context;
    JsonArray array;
    int size = 0;

    public AllCountryDataAdapter(Context context, JsonArray array, int size) {

        this.context = context;
        this.array = array;
        this.size = size;
    }

    @NonNull
    @Override
    public AllCountryDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_recycler_data, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCountryDataAdapter.MyViewHolder holder, int position) {

        JsonObject object = array.get(position).getAsJsonObject();

        holder.locationName.setText(object.get("country_name").toString().replace("\"", ""));
        holder.locationConfirmed.setText(object.get("total_cases").toString().replace("\"", ""));
        holder.locationRecovered.setText(object.get("total_recovered").toString().replace("\"", ""));
        holder.locationDeceased.setText(object.get("total_deaths").toString().replace("\"", ""));
//        holder.flag.setVisibility(View.GONE);

        /*holder.layout.setOnClickListener(v -> {

            if (object.get("country_name").toString().replace("\"", "").equalsIgnoreCase("USA")) {
                context.startActivity(new Intent(context, StateActivity.class));
            }
        });*/

        try {
            Picasso.get()
                    .load(object.get("country_flag").toString().replace("\"", ""))
//                    .resize(30, 50)
//                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.flag);
        } catch (Exception e) {
            System.out.println("Exception inside adapter " + e);
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView locationName, locationConfirmed, locationRecovered, locationDeceased;
        ImageView flag;
        LinearLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            locationName = itemView.findViewById(R.id.locationName);
            locationConfirmed = itemView.findViewById(R.id.locationConfirmed);
            locationRecovered = itemView.findViewById(R.id.locationRecovered);
            locationDeceased = itemView.findViewById(R.id.locationDeceased);
            layout = itemView.findViewById(R.id.layout);
            flag = itemView.findViewById(R.id.flag);

        }
    }
}
