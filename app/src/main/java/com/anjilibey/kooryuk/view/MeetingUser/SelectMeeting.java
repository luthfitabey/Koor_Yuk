package com.anjilibey.kooryuk.view.MeetingUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.anjilibey.kooryuk.R;
import com.anjilibey.kooryuk.model.Rapat;
import com.anjilibey.kooryuk.utils.BaseApiService;
import com.anjilibey.kooryuk.utils.SharedPrefManager;
import com.anjilibey.kooryuk.utils.UtilsApi;
import com.anjilibey.kooryuk.viewModel.RapatAdapter;
import com.anjilibey.kooryuk.viewModel.RapatAdapterUser;
import com.anjilibey.kooryuk.viewModel.RapatList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectMeeting extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    BaseApiService mApiService;
    ProgressDialog loading;
    String token = null;
    String role = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meeting);
        toolbar();
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(SelectMeeting.this);
        token = sharedPrefManager.getSpToken();
        role = sharedPrefManager.getSpRole();
        getResultMeeting();
    }

    private void getResultMeeting() {
        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
        mApiService.viewRapatRequest(
                "application/json",
                "Bearer "+token,
                "application/json"
        ).enqueue(new Callback<RapatList>() {
            @Override
            public void onResponse(Call<RapatList> call, Response<RapatList> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    generateRapatList(response.body().getRapatArrayList());
                } else {
                    loading.dismiss();
                    Toast.makeText(SelectMeeting.this, "Data Rapat kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RapatList> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(SelectMeeting.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateRapatList(ArrayList<Rapat> empDataList) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_rapat);
        RapatAdapterUser adapter = new RapatAdapterUser(empDataList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SelectMeeting.this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }

    private void toolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarText = findViewById(R.id.toolbar_text);

        if(toolbarText!=null && toolbar!=null) {
            toolbarText.setText("Pilih Rapat");
            setSupportActionBar(toolbar);
        }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(view -> finish());
    }
}