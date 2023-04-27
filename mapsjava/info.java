package com.hcs.mapsjava;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class info implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "Latitude")
    public Double latitude;

    @ColumnInfo(name = "Longitude")
    public Double longitude;

    public info(String name, Double longitude, Double latitude){
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
