package com.example.testeroom;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UsuarioDao {
    @Query("SELECT * FROM Usuario WHERE id = :id LIMIT 1")
    Usuario get(int id);

    @Query("SELECT * FROM Usuario")
    List<Usuario> getAll();

    @Insert
    void insertAll(Usuario... usuarios);

    @Update
    void update(Usuario usuario);

    @Delete
    void delete(Usuario usuario);
}
