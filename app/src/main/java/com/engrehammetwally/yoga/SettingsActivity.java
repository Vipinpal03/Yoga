package com.engrehammetwally.yoga;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.engrehammetwally.yoga.broadcastreceiver.AlarmReceiver;
import com.engrehammetwally.yoga.database.YogaDB;
import com.engrehammetwally.yoga.service.RingtonePlayingService;

import java.util.Calendar;


public class SettingsActivity extends AppCompatActivity {

    RadioGroup rgMode;
    RadioButton rBtnEasy, rBtnMedium, rBtnHard;
    ToggleButton toggleBtn;
    TimePicker timePicker;
    Button btnSave;
    AlarmManager alarmManager;
    Intent intent;
    PendingIntent pendingIntent;

    YogaDB yogaDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        rgMode = findViewById(R.id.rgMode);

        rBtnEasy =  findViewById(R.id.rBtnEasy);
        rBtnMedium =  findViewById(R.id.rBtnMedium);
        rBtnHard = findViewById(R.id.rBtnHard);

        toggleBtn = findViewById(R.id.toggleBtn);

        timePicker = findViewById(R.id.timePicker);

        btnSave = findViewById(R.id.btnSave);

        yogaDB = new YogaDB(this);

        int mode = yogaDB.getSettingMode();
        setRadioButton(mode);

        try {
            if (yogaDB.getWorkOutTime() != null) {
                toggleBtn.setChecked(true);
                String[] time = yogaDB.getWorkOutTime().split(":");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Toast.makeText(this, yogaDB.getWorkOutTime(), Toast.LENGTH_LONG).show();
                    timePicker.setHour(Integer.parseInt(time[0]));
                    timePicker.setMinute(Integer.parseInt(time[1]));
                    yogaDB.updateWorkOutTime(time[0]+":"+time[1]);
                    Toast.makeText(this, timePicker.getHour() + ":" + timePicker.getMinute(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, yogaDB.getWorkOutTime(), Toast.LENGTH_LONG).show();
                    timePicker.setCurrentHour(Integer.parseInt(time[0]));
                    timePicker.setCurrentMinute(Integer.parseInt(time[1]));
                    yogaDB.updateWorkOutTime(time[0]+":"+time[1]);
                    Toast.makeText(this, timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute(), Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            Log.e("EXCEPTION", e.getMessage());
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
                setAlarm(toggleBtn.isChecked());
                Toast.makeText(SettingsActivity.this, "Saved !!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void setAlarm(boolean checked) {
        if (checked) {
            Toast.makeText(this, "START ALARM", Toast.LENGTH_SHORT).show();

            Calendar calender = Calendar.getInstance();
            calender.setTimeInMillis(System.currentTimeMillis());
            if (Build.VERSION.SDK_INT >= 23) {
                calender.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calender.set(Calendar.MINUTE, timePicker.getMinute());
                yogaDB.setWorkOutTime(timePicker.getHour() + ":" + timePicker.getMinute());
                Toast.makeText(this, timePicker.getHour() + ":" + timePicker.getMinute(), Toast.LENGTH_SHORT).show();
            } else {
                calender.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calender.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                yogaDB.setWorkOutTime(timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
                Toast.makeText(this, timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute(), Toast.LENGTH_SHORT).show();
            }

            intent = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), 0, pendingIntent);

//            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(),
//                    AlarmManager.INTERVAL_DAY, pendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    calender.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        } else {
            Intent i = new Intent(this, RingtonePlayingService.class);
            stopService(i);

            intent = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);

        }
    }


    private void saveSettings() {
        int selectedId = rgMode.getCheckedRadioButtonId();
        if (selectedId == rBtnEasy.getId()) {
            yogaDB.setSettingMode(0);
        } else if (selectedId == rBtnMedium.getId()) {
            yogaDB.setSettingMode(1);
        } else if (selectedId == rBtnHard.getId()) {
            yogaDB.setSettingMode(2);
        }
    }


    private void setRadioButton(int mode) {
        if (mode == 0) {
            rgMode.check(R.id.rBtnEasy);
        } else if (mode == 1) {
            rgMode.check(R.id.rBtnMedium);
        } else if (mode == 2) {
            rgMode.check(R.id.rBtnHard);
        }
    }

}
