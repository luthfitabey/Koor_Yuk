package com.anjilibey.kooryuk.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.anjilibey.kooryuk.R;
import com.anjilibey.kooryuk.utils.BaseApiService;
import com.anjilibey.kooryuk.utils.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void btnLogOut(View view) {
        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
        sharedPrefManager.deleteSPString(SharedPrefManager.SP_TOKEN);
        Toast.makeText(ProfileActivity.this, "Berhasil Keluar", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }
}