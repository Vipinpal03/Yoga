package com.engrehammetwally.yoga;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;


public class AboutUsActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tv_what, tv_what_text, tv_service, tv_service_text, tv_contact, tv_contact_text, tv_head_office,
            tv_head_office_text, tv_email, tv_email_text, tv_working_hours, tv_working_hours_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initView();
        showPageDetails();
    }

    public void showPageDetails() {
        tv_what.setText(getResources().getString(R.string.about_us_what));
        tv_what_text.setText(getResources().getString(R.string.about_us_what_text));
        tv_service.setText(getResources().getString(R.string.about_us_service));
        tv_service_text.setText(getResources().getString(R.string.about_us_service_text));
        tv_contact.setText(getResources().getString(R.string.about_us_contact));
        tv_contact_text.setText(getResources().getString(R.string.about_us_contact_text));
        tv_head_office.setText(getResources().getString(R.string.about_us_head_office));
        tv_head_office_text.setText(getResources().getString(R.string.about_us_head_office_text));
        tv_email.setText(getResources().getString(R.string.about_us_email));
        tv_email_text.setText(getResources().getString(R.string.about_us_email_text));
        tv_working_hours.setText(getResources().getString(R.string.about_us_working_hours));
        tv_working_hours_text.setText(getResources().getString(R.string.about_us_working_hours_text));
    }

    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.nav_about_us);

        tv_what = findViewById(R.id.tv_what);
        tv_what_text = findViewById(R.id.tv_what_text);
        tv_service = findViewById(R.id.tv_service);
        tv_service_text = findViewById(R.id.tv_service_text);
        tv_contact = findViewById(R.id.tv_contact);
        tv_contact_text = findViewById(R.id.tv_contact_text);
        tv_head_office = findViewById(R.id.tv_head_office);
        tv_head_office_text = findViewById(R.id.tv_head_office_text);
        tv_email = findViewById(R.id.tv_email);
        tv_email_text = findViewById(R.id.tv_email_text);
        tv_working_hours = findViewById(R.id.tv_working_hours);
        tv_working_hours_text = findViewById(R.id.tv_working_hours_text);
    }


}
