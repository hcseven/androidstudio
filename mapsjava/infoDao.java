package com.hcs.mapsjava;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface infoDao {

    @Query("SELECT * FROM info")
    Flowable<List<info>> getAll();

    @Insert
    Completable insert(info inf);

    @Delete
    Completable delete(info inf);
}
