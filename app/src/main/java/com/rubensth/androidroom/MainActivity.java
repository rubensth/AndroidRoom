package com.rubensth.androidroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Entity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    WordRoomDatabase db;
    RecyclerView recyclerView;

    public List<Word> lista = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        carregar();
        recycler();

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TelaCadatro.class);
                startActivity(intent);
            }
        });
        //Adicionar evento de clique no recyclerView
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Toast.makeText(MainActivity.this, "Item Deletado", Toast.LENGTH_SHORT).show();
                                db = Room.databaseBuilder(getApplicationContext(),
                                        WordRoomDatabase.class, "Word_DB").allowMainThreadQueries().build();
                                WordDao wordDao = db.wordDao();
                                String valor =  lista.get(position).getWord();
                                db.wordDao().deletar(valor);
                                onStart();
                            }
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            }
                        }
                )
        );
    }
    @Override
    protected void onStart() {
        carregar();
        recycler();
        super.onStart();
    }

    public void carregar(){
    db = Room.databaseBuilder(getApplicationContext(),
            WordRoomDatabase.class, "Word_DB").allowMainThreadQueries().build();
    List<Word> lista = db.wordDao().getAll();
    this.lista = lista;
 }

 public void recycler(){
     Adapter adapter = new Adapter(lista);

     //Configurar Recyclesview
     RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
     recyclerView.setLayoutManager(layoutManager);
     recyclerView.setHasFixedSize(true);
     recyclerView.setAdapter(adapter);
     this.recyclerView = recyclerView;
 }


}