package com.anjilibey.kooryuk.view.Dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anjilibey.kooryuk.R;
import com.anjilibey.kooryuk.utils.SharedPrefManager;
import com.anjilibey.kooryuk.view.LoginActivity;
import com.anjilibey.kooryuk.view.MeetingUser.AddMeeting;
import com.anjilibey.kooryuk.view.MeetingUser.SelectMeeting;
import com.anjilibey.kooryuk.view.MeetingUser.SelectTL;
import com.anjilibey.kooryuk.view.ProfileActivity;
import com.anjilibey.kooryuk.view.TL.SubmitDecision;

import java.util.Calendar;

public class HomeUserActivity extends AppCompatActivity {
    ImageView greetImg;
    TextView greetText;
    SharedPrefManager sharedPrefManager;
    String token;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);
        greetImg = findViewById(R.id.greeting_img);
        greetText = findViewById(R.id.greeting_text);

        greeting();
    }

    private void greeting() {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay < 12){
            greetText.setText("Selamat Pagi\nSobat Pembangunan");
            greetImg.setImageResource(R.drawable.img_default_half_morning);
        } else if (timeOfDay >= 12 && timeOfDay < 15) {
            greetText.setText("Selamat Siang\nSobat Pembangunan");
            greetImg.setImageResource(R.drawable.img_default_half_afternoon);
        } else if (timeOfDay >= 15 && timeOfDay < 18) {
            greetText.setText("Selamat Sore\nSobat Pembangunan");
            greetImg.setImageResource(R.drawable.img_default_half_without_sun);
        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            greetText.setText("Selamat Malam\nSobat Pembangunan");
            greetText.setTextColor(Color.WHITE);
            greetImg.setImageResource(R.drawable.img_default_half_night);
        }
    }

    public void btnDirection(View view) {
        startActivity(new Intent(HomeUserActivity.this, SelectTL.class));
    }

    public void btnViewRapat(View view) {
        startActivity(new Intent(HomeUserActivity.this, SelectMeeting.class));
    }

    public void AddRapat(View view) {
        startActivity(new Intent(HomeUserActivity.this, AddMeeting.class));
    }

    public void goToIg(View view) {
        Uri uri = Uri.parse("http://instagram.com/_u/bappedalitbang.kotamojokerto/");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/bappedalitbang.kotamojokerto")));
        }
    }

    public void goToWeb(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://bappedalitbang.mojokertokota.go.id/"));
        startActivity(intent);
    }

    public void btnProfile(View view) {
        startActivity(new Intent(HomeUserActivity.this, ProfileActivity.class));
    }

    public void btnLogOut(View view) {
//        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
        sharedPrefManager.deleteSPString(SharedPrefManager.SP_TOKEN);
        Toast.makeText(HomeUserActivity.this, "Berhasil Keluar", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(HomeUserActivity.this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }
}