package com.engrehammetwally.yoga.viewholder;


import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.engrehammetwally.yoga.R;
import com.engrehammetwally.yoga.interfaces.ItemClickListener;

public class ExercisesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView tvExerciseName;
    public ImageView ivExerciseImage;
    private ItemClickListener itemClickListener;

    public ExercisesViewHolder(View itemView) {
        super(itemView);
        tvExerciseName=(TextView)itemView.findViewById(R.id.tvExerciseName);

        ivExerciseImage=(ImageView)itemView.findViewById(R.id.ivExerciseImage);

        itemView.setOnClickListener(this);
    }


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
