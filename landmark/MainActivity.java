package com.hcs.landmark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.hcs.landmark.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    ArrayList<info> inf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        inf = new ArrayList<>();
        info a1 = new info("hakkı", "Nietszche", R.drawable.pp);
        inf.add(a1);

        adapter adapter = new adapter(inf);
        binding.recyclerview1.setAdapter(adapter);
        binding.recyclerview1.setLayoutManager(new LinearLayoutManager(this));
    }
}