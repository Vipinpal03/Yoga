package com.engrehammetwally.yoga;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.engrehammetwally.yoga.adapter.ExercisesAdapter;
import com.engrehammetwally.yoga.model.Exercises;

import java.util.ArrayList;
import java.util.List;

public class ExercisesListActivity extends AppCompatActivity {

    RecyclerView rvExercisesList;
    RecyclerView.LayoutManager layoutManager;

    ExercisesAdapter adapter;

    List<Exercises> exercises=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_list);

        rvExercisesList= findViewById(R.id.rvExercisesList);
        rvExercisesList.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        rvExercisesList.setLayoutManager(layoutManager);

        exercises.add(new Exercises(R.drawable.easy_pose,"Easy Pose"));
        exercises.add(new Exercises(R.drawable.cobra_pose,"Cobra Pose"));
        exercises.add(new Exercises(R.drawable.downward_facing_dog,"Downward Facing Dog"));
        exercises.add(new Exercises(R.drawable.boat_pose,"Boat Pose"));
        exercises.add(new Exercises(R.drawable.half_pigeon,"Half Pigeon"));
        exercises.add(new Exercises(R.drawable.low_lunge,"Low Lunge"));
        exercises.add(new Exercises(R.drawable.upward_bow,"Upward Bow"));
        exercises.add(new Exercises(R.drawable.crescent_lunge,"Crescent Lunge"));
        exercises.add(new Exercises(R.drawable.warrior_pose,"Warrior Pose"));
        exercises.add(new Exercises(R.drawable.bow_pose,"Bow Pose"));
        exercises.add(new Exercises(R.drawable.warrior_pose_2,"Warrior Pose 2"));

        adapter=new ExercisesAdapter(exercises,this);

        rvExercisesList.setAdapter(adapter);
    }
}
