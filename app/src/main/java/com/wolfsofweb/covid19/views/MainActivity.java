package com.wolfsofweb.covid19.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.wolfsofweb.covid19.adapters.AllCountryDataAdapter;
import com.wolfsofweb.covid19.R;
import com.wolfsofweb.covid19.adapters.TopAffectedDataAdapter;
import com.wolfsofweb.covid19.apis.RetrofitClient;
import com.wolfsofweb.covid19.utils.DialogUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView confirmedCases, recoveredCases, deceasedCases, confirmedDailyCases, recoveredDailyCases, deceasedDailyCases;
    RecyclerView showStateData, showTopAffectedCountries;
    SwipeRefreshLayout refreshData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        getData();

        refreshData.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                refreshData.setRefreshing(false);
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
        showTopAffectedCountries = findViewById(R.id.showTopAffectedCountries);
    }


    void getData() {

        final Dialog dialog = DialogUtils.getLoadingDialog(MainActivity.this);
        dialog.show();

        Call<JsonObject> result = RetrofitClient.getClient().getCovidData();
        result.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JsonObject model = response.body().getAsJsonObject();
                    confirmedCases.setText(model.get("total_cases").getAsString());
                    confirmedDailyCases.setText(model.get("new_cases").getAsString());
                    deceasedCases.setText(model.get("total_deaths").getAsString());
                    deceasedDailyCases.setText(model.get("new_deaths").getAsString());
                    recoveredCases.setText(model.get("total_recovered").getAsString());

                    JsonArray array = response.body().getAsJsonObject().getAsJsonArray("countries");

                    LinearLayoutManager top = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
//                    top.setOrientation(RecyclerView.HORIZONTAL);
                    showTopAffectedCountries.setLayoutManager(top);
                    TopAffectedDataAdapter adapters = new TopAffectedDataAdapter(MainActivity.this, array, 10);
                    showTopAffectedCountries.setAdapter(adapters);
                    showTopAffectedCountries.setVisibility(View.VISIBLE);

                    LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
                    showStateData.setLayoutManager(manager);
                    AllCountryDataAdapter adapter = new AllCountryDataAdapter(MainActivity.this, array, array.size());
                    showStateData.setAdapter(adapter);
                    showStateData.setVisibility(View.VISIBLE);
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
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
