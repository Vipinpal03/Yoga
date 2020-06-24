package com.engrehammetwally.yoga;

import android.content.Intent;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.engrehammetwally.yoga.common.Common;
import com.engrehammetwally.yoga.database.YogaDB;


public class ExercisesDetailsActivity extends AppCompatActivity {

    TextView tvExerciseName, tvTimer;
    ImageView ivExercisePhoto, tvPause, tvPlay, tvCancel;
    Button btnStart;
    boolean isRunning = false;
    YogaDB yogaDB;

    int timeLimit = 0;
    private boolean isPaused = false;
    private long timeRemaining = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_details);

        tvExerciseName = findViewById(R.id.tvExerciseName);

        ivExercisePhoto = findViewById(R.id.ivExercisePhoto);
        tvPause = findViewById(R.id.tvPause);
        tvPlay = findViewById(R.id.tvPlay);
        tvCancel = findViewById(R.id.tvCancel);

        tvTimer = findViewById(R.id.tvTimer);

        Intent intentDetails = getIntent();
        Glide.with(this)
                .load(intentDetails.getIntExtra("EXERCISE_PHOTO", 0))
                .into(ivExercisePhoto);
        tvExerciseName.setText(intentDetails.getStringExtra("EXERCISE_NAME"));

        yogaDB = new YogaDB(this);


        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    btnStart.setText(getResources().getString(R.string.done));
                    btnStart.setVisibility(View.INVISIBLE);
                    if (yogaDB.getSettingMode() == 0) {
                        timeLimit = Common.TIME_LIMIT_EASY;
                    } else if (yogaDB.getSettingMode() == 1) {
                        timeLimit = Common.TIME_LIMIT_MEDIUM;
                    } else if (yogaDB.getSettingMode() == 2) {
                        timeLimit = Common.TIME_LIMIT_Hard;
                    }

                    new CountDownTimer(timeLimit, 1000) {
                        @Override
                        public void onTick(long l) {
                            if(isPaused)
                            {
                                cancel();
                            }
                            else {
                                tvTimer.setText("" + l / 1000);
                                timeRemaining = l;
                            }

                        }

                        @Override
                        public void onFinish() {
                            Toast.makeText(ExercisesDetailsActivity.this, "Finished !!!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }.start();
                    tvPause.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tvPlay.setVisibility(View.VISIBLE);
                            tvPause.setVisibility(View.INVISIBLE);
                            isPaused = true;
                            if (yogaDB.getSettingMode() == 0) {
                                timeLimit = Common.TIME_LIMIT_EASY;
                            } else if (yogaDB.getSettingMode() == 1) {
                                timeLimit = Common.TIME_LIMIT_MEDIUM;
                            } else if (yogaDB.getSettingMode() == 2) {
                                timeLimit = Common.TIME_LIMIT_Hard;
                            }

                            new CountDownTimer(timeLimit, 1000) {
                                @Override
                                public void onTick(long l) {
                                    if(isPaused)
                                    {
                                        cancel();
                                    }
                                    else {
                                        tvTimer.setText("" + l / 1000);
                                        timeRemaining = l;
                                    }

                                }

                                @Override
                                public void onFinish() {
                                    Toast.makeText(ExercisesDetailsActivity.this, "Finished !!!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }.start();

                        }
                    });

                    tvPlay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tvPlay.setVisibility(View.INVISIBLE);
                            tvPause.setVisibility(View.VISIBLE);

                            isPaused = false;

                            new CountDownTimer(timeRemaining, 1000) {
                                @Override
                                public void onTick(long l) {
                                    if (isPaused) {
                                        cancel();
                                    } else {
                                        tvTimer.setText("" + l / 1000);
                                        timeRemaining = l;
                                    }
                                }

                                @Override
                                public void onFinish() {
                                    Toast.makeText(ExercisesDetailsActivity.this, "Finished !!!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }.start();
                        }
                    });

                    tvCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent exercisesListIntent = new Intent(ExercisesDetailsActivity.this, ExercisesListActivity.class);
                            startActivity(exercisesListIntent);
                            finish();
                        }
                    });
                } else {
                    Toast.makeText(ExercisesDetailsActivity.this, "Finished !!!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                isRunning = !isRunning;
            }
        });

        tvTimer.setText("");

    }

    @Override
    protected void onPause() {
        super.onPause();
        isPaused=true;

    }
}
