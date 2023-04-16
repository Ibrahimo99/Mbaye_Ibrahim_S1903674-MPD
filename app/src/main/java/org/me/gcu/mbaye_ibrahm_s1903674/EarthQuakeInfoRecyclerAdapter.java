package org.me.gcu.mbaye_ibrahm_s1903674;
//  Ibrahim Mbaye S1903674
import android.content.Context;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EarthQuakeInfoRecyclerAdapter extends RecyclerView.Adapter<EarthQuakeInfoRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<EarthQuakeInfo> earthQuakeInfos;
    private LayoutInflater layoutInflater;


    EarthQuakeInfoRecyclerAdapter(Context context, List<EarthQuakeInfo> earthQuakeInfos) {
        this.context = context;
        this.earthQuakeInfos = earthQuakeInfos;
        this.layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.earthquake_info, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EarthQuakeInfo earthQuakeInfo = earthQuakeInfos.get(position);
        //holder.title.setText(earthQuakeInfo.getTitle());
        holder.description.setText(earthQuakeInfo.getDescription());
        holder.items.setLayoutManager(new LinearLayoutManager(context));
        holder.items.setAdapter(new EarthQuakeItemRecyclerAdapter(context, earthQuakeInfo.getItems()));

    }

    @Override
    public int getItemCount() {
        return earthQuakeInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //public TextView title;
        private TextView description;
        private RecyclerView items;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //this.title = itemView.findViewById(R.id.location_title);
            this.description = itemView.findViewById(R.id.description);
            this.items = itemView.findViewById(R.id.earthquake_items);
        }


    }
}

