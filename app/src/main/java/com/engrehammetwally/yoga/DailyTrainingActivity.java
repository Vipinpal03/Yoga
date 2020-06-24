package com.engrehammetwally.yoga;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.engrehammetwally.yoga.common.Common;
import com.engrehammetwally.yoga.database.YogaDB;
import com.engrehammetwally.yoga.model.Exercises;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class DailyTrainingActivity extends AppCompatActivity {

    ImageView ivExercisePhoto,tvPause,tvPlay,tvCancel;
    Button btnStartTraining;
    TextView tvGetReady, tvCountDown, tvTimer, tvExerciseName;
    MaterialProgressBar progressBar;
    LinearLayout getReadyLayout;
    List<Exercises> list = new ArrayList<>();
    int exerciseId = 0;
    YogaDB yogaDB;
    MediaPlayer mediaPlayer;
    private boolean isPaused = false;
    private long timeRemaining = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_training);

        initData();

        yogaDB = new YogaDB(this);

        btnStartTraining = findViewById(R.id.btnStartTraining);

        ivExercisePhoto = findViewById(R.id.ivExercisePhoto);
        tvPause = findViewById(R.id.tvPause);
        tvPlay = findViewById(R.id.tvPlay);
        tvCancel = findViewById(R.id.tvCancel);

        tvGetReady = findViewById(R.id.tvGetReady);

        tvCountDown = findViewById(R.id.tvCountDown);

        tvTimer = findViewById(R.id.tvTimer);

        tvExerciseName = findViewById(R.id.tvExerciseName);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(list.size());

        getReadyLayout = findViewById(R.id.getReadyLayout);

        setExerciseInfo(exerciseId);

        btnStartTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStartTraining.getText().toString().toLowerCase().equals("start")) {
                    showGetReady();
                    btnStartTraining.setText("done");
                } else if (btnStartTraining.getText().toString().toLowerCase().equals("done")) {
                    if (yogaDB.getSettingMode() == 0)
                        countDownTimerEasyMode.cancel();
                    else if (yogaDB.getSettingMode() == 1)
                        countDownTimerMediumMode.cancel();
                    else if (yogaDB.getSettingMode() == 2)
                        countDownTimerHardMode.cancel();

                    countDownTimerReset.cancel();

                    if (exerciseId < list.size() - 1) {
                        showRestTime();
                        exerciseId++;
                        progressBar.setProgress(exerciseId);
                    } else {
                        showFinished();
                    }
                } else {
                    if (yogaDB.getSettingMode() == 0)
                        countDownTimerEasyMode.cancel();
                    else if (yogaDB.getSettingMode() == 1)
                        countDownTimerMediumMode.cancel();
                    else if (yogaDB.getSettingMode() == 2)
                        countDownTimerHardMode.cancel();

                    countDownTimerReset.cancel();

                    if (exerciseId < list.size()) {
                        setExerciseInfo(exerciseId);
                    } else {
                        showFinished();
                    }

                }
            }
        });

        tvPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPlay.setVisibility(View.VISIBLE);
                tvPause.setVisibility(View.INVISIBLE);
                isPaused = true;
                if (yogaDB.getSettingMode() == 0)
                    countDownTimerEasyMode.start();
                else if (yogaDB.getSettingMode() == 1)
                    countDownTimerMediumMode.start();
                else if (yogaDB.getSettingMode() == 2)
                    countDownTimerHardMode.start();
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
                        if(isPaused)
                        {
                            cancel();
                        }
                        else {
                            tvTimer.setText("" + l / 1000);
                            timeRemaining =l;
                        }
                    }

                    @Override
                    public void onFinish() {
                        if (exerciseId < list.size() - 1) {
                            exerciseId++;
                            progressBar.setProgress(exerciseId);
                            tvTimer.setText("");
                            setExerciseInfo(exerciseId);
                            btnStartTraining.setText(getResources().getString(R.string.start));
                        } else {
                            showFinished();
                        }
                    }
                }.start();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exercisesListIntent=new Intent(DailyTrainingActivity.this,ExercisesListActivity.class);
                startActivity(exercisesListIntent);
                mediaPlayer.stop();
                finish();
            }
        });
    }

    private void initData() {
        list.add(new Exercises(R.drawable.easy_pose, "Easy Pose"));
        list.add(new Exercises(R.drawable.cobra_pose, "Cobra Pose"));
        list.add(new Exercises(R.drawable.downward_facing_dog, "Downward Facing Dog"));
        list.add(new Exercises(R.drawable.boat_pose, "Boat Pose"));
        list.add(new Exercises(R.drawable.half_pigeon, "Half Pigeon"));
        list.add(new Exercises(R.drawable.low_lunge, "Low Lunge"));
        list.add(new Exercises(R.drawable.upward_bow, "Upward Bow"));
        list.add(new Exercises(R.drawable.crescent_lunge, "Crescent Lunge"));
        list.add(new Exercises(R.drawable.warrior_pose, "Warrior Pose"));
        list.add(new Exercises(R.drawable.bow_pose, "Bow Pose"));
        list.add(new Exercises(R.drawable.warrior_pose_2, "Warrior Pose 2"));

    }

    private void showRestTime() {
        ivExercisePhoto.setVisibility(View.INVISIBLE);
        getReadyLayout.setVisibility(View.VISIBLE);
        btnStartTraining.setVisibility(View.VISIBLE);
        btnStartTraining.setText("Skip");
        tvTimer.setVisibility(View.INVISIBLE);
        countDownTimerReset.start();
        tvGetReady.setText("Reset Time");
    }

    private void showFinished() {
        ivExercisePhoto.setVisibility(View.INVISIBLE);
        btnStartTraining.setVisibility(View.INVISIBLE);
        tvTimer.setVisibility(View.INVISIBLE);
        tvCancel.setVisibility(View.INVISIBLE);
        tvPause.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        tvExerciseName.setVisibility(View.INVISIBLE);

        getReadyLayout.setVisibility(View.VISIBLE);

        yogaDB.setWorkOutDays("" + Calendar.getInstance().getTimeInMillis());
        tvGetReady.setText("Finished !!!");
        tvCountDown.setTextSize(20);
        new CountDownTimer(11000, 1000) {

            @Override
            public void onTick(long l) {
                tvCountDown.setText("Congratulation \n you're done with today exercise \n \n \n  ");
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(DailyTrainingActivity.this, MainActivity.class));
            }
        }.start();


    }

    private void showGetReady() {
        tvExerciseName.setVisibility(View.INVISIBLE);
        ivExercisePhoto.setVisibility(View.INVISIBLE);
        btnStartTraining.setVisibility(View.INVISIBLE);
        getReadyLayout.setVisibility(View.VISIBLE);
        tvTimer.setVisibility(View.INVISIBLE);
        tvCancel.setVisibility(View.INVISIBLE);
        tvPause.setVisibility(View.INVISIBLE);

        tvGetReady.setText("Get Ready");
        mediaPlayer = MediaPlayer.create(this, R.raw.wait);

        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {
                tvCountDown.setText("" + l / 1000);
                mediaPlayer.start();
            }

            @Override
            public void onFinish() {
                showExercises();
                mediaPlayer.stop();
            }
        }.start();


    }

    private void showExercises() {
        if (exerciseId < list.size()) {
            tvExerciseName.setVisibility(View.VISIBLE);
            tvCancel.setVisibility(View.VISIBLE);
            tvPause.setVisibility(View.VISIBLE);
            ivExercisePhoto.setVisibility(View.VISIBLE);
            btnStartTraining.setVisibility(View.INVISIBLE);
            getReadyLayout.setVisibility(View.INVISIBLE);

            tvTimer.setVisibility(View.VISIBLE);

            isPaused = false;

            if (yogaDB.getSettingMode() == 0)
                countDownTimerEasyMode.start();
            else if (yogaDB.getSettingMode() == 1)
                countDownTimerMediumMode.start();
            else if (yogaDB.getSettingMode() == 2)
                countDownTimerHardMode.start();

            Glide.with(this)
                    .load(list.get(exerciseId).getExercisesImageId())
                    .into(ivExercisePhoto);
            tvExerciseName.setText(list.get(exerciseId).getExercisesName());

        } else {
            showFinished();
        }

    }

    private void setExerciseInfo(int id) {
        Glide.with(this)
                .load(list.get(id).getExercisesImageId())
                .into(ivExercisePhoto);
        tvExerciseName.setText(list.get(id).getExercisesName());
        btnStartTraining.setText(getResources().getString(R.string.start));

        tvPause.setVisibility(View.VISIBLE);
        ivExercisePhoto.setVisibility(View.VISIBLE);
        btnStartTraining.setVisibility(View.INVISIBLE);
        tvTimer.setVisibility(View.INVISIBLE);
        getReadyLayout.setVisibility(View.VISIBLE);
        showGetReady();
    }

    CountDownTimer countDownTimerEasyMode = new CountDownTimer(Common.TIME_LIMIT_EASY, 1000) {
        @Override
        public void onTick(long l) {
            if(isPaused)
            {
                cancel();
            }
            else {
                tvTimer.setText("" + l / 1000);
                timeRemaining =l;
            }
        }

        @Override
        public void onFinish() {
            if (exerciseId < list.size() - 1) {
                exerciseId++;
                progressBar.setProgress(exerciseId);
                tvTimer.setText("");
                setExerciseInfo(exerciseId);
                btnStartTraining.setText(getResources().getString(R.string.start));


            } else {
                showFinished();
            }
        }
    };
    CountDownTimer countDownTimerMediumMode = new CountDownTimer(Common.TIME_LIMIT_MEDIUM, 1000) {
        @Override
        public void onTick(long l) {
            if(isPaused)
            {
                //If the user request to cancel or paused the
                //CountDownTimer we will cancel the current instance
                cancel();
            }
            else {
                tvTimer.setText("" + l / 1000);
                timeRemaining =l;
            }

        }

        @Override
        public void onFinish() {
            if (exerciseId < list.size() - 1) {
                exerciseId++;
                progressBar.setProgress(exerciseId);
                tvTimer.setText("");
                setExerciseInfo(exerciseId);
                btnStartTraining.setText(getResources().getString(R.string.start));
            } else {
                showFinished();
            }
        }
    };
    CountDownTimer countDownTimerHardMode = new CountDownTimer(Common.TIME_LIMIT_Hard, 1000) {
        @Override
        public void onTick(long l) {
            if(isPaused)
            {
                //If the user request to cancel or paused the
                //CountDownTimer we will cancel the current instance
                cancel();
            }
            else {
                tvTimer.setText("" + l / 1000);
                timeRemaining =l;
            }

        }

        @Override
        public void onFinish() {
            if (exerciseId < list.size() - 1) {
                exerciseId++;
                progressBar.setProgress(exerciseId);
                tvTimer.setText("");
                setExerciseInfo(exerciseId);
                btnStartTraining.setText(getResources().getString(R.string.start));
            } else {
                showFinished();
            }
        }
    };
    CountDownTimer countDownTimerReset = new CountDownTimer(11000, 1000) {
        @Override
        public void onTick(long l) {
            tvCountDown.setText("" + l / 1000);
        }

        @Override
        public void onFinish() {
            setExerciseInfo(exerciseId);
            showExercises();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
    }
}
