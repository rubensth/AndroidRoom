package com.rubensth.androidroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TelaCadatro extends AppCompatActivity {

    EditText editNome;
    Button salvar;
    Button voltar;
    WordRoomDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadatro);

        editNome = findViewById(R.id.editNome);
        salvar = findViewById(R.id.salvar);
        voltar = findViewById(R.id.voltar);

        db = Room.databaseBuilder(getApplicationContext(),
                WordRoomDatabase.class, "Word_DB").build();

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Valida campo em branco
                if(TextUtils.isEmpty(editNome.getText())){
                    editNome.setError("Preencha um nome");
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            String nome = editNome.getText().toString();
                            Word word = new Word(nome);
                            db.wordDao().insert(word);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    editNome.setText("");
                                    Toast.makeText(TelaCadatro.this, "Salvo", Toast.LENGTH_SHORT).show();
                                    consultas();
                                }
                            });
                        }
                    }).start();
                }
            }
        });
    }

    public void voltar(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void consultas(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Word> word = db.wordDao().getAll();
                for (Word word1 : word){
                    Log.i("Dados", "Nome:" + word1.getWord());
                }
            }
        }).start();

    }



}