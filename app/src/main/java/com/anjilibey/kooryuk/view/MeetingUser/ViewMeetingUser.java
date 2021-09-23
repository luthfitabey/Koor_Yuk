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
import com.anjilibey.kooryuk.utils.BaseApiService;
import com.anjilibey.kooryuk.utils.SharedPrefManager;
import com.anjilibey.kooryuk.utils.UtilsApi;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewMeetingUser extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    BaseApiService mApiService;
    ProgressDialog loading;
    String token = null;
    String role = null;
    String id_rapat;
    TextView topik, tgl, waktuM, waktuS, tempat, nama, nip, jabatan, p1, p2, p3, p4, p5,lampiran;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meeting_user);
        topik = findViewById(R.id.viewTopik);
        tgl = findViewById(R.id.viewTgl);
        waktuM = findViewById(R.id.viewWaktuM);
        waktuS = findViewById(R.id.viewWaktuS);
        tempat = findViewById(R.id.viewTempat);
        nama = findViewById(R.id.viewNama);
        nip = findViewById(R.id.viewNip);
        jabatan = findViewById(R.id.viewJabatan);
        p1 = findViewById(R.id.viewPoin1);
        p2 = findViewById(R.id.viewPoin2);
        p3 = findViewById(R.id.viewPoin3);
        p4 = findViewById(R.id.viewPoin4);
        p5 = findViewById(R.id.viewPoin5);
        lampiran = findViewById(R.id.viewLampiran);
        toolbar();

        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(ViewMeetingUser.this);
        token = sharedPrefManager.getSpToken();
        role = sharedPrefManager.getSpRole();

        Intent mIntent = getIntent();
        id_rapat = mIntent.getStringExtra("id_rapat");
        Log.d("id rapat", id_rapat);
        token = sharedPrefManager.getSpToken();
        role = sharedPrefManager.getSpRole();
        getResultMeeting();

    }

    private void toolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarViewMeetingUser);
        setSupportActionBar(toolbar);
        TextView toolbarText = findViewById(R.id.toolbar_text_userMeeting);

        if(toolbarText!=null && toolbar!=null) {
            toolbarText.setText("Lihat Rapat");
            setSupportActionBar(toolbar);
        }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void getResultMeeting() {
        final ProgressDialog loading = ProgressDialog.show(ViewMeetingUser.this, "Fetching Data","Please wait..",false,false);
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
                                p1.setText(data.getPoin1());
                                p2.setText(data.getPoin2());
                                p3.setText(data.getPoin3());
                                p4.setText(data.getPoin4());
                                p5.setText(data.getPoin5());
                                lampiran.setText(data.getLampiran());
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
}