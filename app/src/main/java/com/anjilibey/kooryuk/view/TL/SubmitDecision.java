package com.anjilibey.kooryuk.view.TL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anjilibey.kooryuk.R;
import com.anjilibey.kooryuk.model.RapatId;
import com.anjilibey.kooryuk.model.RapatIds;
import com.anjilibey.kooryuk.utils.BaseApiService;
import com.anjilibey.kooryuk.utils.SharedPrefManager;
import com.anjilibey.kooryuk.utils.UtilsApi;
import com.anjilibey.kooryuk.view.Dashboard.HomeAdminActivity;
import com.anjilibey.kooryuk.view.Dashboard.HomeKabanActivity;
import com.anjilibey.kooryuk.view.Dashboard.HomeUserActivity;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitDecision extends AppCompatActivity {
    String id_rapat;
    ProgressDialog loading;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;
    String token, role;
    EditText TL;
    TextView topik, tgl, waktuM, waktuS, tempat, nama, nip, jabatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_decision);
        TL = findViewById(R.id.decTL);
        topik = findViewById(R.id.decTopik);
        tgl = findViewById(R.id.decTgl);
        waktuM = findViewById(R.id.decWaktuM);
        waktuS = findViewById(R.id.decWaktuS);
        tempat = findViewById(R.id.decTempat);
        nama = findViewById(R.id.decNama);
        nip = findViewById(R.id.decNip);
        jabatan = findViewById(R.id.decJabatan);
        toolbar();
        mApiService = UtilsApi.getAPIService();
        token = sharedPrefManager.getSpToken();
        Log.d("submit token", token);
        role = sharedPrefManager.getSpRole();

        Intent mIntent = getIntent();
        id_rapat = mIntent.getStringExtra("id_rapat");
        Log.d("id rapat", id_rapat);
        getRapat();
    }

    private void getRapat() {
        final ProgressDialog loading = ProgressDialog.show(SubmitDecision.this, "Fetching Data","Please wait..",false,false);
        mApiService.viewRapatIdRequest(
                "application/json",
                "Bearer "+token,
                id_rapat).enqueue(
                new Callback<RapatIds>() {
                    @Override
                    public void onResponse(Call<RapatIds> call, Response<RapatIds> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            List<RapatId> rapatId = response.body().getRapat();
                            for (RapatId data : rapatId) {
                                Log.d("id haha: ", data.getId());
                                topik.setText(data.getTopik());
                                tgl.setText(data.getTanggal());
                                waktuM.setText(data.getWaktu_mulai());
                                waktuS.setText(data.getWaktu_selesai());
                                tempat.setText(data.getTempat());
                                nama.setText(data.getNama());
                                nip.setText(data.getNip());
                                jabatan.setText(data.getJabatan());
                            }
                        }
                        else {
                            loading.dismiss();
                            Log.d("hasilnya: ", "gagal");
                        }
                    }

                    @Override
                    public void onFailure(Call<RapatIds> call, Throwable t) {
                        loading.dismiss();
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                    }
                }
        );
    }

    private void toolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarText = findViewById(R.id.toolbar_text);

        if (toolbarText != null && toolbar != null) {
            toolbarText.setText("Tindak Lanjut Rapat");
            setSupportActionBar(toolbar);
        }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(view -> finish());
    }


    public void btnSubmit(View view) {
        String tl = TL.getText().toString();
        Log.d("token ubah", token);

        if (tl.isEmpty()) {
            TL.setError("Harap masukkan Tindak Lanjut");
            TL.requestFocus();
        }
        else {
                loading = ProgressDialog.show(SubmitDecision.this, null, "Harap Tunggu...", true, false);
                mApiService.rapatRequest(
                        "application/json",
                        "Bearer " + token,
                        id_rapat,
                        TL.getText().toString()
                ).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            Toast.makeText(SubmitDecision.this, "Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                            validasi();
                        } else {
                            loading.dismiss();
                            Toast.makeText(SubmitDecision.this, "Maaf data anda salah", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
            }
        }

    private void validasi() {
        if (role.equals("1")){
            startActivity(new Intent(SubmitDecision.this, HomeAdminActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        else if (role.equals("3")){
            startActivity(new Intent(SubmitDecision.this, HomeKabanActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        else if (role.equals("2")){
            startActivity(new Intent(SubmitDecision.this, HomeUserActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }
}