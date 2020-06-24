package com.engrehammetwally.yoga;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.engrehammetwally.yoga.custom.WorkoutDoneDecorator;
import com.engrehammetwally.yoga.database.YogaDB;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {
    MaterialCalendarView calender;
    YogaDB yogaDB;
    List<String> workDay;
    HashSet<CalendarDay> convertedList = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        yogaDB = new YogaDB(this);
        calender = findViewById(R.id.calender);
        workDay = new ArrayList<>();
        try {
            workDay=yogaDB.getWorkOutDays();
            for (String value : workDay) {
                convertedList.add(CalendarDay.from(new Date(Long.parseLong(value))));
            }
        } catch (NumberFormatException e) {
            Log.e("TAG", e.getMessage());
        }


        calender.addDecorator(new WorkoutDoneDecorator(convertedList));

    }
}
