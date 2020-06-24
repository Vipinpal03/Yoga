package com.engrehammetwally.yoga.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.engrehammetwally.yoga.ExercisesDetailsActivity;
import com.engrehammetwally.yoga.R;
import com.engrehammetwally.yoga.common.Common;
import com.engrehammetwally.yoga.interfaces.ItemClickListener;
import com.engrehammetwally.yoga.model.Exercises;
import com.engrehammetwally.yoga.viewholder.ExercisesViewHolder;

import java.util.List;


public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesViewHolder> {
    private List<Exercises> exercisesList;
    private Context context;


    public ExercisesAdapter(List<Exercises> exercisesList, Context context) {
        this.exercisesList = exercisesList;
        this.context = context;
    }

    @Override
    public ExercisesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.exercises_item, parent, false);
        return new ExercisesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExercisesViewHolder holder, int position) {

        holder.tvExerciseName.setText(exercisesList.get(position).getExercisesName());
        Glide.with(context)
                .load(exercisesList.get(position).getExercisesImageId())
                .into(holder.ivExerciseImage);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int position, boolean isLongClicked) {
                Intent intent=new Intent(context, ExercisesDetailsActivity.class);
                intent.putExtra(Common.EXERCISE_NAME,exercisesList.get(position).getExercisesName());
                intent.putExtra(Common.EXERCISE_PHOTO,exercisesList.get(position).getExercisesImageId());
                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return exercisesList.size();
    }


}
