package com.rubensth.androidroom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Word> listaWord;

    public Adapter(List<Word> lista) {
        this.listaWord = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Word filme = listaWord.get(position);
        holder.titulo.setText(filme.getWord());

    }

    @Override
    public int getItemCount() {
        return listaWord.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titulo;

        public MyViewHolder(View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.itemLista);
        }
    }
}