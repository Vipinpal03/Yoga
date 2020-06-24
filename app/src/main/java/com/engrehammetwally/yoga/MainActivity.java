package com.engrehammetwally.yoga;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    AppCompatButton btnSettings,btnExercises,btnCalender;
    ImageView ivPlayTraining;
    Toolbar toolbar;
    String emailMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        btnSettings= findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent=new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });

        btnExercises=  findViewById(R.id.btnExercises);
        btnExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exercisesIntent=new Intent(MainActivity.this,ExercisesListActivity.class);
                startActivity(exercisesIntent);
            }
        });

        ivPlayTraining=  findViewById(R.id.ivPlayTraining);
        ivPlayTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trainingIntent=new Intent(MainActivity.this,DailyTrainingActivity.class);
                startActivity(trainingIntent);
            }
        });

        btnCalender=  findViewById(R.id.btnCalender);
        btnCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calendarIntent=new Intent(MainActivity.this,CalendarActivity.class);
                startActivity(calendarIntent);
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_about_yoga) {
            AboutYogaFragment aboutYogaFragment = new AboutYogaFragment();
            aboutYogaFragment.show(getSupportFragmentManager(), "Dialog Fragment");
        } else if (id == R.id.nav_about_us) {
            startActivity(new Intent(MainActivity.this,AboutUsActivity.class));
//            AboutFm2AppsFragment aboutFm2AppsFragment = new AboutFm2AppsFragment();
//            aboutFm2AppsFragment.show(getSupportFragmentManager(), "Dialog Fragment");
        } else if (id == R.id.nav_contact_us) {
            contactUs();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void contactUs() {
        final EditText input = new EditText(this);
        input.setLines(4);
        input.setSingleLine(false);
        input.setGravity(Gravity.TOP);
        input.setHint("Type Your Message here ...");
        new AlertDialog.Builder(this)
                .setTitle("Contact Us")
                .setView(input)
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        emailMessage = input.getText().toString();
                        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                        final PackageManager pm = getPackageManager();
                        final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                        ResolveInfo best = null;
                        for (final ResolveInfo info : matches)
                            if (info.activityInfo.packageName.endsWith(".gm") ||
                                    info.activityInfo.name.toLowerCase().contains("gmail"))
                                best = info;
                        if (best != null)
                            intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                        intent.setType("message/rfc822");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@fm2apps.com"});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Message Yoga User");
                        intent.putExtra(Intent.EXTRA_TEXT, emailMessage);
                        try {
                            startActivity(Intent.createChooser(intent, "Send mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();

    }
}
