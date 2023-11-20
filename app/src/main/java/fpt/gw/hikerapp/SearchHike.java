package fpt.gw.hikerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;


public class SearchHike extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private RecyclerView rvcTrips;
    private SearchAdapter searchAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_hike);

        databaseHelper = new DatabaseHelper(this, "hikeApp.sqlite", null, 1);

        rvcTrips = findViewById(R.id.recyclerViewTrips);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvcTrips.setLayoutManager(linearLayoutManager);

        searchAdapter = new SearchAdapter(getListTrips());
        rvcTrips.setAdapter(searchAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvcTrips.addItemDecoration(itemDecoration);
    }

    private List<Hike> getListTrips() {
        Cursor dataHike = databaseHelper.GetData("SELECT * FROM Hike");
        List<Hike> arrayTrip = new ArrayList<>();
        arrayTrip.clear(); //Clear array de khong bi lap lai trip
        while (dataHike.moveToNext()){
            String hikeName = dataHike.getString(1);
            String location = dataHike.getString(2);
            String length = dataHike.getString(3);
            String difficulty = dataHike.getString(4);
            String dateStart = dataHike.getString(5);
            String dateEnd = dataHike.getString(6);
            String risk = dataHike.getString(7);
            String description = dataHike.getString(8);
            int id = dataHike.getInt(0);
            arrayTrip.add(new Hike(id, hikeName, location, length, difficulty, dateStart, dateEnd, risk, description));
        }
        return arrayTrip;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_trip, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.searchTripName).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }
}