package com.hcs.artbookv2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hcs.artbookv2.databinding.ActivityMainBinding;
import com.hcs.artbookv2.databinding.RecyclershowBinding;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.holder> {

    ArrayList<info> infos;
    public adapter(ArrayList<info> infos){
        this.infos = infos;
    }

    public holder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclershowBinding binding = RecyclershowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new holder(binding);
    }

    @Override
    public void onBindViewHolder(holder holder, int position) {
        holder.binding.recycshow.setText(infos.get(position).name);
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    public class holder extends RecyclerView.ViewHolder{
        private RecyclershowBinding binding;

         public holder(RecyclershowBinding binding) {
             super(binding.getRoot());
             this.binding = binding;
        }
    }
}
