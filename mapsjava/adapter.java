package com.hcs.mapsjava;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.hcs.mapsjava.databinding.RecycshowBinding;

import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.holder> {

    List<info> infos;

    public adapter(List<info> infos){
        this.infos = infos;
    }
    @Override
    public holder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecycshowBinding binding = RecycshowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new holder(binding);
    }

    @Override
    public void onBindViewHolder(holder holder, int position) {
        holder.binding.recyctext.setText(infos.get(position).name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), MapsActivity.class);
                intent.putExtra("info", "old");
                intent.putExtra("inff", infos.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    public class holder extends ViewHolder{
        RecycshowBinding binding;

        public holder(@NonNull RecycshowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
