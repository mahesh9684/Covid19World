package com.info.covid19.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.info.covid19.CountryDataAdapter;
import com.info.covid19.R;
import com.info.covid19.apis.RetrofitClient;
import com.info.covid19.utils.DialogUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView confirmedCases, recoveredCases, deceasedCases, confirmedDailyCases, recoveredDailyCases, deceasedDailyCases;
    RecyclerView showStateData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        getData();
    }

    void initComponents() {
        confirmedCases = findViewById(R.id.confirmedCases);
        recoveredCases = findViewById(R.id.recoveredCases);
        deceasedCases = findViewById(R.id.deceasedCases);
        confirmedDailyCases = findViewById(R.id.confirmedDailyCases);
        recoveredDailyCases = findViewById(R.id.recoveredDailyCases);
        deceasedDailyCases = findViewById(R.id.deceasedDailyCases);
        showStateData = findViewById(R.id.showStateData);
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

                    LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
                    showStateData.setLayoutManager(manager);
                    CountryDataAdapter adapter = new CountryDataAdapter(MainActivity.this, array);
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
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
