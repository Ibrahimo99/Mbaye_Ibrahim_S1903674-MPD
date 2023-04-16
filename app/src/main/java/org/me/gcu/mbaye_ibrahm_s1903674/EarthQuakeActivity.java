package org.me.gcu.mbaye_ibrahm_s1903674;;
//  Ibrahim Mbaye S1903674

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EarthQuakeActivity extends AppCompatActivity {

    private RecyclerView earthQuakeInfoView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earth_quake);
        Log.e("MyTag","in onCreate");
        Log.e("MyTag","after startButton");
        // More Code goes here

        Intent i = getIntent();
        List<EarthQuakeInfo> list = (List<EarthQuakeInfo>) i.getSerializableExtra("listEarthQuakeData");

        earthQuakeInfoView = (RecyclerView) findViewById(R.id.listEarthQuakeInfo);
        earthQuakeInfoView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        earthQuakeInfoView.setAdapter(new EarthQuakeInfoRecyclerAdapter(this, list));

    }

    public void onBackPressed() {
        // super.onBackPressed();
        Toast.makeText(EarthQuakeActivity.this,"There is no back action",Toast.LENGTH_LONG).show();
        return;
    }
}
