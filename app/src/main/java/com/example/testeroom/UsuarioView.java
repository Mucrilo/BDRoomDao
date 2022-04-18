package com.example.testeroom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UsuarioView extends AppCompatActivity {
    private AppDatabase db;
    private EditText edtUsuario;
    private Button btnSalvar, btnExcluir;
    private int dbUsuarioId;
    private Usuario dbUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_view);
        db = AppDatabase.getDatabase(getApplicationContext());
        edtUsuario = findViewById(R.id.edtUsuario);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnExcluir = findViewById(R.id.btnExcluir);
        dbUsuarioId = getIntent().getIntExtra("usuario_id", -1);
    }

    protected void onResume() {
        super.onResume();
        if(dbUsuarioId >= 0) {
            getUsuario();
        } else {
            btnExcluir.setVisibility(View.GONE);
        }
    }

    private void getUsuario() {
        dbUsuario = db.usuarioDao().get(dbUsuarioId);
        edtUsuario.setText(dbUsuario.getNome());
    }

    public void salvarUsuario(View view) {
        String nome = edtUsuario.getText().toString();

        if (nome.equals("")) {
            Toast.makeText(this, "é necessário especificar um nome!", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);

        if (dbUsuario != null) {
            usuario.setId(dbUsuarioId);
            db.usuarioDao().update(usuario);
            Toast.makeText(this, "Atualizado com sucesso.", Toast.LENGTH_SHORT).show();
        } else {
            db.usuarioDao().insertAll(usuario);
            Toast.makeText(this, "Usuario inserido com sucesso.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void excluirUsuario(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Exclusão de Usuário")
                .setMessage("Deseja excluir esse usuário?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluir();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    private void excluir() {
        try {
            db.usuarioDao().delete(dbUsuario);
            Toast.makeText(this, "Excluído com sucesso", Toast.LENGTH_SHORT).show();
        } catch (SQLiteConstraintException e) {
            Toast.makeText(this, "Impossível excluir!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void voltar(View view) {
        finish();
    }
}