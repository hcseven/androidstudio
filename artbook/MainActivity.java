package com.hcs.artbookv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.hcs.artbookv2.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<info> infos;
    private ActivityMainBinding binding;
    adapter adapter1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        infos = new ArrayList<>();

        binding.recycview.setLayoutManager(new LinearLayoutManager(this));
        adapter1 = new adapter(infos);
        binding.recycview.setAdapter(adapter1);

        getartsData();
    }

    private void getartsData(){
        SQLiteDatabase database = this.openOrCreateDatabase("arts", MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("SELECT * FROM arts", null);

        int idix = cursor.getColumnIndex("ID");
        int artix = cursor.getColumnIndex("Artwork");

        while (cursor.moveToNext()){
            String name = cursor.getString(artix);
            int id = cursor.getInt(idix);
            info inf = new info(name, id);
            System.out.println("id : " + id);
            infos.add(inf);
        }
        adapter1.notifyDataSetChanged();
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.art_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuadd){
            Intent intent = new Intent(this, ArtActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}