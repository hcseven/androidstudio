package com.hcs.mapsjava;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {info.class}, version = 1)
public abstract class infoDatabase extends RoomDatabase {
    public abstract infoDao infoDao();
}
