package com.info.covid19.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.info.covid19.R;
import com.info.covid19.adapters.AllCountryDataAdapter;
import com.info.covid19.adapters.CountryDataAdapter;
import com.info.covid19.apis.RetrofitClient;
import com.info.covid19.utils.DialogUtils;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StateActivity extends AppCompatActivity {

    TextView confirmedCases, recoveredCases, deceasedCases, confirmedDailyCases, recoveredDailyCases, deceasedDailyCases, countryName, tvTitle;
    RecyclerView showStateData;
    ImageView backState, flagCountry;
    SwipeRefreshLayout refreshData;
    String flag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);
        initComponents();
        flag = getIntent().getStringExtra("flag");
        getData();

        if (!flag.equalsIgnoreCase("")) {
            try {
                Picasso.get()
                        .load(flag)
//                    .resize(30, 50)
//                    .centerCrop()
//                    .placeholder(R.mipmap.ic_launcher)
                        .into(flagCountry);
            } catch (Exception e) {
                System.out.println("Exception inside adapter " + e);
            }
        }
        refreshData.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                refreshData.setRefreshing(false);
            }
        });
        backState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void initComponents() {
        confirmedCases = findViewById(R.id.confirmedCases);
        recoveredCases = findViewById(R.id.recoveredCases);
        deceasedCases = findViewById(R.id.deceasedCases);
        confirmedDailyCases = findViewById(R.id.confirmedDailyCases);
        recoveredDailyCases = findViewById(R.id.recoveredDailyCases);
        deceasedDailyCases = findViewById(R.id.deceasedDailyCases);
        showStateData = findViewById(R.id.showStateData);
        refreshData = findViewById(R.id.refreshData);
        countryName = findViewById(R.id.countryName);
        backState = findViewById(R.id.backState);
        tvTitle = findViewById(R.id.tvTitle);
        flagCountry = findViewById(R.id.flagCountry);
    }

    void getData() {

        final Dialog dialog = DialogUtils.getLoadingDialog(StateActivity.this);
        dialog.show();

        Call<JsonObject> result = RetrofitClient.getClient().getStateData();
        result.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JsonObject model = response.body().getAsJsonObject();
                    tvTitle.setText(model.get("state").getAsString());
                    countryName.setText("Across " + model.get("state").getAsString());
                    confirmedCases.setText(model.get("total_cases").getAsString());
                    confirmedDailyCases.setText(model.get("new_cases").getAsString());
                    deceasedCases.setText(model.get("total_deaths").getAsString());
                    deceasedDailyCases.setText(model.get("new_deaths").getAsString());
                    recoveredCases.setText(model.get("active_cases").getAsString());
//                    recoveredCases.setText(model.get("total_recovered").getAsString());

                    JsonArray array = response.body().getAsJsonObject().getAsJsonArray("states");

                    LinearLayoutManager manager = new LinearLayoutManager(StateActivity.this);
                    showStateData.setLayoutManager(manager);
                    CountryDataAdapter adapter = new CountryDataAdapter(StateActivity.this, array);
                    showStateData.setAdapter(adapter);
                    dialog.dismiss();
                } catch (Exception e) {
                    dialog.dismiss();
                    System.out.println("Exception is " + e);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dialog.dismiss();
                System.out.println("error");
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
