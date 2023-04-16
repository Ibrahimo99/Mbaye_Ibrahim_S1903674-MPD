package org.me.gcu.mbaye_ibrahm_s1903674;
//  Ibrahim Mbaye S1903674

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SearchItem extends AppCompatActivity {

    // List View object
    ListView listView;

    // Define array adapter for ListView
    ArrayAdapter<String> adapter;

    // Define array List for List View data
    List<EarthQuakeItem> listItems;
    String itemDescription;
    ArrayList<String> itemDescriptions;
    MapsActivity maps;
    String latitude;
    String longitude;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);

        // initialise ListView with id
        listView = findViewById(R.id.listView);
        Intent i = getIntent();
        // Add items to Array List
        listItems = (List<EarthQuakeItem>) i.getSerializableExtra("EarthQuakeListItem");
        itemDescriptions = new ArrayList<>();

        for(int j=0;j<listItems.size();j++){
        itemDescription = listItems.get(j).getDescription();
        itemDescriptions.add(itemDescription);
        }

        System.out.println("itemsDescription"+itemDescriptions);

        // Set adapter to ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemDescriptions);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {

                //get latitude and longitude from clicked item after search
                String detail = (String)adapter.getItemAtPosition(position);
                title = detail.split(";")[1];
                detail = detail.split(";")[2].replaceAll("^[\\d,.]+$", "");
                latitude = detail.split(",")[0].replaceAll("[^0-9.]", "");
                longitude = detail.split(",")[1];

                //start intent
                Intent i = new Intent(v.getContext(), MapsActivity.class);  // get a valid context
                i.putExtra("latitude", latitude);
                i.putExtra("longitude", longitude);
                i.putExtra("title", title);
                startActivity(i);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu with items using MenuInflator
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        // Initialise menu item search bar
        // with id and take its object
        MenuItem searchViewItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);

        // attach setOnQueryTextListener
        // to search view defined above
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // Override onQueryTextSubmit method which is call when submit query is searched
            @Override
            public boolean onQueryTextSubmit(String query) {
                // If the list contains the search query than filter the adapter
                // using the filter method with the query as its argument
                if (itemDescriptions.contains(query)) {
                    adapter.getFilter().filter(query);
                } else {
                    // Search query not found in List View
                    Toast.makeText(SearchItem.this, "Not found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            // This method is overridden to filter the adapter according
            // to a search query when the user is typing search
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}