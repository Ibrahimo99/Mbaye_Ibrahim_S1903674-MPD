package org.me.gcu.mbaye_ibrahm_s1903674;
//  Ibrahim Mbaye S1903674
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

public class EarthQuakeItemRecyclerAdapter extends RecyclerView.Adapter<EarthQuakeItemRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<EarthQuakeItem> earthQuakeItems;
    private LayoutInflater layoutInflater;

    EarthQuakeItemRecyclerAdapter(Context context, List<EarthQuakeItem> earthQuakeItems) {
        this.context = context;
        this.earthQuakeItems = earthQuakeItems;
        this.layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_display_earthquake, parent, false);
        return new EarthQuakeItemRecyclerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        EarthQuakeItem earthQuakeItem = earthQuakeItems.get(position);
        holder.title.setText(earthQuakeItem.getTitle());
        holder.itemPosition = position;
    }

    @Override
    public int getItemCount() {
        return earthQuakeItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public int itemPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.earthquake_item_title);
            this.itemPosition = 0;
            itemView.setOnClickListener((View v) -> {
                Intent intent = new Intent(context, EarthQuakeItemDetail.class);
                intent.putExtra("EarthQuake_Item_Description", earthQuakeItems.get(this.itemPosition).getDescription());
                intent.putExtra("latitude", earthQuakeItems.get(this.itemPosition).getLatitude());
                intent.putExtra("longitude", earthQuakeItems.get(this.itemPosition).getLongitude());
                intent.putExtra("title", earthQuakeItems.get(this.itemPosition).getTitle());
                intent.putExtra("EarthQuakeListItem", (Serializable)earthQuakeItems);
                context.startActivity(intent);


            });
        }
    }
}
