package com.anjilibey.kooryuk.view.MeetingUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.anjilibey.kooryuk.R;
import com.anjilibey.kooryuk.utils.BaseApiService;
import com.anjilibey.kooryuk.utils.SharedPrefManager;
import com.anjilibey.kooryuk.utils.UtilsApi;
import com.anjilibey.kooryuk.view.Dashboard.HomeAdminActivity;
import com.anjilibey.kooryuk.view.Dashboard.HomeKabanActivity;
import com.anjilibey.kooryuk.view.Dashboard.HomeUserActivity;

import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMeeting extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    BaseApiService mApiService;
    ProgressDialog loading;
    DatePickerDialog picker;
    TimePickerDialog pickerTime;
    EditText tanggal, waktuM, waktuS, topik, tempat, poin1, poin2, poin3, poin4, poin5, lampiran;
    String token = null;
    String role = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapat);
        topik = findViewById(R.id.form_topic);
        tanggal = findViewById(R.id.date);
        waktuM= findViewById(R.id.time);
        waktuS= findViewById(R.id.time2);
        tempat = findViewById(R.id.form_place);
        poin1=findViewById(R.id.point1);
        poin2=findViewById(R.id.point2);
        poin3=findViewById(R.id.point3);
        poin4=findViewById(R.id.point4);
        poin5=findViewById(R.id.point5);
        lampiran=findViewById(R.id.lampiran);

        toolbar();
        getDate();
        getTime();
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(AddMeeting.this);
        token = sharedPrefManager.getSpToken();
        role = sharedPrefManager.getSpRole();
    }
    private void toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarText = (TextView)
                findViewById(R.id.toolbar_text);

        if(toolbarText!=null && toolbar!=null) {
            toolbarText.setText("Tambah Rapat");
            setSupportActionBar(toolbar);
        }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void getTime() {
        waktuM.setInputType(InputType.TYPE_NULL);
        waktuS.setInputType(InputType.TYPE_NULL);
        waktuM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                pickerTime = new TimePickerDialog(AddMeeting.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                waktuM.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                pickerTime.show();
            }
        });
        waktuS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                pickerTime = new TimePickerDialog(AddMeeting.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                waktuS.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                pickerTime.show();
            }
        });
    }


    private void getDate() {
        tanggal.setInputType(InputType.TYPE_NULL);
        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddMeeting.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tanggal.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }

    public void btnSubmit(View view) {
        String a = topik.getText().toString().trim();
        String b = tanggal.getText().toString().trim();
        String c = waktuM.getText().toString().trim();
        String d = waktuS.getText().toString().trim();
        String e = poin1.getText().toString().trim();
        String f = lampiran.getText().toString().trim();
        String poinb = poin2.getText().toString();
        String poinc = poin3.getText().toString();
        String poind = poin4.getText().toString();
        String poine = poin5.getText().toString();
        Log.d("token ubah", token);

        if (a.isEmpty()) {
            topik.setError("Harap masukkan Topik");
            topik.requestFocus();
        }
        else if (b.isEmpty()) {
            tanggal.setError("Harap masukkan tanggal");
            tanggal.requestFocus();
        }
        else if (c.isEmpty()) {
            waktuM.setError("Harap masukkan waktu mulai");
            waktuM.requestFocus();
        }
        else if (d.isEmpty()) {
            waktuS.setError("Harap masukkan waktu selesai");
            waktuS.requestFocus();
        }
        else if (e.isEmpty()) {
            poin1.setError("Harap masukkan poin rapat");
            poin1.requestFocus();
        }
        else if (f.isEmpty()) {
            poin1.setError("Harap masukkan lampiran rapat");
            poin1.requestFocus();
        }
        else {
            loading = ProgressDialog.show(AddMeeting.this, null, "Harap Tunggu...", true, false);
            mApiService.rapatRequest(
                    "application/json",
                    "Bearer "+token,
                    topik.getText().toString(),
                    tanggal.getText().toString(),
                    waktuM.getText().toString(),
                    waktuS.getText().toString(),
                    tempat.getText().toString(),
                    poin1.getText().toString(),
                    poinb,
                    poinc,
                    poind,
                    poine,
                    lampiran.getText().toString()
            ).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        loading.dismiss();
                        Toast.makeText(AddMeeting.this, "Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                        validasi();
                    } else {
                        loading.dismiss();
                        Toast.makeText(AddMeeting.this, "Maaf data anda salah", Toast.LENGTH_SHORT).show();
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
    private void validasi(){
        if (role.equals("1")){
            startActivity(new Intent(AddMeeting.this, HomeAdminActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        else if (role.equals("3")){
            startActivity(new Intent(AddMeeting.this, HomeKabanActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        else if (role.equals("2")){
            startActivity(new Intent(AddMeeting.this, HomeUserActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }
}