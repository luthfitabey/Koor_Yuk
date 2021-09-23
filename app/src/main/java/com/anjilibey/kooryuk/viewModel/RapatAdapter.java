package com.anjilibey.kooryuk.viewModel;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anjilibey.kooryuk.R;
import com.anjilibey.kooryuk.model.Rapat;
import com.anjilibey.kooryuk.view.TL.SubmitDecision;

import java.util.ArrayList;

public class RapatAdapter extends RecyclerView.Adapter<RapatAdapter.ViewHolder>{
    private ArrayList<Rapat> result;

    public RapatAdapter(ArrayList<Rapat> result) {
        this.result = result;
    }

    @Override
    public RapatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_row_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RapatAdapter.ViewHolder viewHolder, int i) {
        viewHolder.topik.setText(result.get(i).topik);
        viewHolder.waktuM.setText(result.get(i).waktu_mulai);
        viewHolder.waktuS.setText(result.get(i).waktu_selesai);
        viewHolder.tgl.setText(result.get(i).tanggal);
        viewHolder.tempat.setText(result.get(i).tempat);
        viewHolder.poin.setText(result.get(i).poin1);

        final String idRapat = result.get(i).getId();
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), SubmitDecision.class);
                mIntent.putExtra("id_rapat", idRapat);
                view.getContext().startActivity(mIntent);
            }
        });
    }

    public int getItemCount(){
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView topik, waktuM, waktuS, tgl, tempat, poin;
        public ViewHolder(View view) {
            super(view);
            topik = (TextView)view.findViewById(R.id.tvTopik);
            tgl = (TextView)view.findViewById(R.id.tvTgl);
            waktuM = (TextView)view.findViewById(R.id.tvWaktuM);
            waktuS = (TextView)view.findViewById(R.id.tvWaktuS);
            tempat = (TextView)view.findViewById(R.id.tvPlace);
            poin = (TextView)view.findViewById(R.id.tvPoin1);
        }
    }
}
