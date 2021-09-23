package com.anjilibey.kooryuk.view.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anjilibey.kooryuk.R;
import com.anjilibey.kooryuk.utils.BaseApiService;
import com.anjilibey.kooryuk.utils.SharedPrefManager;
import com.anjilibey.kooryuk.utils.UtilsApi;
import com.anjilibey.kooryuk.view.Dashboard.HomeAdminActivity;
import com.anjilibey.kooryuk.view.Dashboard.HomeKabanActivity;
import com.anjilibey.kooryuk.view.Dashboard.HomeUserActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    Button register;
    EditText eName, eUsername, ePassword, eCPassword, eRole;
    BaseApiService mApiService;
    ProgressDialog loading;
    String token, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = findViewById(R.id.btn_submit);
        eName = findViewById(R.id.form_name);
        eUsername = findViewById(R.id.form_username);
        ePassword = findViewById(R.id.form_password);
        eCPassword = findViewById(R.id.form_cPassword);
        eRole = findViewById(R.id.form_role);
        toolbar();
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(RegisterActivity.this);
        token = sharedPrefManager.getSpToken();
        role = sharedPrefManager.getSpRole();
        Log.d("token ubah", token);
//
//        Intent intent = getIntent();
//        id = intent.getStringExtra("nganu");
//        Log.d("ikilo", id);
    }

    private void toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarText = (TextView)
                findViewById(R.id.toolbar_text);

        if(toolbarText!=null && toolbar!=null) {
            toolbarText.setText("Register User");
            setSupportActionBar(toolbar);
        }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    public void btnSubmit(View view) {
        String a = eName.getText().toString().trim();
        String b = eUsername.getText().toString().trim();
        String c = ePassword.getText().toString().trim();
        String d = eCPassword.getText().toString().trim();
        String e = eRole.getText().toString().trim();
        if (a.isEmpty()) {
            eName.setError("Harap masukkan Nama");
            eName.requestFocus();
        }
        else if (b.isEmpty()) {
            ePassword.setError("Harap masukkan username");
            ePassword.requestFocus();
        }
        else if (c.isEmpty()) {
            eCPassword.setError("Harap masukkan password");
            eCPassword.requestFocus();
        }
        else if (d.isEmpty()) {
            eCPassword.setError("Harap masukkan konfirmasi password");
            eCPassword.requestFocus();
        }
        else if (!d.equals(c)) {
            eCPassword.setError("Password anda tidak sama");
            eCPassword.requestFocus();
        }
        else {
            loading = ProgressDialog.show(RegisterActivity.this, null, "Harap Tunggu...", true, false);
            mApiService.registerRequest(
                    "application/json",
                    eName.getText().toString(),
                    eUsername.getText().toString(),
                    ePassword.getText().toString(),
                    eCPassword.getText().toString(),
                    eRole.getText().toString()
            ).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        loading.dismiss();
                        Toast.makeText(RegisterActivity.this, "Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                        validasi();
                    } else {
                        loading.dismiss();
                        Toast.makeText(RegisterActivity.this, "Maaf data anda salah", Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(RegisterActivity.this, HomeAdminActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        else if (role.equals("3")){
            startActivity(new Intent(RegisterActivity.this, HomeKabanActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        else {
            startActivity(new Intent(RegisterActivity.this, HomeUserActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }

}