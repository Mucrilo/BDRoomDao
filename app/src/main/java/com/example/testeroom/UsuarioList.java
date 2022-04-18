package com.example.testeroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class UsuarioList extends AppCompatActivity {
    AppDatabase db;
    List<Usuario> usuarios;
    ListView listUsuarios;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_list);
        db = AppDatabase.getDatabase(getApplicationContext());
        listUsuarios = findViewById(R.id.listViewUsuarios);
    }
    @Override
    protected void onResume() {
        super.onResume();
        intent = new Intent(this, UsuarioView.class);
        preencheUsuarios();
    }

    private void preencheUsuarios() {
        usuarios = db.usuarioDao().getAll();
        ArrayAdapter<Usuario> adapter = new ArrayAdapter<Usuario>(this,
                android.R.layout.simple_list_item_1,usuarios);
        listUsuarios.setAdapter(adapter);
        listUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usuario usuarioselecionado = usuarios.get(position);
                intent.putExtra("usuario_id", usuarioselecionado.getId());
                startActivity(intent);
            }
        });
    }

    public void cadastrarUsuario(View view) {
        startActivity(intent);
    }

    public void voltar(View view) {
        finish();
    }
}