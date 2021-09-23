package com.anjilibey.kooryuk.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anjilibey.kooryuk.R;
import com.anjilibey.kooryuk.utils.BaseApiService;
import com.anjilibey.kooryuk.utils.SharedPrefManager;
import com.anjilibey.kooryuk.utils.UtilsApi;
import com.anjilibey.kooryuk.view.Dashboard.HomeAdminActivity;
import com.anjilibey.kooryuk.view.Dashboard.HomeKabanActivity;
import com.anjilibey.kooryuk.view.Dashboard.HomeUserActivity;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    BaseApiService mApiService;
    ProgressDialog loading;
    EditText mUsername, mPassword;
    Button btnLogin;
    SharedPrefManager sharedPrefManager;
    String token = null;
    String role = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fragment);
        mUsername = findViewById(R.id.edit_username);
        mPassword = findViewById(R.id.edit_password);
        btnLogin = findViewById(R.id.btn_submit);
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(LoginActivity.this);

       validasi();

    }
    private void validasi(){
        if(sharedPrefManager.getSPSudahLogin()){
            role = sharedPrefManager.getSpRole();
            if (role.equals("1")){
                startActivity(new Intent(LoginActivity.this, HomeAdminActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
            else if (role.equals("3")){
                startActivity(new Intent(LoginActivity.this, HomeKabanActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
            else if (role.equals("2")){
                startActivity(new Intent(LoginActivity.this, HomeUserActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        }
    }
    public void btnLogin(View view) {
        String user = mUsername.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        if(user.isEmpty()){
            mUsername.setError("Harap masukkan username");
            mUsername.requestFocus();
            return;
        }
        else if (password.isEmpty()){
            mPassword.setError("Harap masukkan password");
            mPassword.requestFocus();
            return;
        }

        loading = ProgressDialog.show(LoginActivity.this, null, "Harap Tungu....", true, false);
        mApiService.loginRequest(mUsername.getText().toString(), mPassword.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            loading.dismiss();
                            try {
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                token = jsonResults.getJSONObject("success").getString("token");
                                role = jsonResults.getString("role");
                                Log.d("token", "berhasil");
                                Log.d("hasil token", token);
                                Log.d("role", role);
                                sharedPrefManager.saveSPString(SharedPrefManager.SP_TOKEN, token);
                                sharedPrefManager.saveSPString(SharedPrefManager.SP_ROLE, role);
                                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                                if (role.equals("1")){
                                    startActivity(new Intent(LoginActivity.this, HomeAdminActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                }
                                else if (role.equals("3")){
                                    startActivity(new Intent(LoginActivity.this, HomeKabanActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                }
                                else if (role.equals("2")){
                                    startActivity(new Intent(LoginActivity.this, HomeUserActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            loading.dismiss();
                            Toast.makeText(LoginActivity.this, "Maaf data anda salah", Toast.LENGTH_SHORT).show();
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
