package blackskystudio.com.dinaskebersihan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterSampah extends RecyclerView.Adapter<AdapterSampah.AdapterSampahHolder> {

    private ArrayList<ItemSampah> dataList;

    public AdapterSampah(ArrayList<ItemSampah> dataList){
        this.dataList = dataList;
    }

    @Override
    public AdapterSampahHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_sampah, parent, false);
        return new AdapterSampahHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSampah.AdapterSampahHolder holder, int position) {
        holder.reqNama.setText(dataList.get(position).getNama());
        holder.reqAlamat.setText(dataList.get(position).getAlamat());
        holder.reqJSampah.setText(dataList.get(position).getJumlahSampah());
        holder.wrapper.setTag(R.id.wrapper, dataList.get(position).getId_sampah());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class AdapterSampahHolder extends RecyclerView.ViewHolder{
        private TextView reqNama, reqAlamat, reqJSampah;
        private LinearLayout wrapper;

        public AdapterSampahHolder(final View itemView) {
            super(itemView);
            reqNama     = (TextView)itemView.findViewById(R.id.reqNama);
            reqAlamat   = (TextView)itemView.findViewById(R.id.reqAlamat);
            reqJSampah  = (TextView)itemView.findViewById(R.id.reqJSampah);

            wrapper     = (LinearLayout)itemView.findViewById(R.id.wrapper);

            wrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = (String)v.getTag(R.id.wrapper);
                    Log.d("id", id);

                    Intent intent = new Intent(v.getContext(), detail.class);
                    intent.putExtra("id_sampah", id);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
