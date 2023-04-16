package org.me.gcu.mbaye_ibrahm_s1903674;
//  Ibrahim Mbaye S1903674

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;


public class EarthQuakeItemDetail extends AppCompatActivity {

    private TextView earthquakeDescription;
    private TextView desc;
    List<EarthQuakeItem> list;
    private String latitude;
    private String longitude;
    private String title;
    private Button startButton;
    String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake_item_detail);

        Intent intent = getIntent();
        list = (List<EarthQuakeItem>) intent.getSerializableExtra("EarthQuakeListItem");
        description = intent.getStringExtra("EarthQuake_Item_Description");
        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");
        title = intent.getStringExtra("title");
        earthquakeDescription = findViewById(R.id.earthquake_description);
        earthquakeDescription.setText(description);
        desc = findViewById(R.id.desc);

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(this::onClick);
    }


     public void onClick(View view) {
         Intent i = new Intent(this, MapsActivity.class);  // get a valid context
         i.putExtra("latitude", latitude);
         i.putExtra("longitude", longitude);
         i.putExtra("title", title);
         startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.searchInfo:
                Intent intentList = new Intent(this, SearchItem.class);
                intentList.putExtra("EarthQuakeListItem", (Serializable) list);
                this.startActivity(intentList);
                return true;
            case R.id.moreInfo:
                Bundle b = new Bundle();
                MoreInfoFragment newFragment = MoreInfoFragment.newInstance(list);
                desc.setText("");
                earthquakeDescription.setText("");
                startButton.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, newFragment).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}