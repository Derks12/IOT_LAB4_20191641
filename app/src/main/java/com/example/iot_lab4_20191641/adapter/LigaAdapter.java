package com.example.iot_lab4_20191641.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.iot_lab4_20191641.R;
import com.example.iot_lab4_20191641.model.Liga;

import java.util.List;

public class LigaAdapter extends RecyclerView.Adapter<LigaAdapter.LigaViewHolder> {

    private Context context;
    private List<Liga> ligaList;

    public LigaAdapter(Context context, List<Liga> ligaList) {
        this.context = context;
        this.ligaList = ligaList;
    }

    @NonNull
    @Override
    public LigaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_ligas, parent, false);
        return new LigaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LigaViewHolder holder, int position) {
        Liga liga = ligaList.get(position);

        holder.idLeague.setText(liga.getIdLeague());
        holder.strLeague.setText(liga.getStrLeague());
        holder.strSport.setText(liga.getStrSport());
        holder.strLeagueAlternate.setText(liga.getStrLeagueAlternate());
    }

    @Override
    public int getItemCount() {
        return (ligaList != null) ? ligaList.size() : 0;
    }

    public static class LigaViewHolder extends RecyclerView.ViewHolder {
        TextView idLeague, strLeague, strSport, strLeagueAlternate;
        CardView cardView;

        public LigaViewHolder(@NonNull View itemView) {
            super(itemView);

            idLeague = itemView.findViewById(R.id.idLeague);
            strLeague = itemView.findViewById(R.id.strLeague);
            strSport = itemView.findViewById(R.id.strSport);
            strLeagueAlternate = itemView.findViewById(R.id.strLeagueAlternate);
            cardView = itemView.findViewById(R.id.cardView1);
        }
    }
}