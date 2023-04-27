package com.hcs.mapsjava;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.room.Room;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hcs.mapsjava.databinding.ActivityMapsBinding;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    ActivityResultLauncher<String> permissionlauncher;
    LocationManager locationManager;
    LocationListener locationListener;
    Integer a;
    infoDatabase idatabase;
    infoDao dao;
    Double slongitude;
    Double slatitude;
    info selectedinfo;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        registerLauncher();

        a = 0;

        idatabase = Room.databaseBuilder(getApplicationContext(), infoDatabase.class, "infoDatabase").build();
        dao = idatabase.infoDao();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        Intent getintent = getIntent();
        String getinfo = getintent.getStringExtra("info");

        //eski bir şeyi göstereceksek, haritayı son kaldığı yerden açmaya vs gerek yok
        //sadece konumu göstermemiz yeterli olur.
        if (getinfo.matches("old")){
            mMap.clear();
            binding.savebutton.setVisibility(View.GONE);
            selectedinfo = (info)getintent.getSerializableExtra("inff");
            binding.name.setText(selectedinfo.name);
            LatLng getlang = new LatLng(selectedinfo.latitude, selectedinfo.longitude);
            mMap.addMarker(new MarkerOptions().position(getlang).title(binding.name.getText().toString()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getlang, 15));
        }
        //yeni bir şey ekliyorsak izin almamız gerek, son konuma göre harita açmamız gerek
        else if (getinfo.matches("new")){
            binding.deletebutton.setVisibility(View.GONE);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, "NEED PERMISSION")) {
                    Snackbar.make(binding.getRoot(), "Permission Need", BaseTransientBottomBar.LENGTH_INDEFINITE).setAction("Give Permission", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            permissionlauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                        }
                    }).show();
                } else {
                    permissionlauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                }

            } else {
                //gps ile konum almamız, kaç milisaniyede yenileyeceğimiz, kaç metrede yenileyeceğimiz ve listener'ımız input.
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                //bilinen son konumu alıp oraya odaklamak için.
                Location lastknown = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastknown != null){
                    LatLng lastknownloc = new LatLng(lastknown.getLatitude(), lastknown.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastknownloc, 15));
                }
                mMap.setMyLocationEnabled(true);
            }

            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    //başlangıçta sadece bir kez konuma odaklanması için
                    if (a == 0){
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        a = 1;
                    }

                }
            };

        }//yeni bir şey ekleme



    }

    private void registerLauncher() {
        permissionlauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if (result) {
                    //result true olsa bile, yeniden kontrol yapmanı istiyor
                    if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);

                        Location lastknown = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (lastknown != null){
                            LatLng lastknownloc = new LatLng(lastknown.getLatitude(), lastknown.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastknownloc, 15));
                        }
                    }
                }
                else {
                    Toast.makeText(MapsActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    //haritaya uzun tıklayınca o yere bir marker atmak
    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng));
        //konumum enlem ve boylamını alıp kaydetmek.
        slongitude = latLng.longitude;
        slatitude = latLng.latitude;

        binding.savebutton.setEnabled(true);
    }

    public void save(View view){
        info info = new info(binding.name.getText().toString(), slongitude, slatitude);
        compositeDisposable.add(dao.insert(info)
                .subscribeOn(Schedulers.io())//burada yap
                .observeOn(AndroidSchedulers.mainThread())//burada gözlemleyeceğim
                .subscribe(MapsActivity.this::handleResponse));//işlemi yap ve fonksiyonu çalıştır, fonksiyon vermeyebilirsin
    }

    private void handleResponse(){
        Intent intent = new Intent(MapsActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void delete(View view){

        compositeDisposable.add(dao.delete(selectedinfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(MapsActivity.this::handleResponse));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}