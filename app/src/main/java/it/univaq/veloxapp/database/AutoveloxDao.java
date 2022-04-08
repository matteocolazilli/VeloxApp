package it.univaq.veloxapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import it.univaq.veloxapp.model.Autovelox;

@Dao
public interface AutoveloxDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(List<Autovelox> autoveloxes);

    @Query("SELECT * FROM speed_checkers ORDER BY id")
    List<Autovelox> findAll();
}
