package com.hcs.landmark;

import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hcs.landmark.databinding.GorunusBinding;


import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.holder> {
    ArrayList<info> inf;

    public adapter(ArrayList<info> inf){
        this.inf = inf;
    }

    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GorunusBinding imgb = GorunusBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new holder(imgb);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.imgbind.Name.setText(inf.get(position).name);
        holder.imgbind.image.setImageResource(inf.get(position).image);
        holder.imgbind.lastmessage.setText(inf.get(position).lastmessage);
    }

    @Override
    public int getItemCount() {
        return inf.size();
    }


    public class holder extends RecyclerView.ViewHolder{

        private GorunusBinding imgbind;

        public holder(GorunusBinding imgbind) {
            super(imgbind.getRoot());
            this.imgbind = imgbind;
        }

    }

}
