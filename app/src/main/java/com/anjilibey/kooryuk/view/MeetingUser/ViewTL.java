package com.anjilibey.kooryuk.view.MeetingUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.anjilibey.kooryuk.R;
import com.anjilibey.kooryuk.model.RapatId;
import com.anjilibey.kooryuk.model.RapatIds;
import com.anjilibey.kooryuk.model.TlId;
import com.anjilibey.kooryuk.model.TlIds;
import com.anjilibey.kooryuk.utils.BaseApiService;
import com.anjilibey.kooryuk.utils.SharedPrefManager;
import com.anjilibey.kooryuk.utils.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewTL extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    BaseApiService mApiService;
    ProgressDialog loading;
    String token = null;
    String role = null;
    String id_rapat;
    TextView topik, tgl, waktuM, waktuS, tempat, nama, nip, jabatan, p1, p2, p3, p4, p5,lampiran, tl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tl);
        topik = findViewById(R.id.tlTopik);
        tgl = findViewById(R.id.tlTgl);
        waktuM = findViewById(R.id.tlWaktuM);
        waktuS = findViewById(R.id.tlWaktuS);
        tempat = findViewById(R.id.tlTempat);
        nama = findViewById(R.id.tlNama);
        nip = findViewById(R.id.tlNip);
        jabatan = findViewById(R.id.tlJabatan);
        p1 = findViewById(R.id.tlPoin1);
        p2 = findViewById(R.id.tlPoin2);
        p3 = findViewById(R.id.tlPoin3);
        p4 = findViewById(R.id.tlPoin4);
        p5 = findViewById(R.id.tlPoin5);
        lampiran = findViewById(R.id.tlLampiran);
        tl = findViewById(R.id.tlKaban);
        toolbar();

        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(ViewTL.this);
        token = sharedPrefManager.getSpToken();
        role = sharedPrefManager.getSpRole();

        Intent mIntent = getIntent();
        id_rapat = mIntent.getStringExtra("id_rapat");
        Log.d("id rapat", id_rapat);
        token = sharedPrefManager.getSpToken();
        role = sharedPrefManager.getSpRole();
        getTlMeeting();
    }

    private void getTlMeeting() {
        final ProgressDialog loading = ProgressDialog.show(ViewTL.this, "Fetching Data","Please wait..",false,false);
        mApiService.tlRequestShow(
                "application/json",
                "Bearer "+token,
                id_rapat).enqueue(
                new Callback<TlIds>() {
                    @Override
                    public void onResponse(Call<TlIds> call, Response<TlIds> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            List<TlId> tlId = response.body().getTl();
                            for (TlId data : tlId) {
                                topik.setText(data.getTopik());
                                tgl.setText(data.getTanggal());
                                waktuM.setText(data.getWaktu_mulai());
                                waktuS.setText(data.getWaktu_selesai());
                                tempat.setText(data.getTempat());
                                nama.setText(data.getNama());
                                nip.setText(data.getNip());
                                jabatan.setText(data.getJabatan());
                                p1.setText(data.getPoin1());
                                p2.setText(data.getPoin2());
                                p3.setText(data.getPoin3());
                                p4.setText(data.getPoin4());
                                p5.setText(data.getPoin5());
                                lampiran.setText(data.getLampiran());
                                tl.setText(data.getTl_kaban());
                            }
                        }
                        else {
                            loading.dismiss();
                            Log.d("hasilnya: ", "gagal");
                        }
                    }

                    @Override
                    public void onFailure(Call<TlIds> call, Throwable t) {
                        loading.dismiss();
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                    }
                }
        );
    }

    private void toolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarViewTlUser);
        setSupportActionBar(toolbar);
        TextView toolbarText = findViewById(R.id.toolbar_text_tlMeeting);

        if(toolbarText!=null && toolbar!=null) {
            toolbarText.setText("Lihat Tindak Lanjut");
            setSupportActionBar(toolbar);
        }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(view -> finish());
    }
}