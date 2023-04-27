package com.hcs.artbookv2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hcs.artbookv2.databinding.ActivityArtBinding;

import java.io.ByteArrayOutputStream;

public class ArtActivity extends AppCompatActivity {
    private ActivityArtBinding binding;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> permissionLauncher;
    Bitmap bmimage;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArtBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        registerlauncher();
    }

    public void savebutton(View view){
        String artwork = binding.artwork.getText().toString();
        String artist = binding.artist.getText().toString();
        String year = binding.year.toString();

        Bitmap smallimage = makesmallerimage(bmimage);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        smallimage.compress(Bitmap.CompressFormat.PNG, 50, baos);
        byte[] byteimage = baos.toByteArray();

        database = this.openOrCreateDatabase("arts", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS arts(ID INT PRIMARY KEY, Artwork VARCHAR, Artist VARCHAR, Year VARCHAR, Image BLOB)");
        String sqlcom = "INSERT INTO arts(Artwork, Artist, Year, Image) values(?,?,?,?)";
        SQLiteStatement sqLiteStatement = database.compileStatement(sqlcom);
        sqLiteStatement.bindString(1, artwork);
        sqLiteStatement.bindString(2, artist);
        sqLiteStatement.bindString(3, year);
        sqLiteStatement.bindBlob(4, byteimage);
        sqLiteStatement.execute();
        Intent intent = new Intent(ArtActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    public Bitmap makesmallerimage(Bitmap img){
        int width = img.getWidth();
        int height = img.getHeight();
        float ratio = (float)width / (float)height;
        if(ratio > 1){
           width = 250;
           height = (int)(width/ratio);
        }
        else {
            height = 250;
            width = (int) (height * ratio);
        }
        return img.createScaledBitmap(img, width, height, true);
    }

    public void selectimage(View view){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
           if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
               Snackbar.make(view, "Need Permission!", BaseTransientBottomBar.LENGTH_INDEFINITE).setAction("Give Permissiom", new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                   }
               }).show();
           }
           else {
               permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
           }
        }
        else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intent);
        }
    }

    public void registerlauncher(){

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                //result kullanıcının galerisinden dönen değer
                if (result.getResultCode() == RESULT_OK){
                    Intent intentfromresult = result.getData();
                    if (intentfromresult != null){
                        Uri imagedata = intentfromresult.getData();
                        //binding.image.setImageURI(imagedata);
                        try{
                            if(Build.VERSION.SDK_INT >= 28){
                                ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), imagedata);
                                bmimage = ImageDecoder.decodeBitmap(source);
                                binding.image.setImageBitmap(bmimage);
                            }
                            else {
                                bmimage = MediaStore.Images.Media.getBitmap(getContentResolver(), imagedata);
                                binding.image.setImageBitmap(bmimage);
                            }
                        } catch (Exception e){


                        }
                    }

                }

            }
        });
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if (result) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intent);
                }
                else {
                    Toast.makeText(ArtActivity.this, "Permission Need", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}