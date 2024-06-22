package com.example.shotokansyllabus;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ExpandableListView expandableListView;
    private HashMap<String, HashMap<String, List<String>>> expandableListDetail;
    private List<String> expandableListTitle;
    private Map<String, String> beltColours; // Store belt colors
    private CustomExpandableListAdapter expandableListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        expandableListView = findViewById(R.id.expandableListView);

        expandableListDetail = getDataFromJson();
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        // Sort the list in descending order
        Collections.sort(expandableListTitle, Collections.reverseOrder());

        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail, beltColours);
        expandableListView.setAdapter(expandableListAdapter);

        // Set "All" radio button checked by default and update list
        radioGroup.check(R.id.radioAll);
        updateListForCategory("All");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioAll) {
                    updateListForCategory("All");
                } else if (checkedId == R.id.radioBasics) {
                    updateListForCategory("basics");
                } else if (checkedId == R.id.radioKicks) {
                    updateListForCategory("kicks");
                } else if (checkedId == R.id.radioKata) {
                    updateListForCategory("kata");
                } else if (checkedId == R.id.radioKumite) {
                    updateListForCategory("kumite");
                }
                else if (checkedId == R.id.radioCombinations){
                    updateListForCategory("combinations");
                }
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int len = expandableListAdapter.getGroupCount();
                for (int i = 0; i < len; i++) {
                    if (i != groupPosition) {
                        expandableListView.collapseGroup(i);
                    }
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        /*
        switch (item.getItemId()) {
            case R.id.nav_home:
                // Handle navigation view item clicks here.
                break;
        }
        */
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private HashMap<String, HashMap<String, List<String>>> getDataFromJson() {
        HashMap<String, HashMap<String, List<String>>> expandableListDetail = new LinkedHashMap<>();
        beltColours = new LinkedHashMap<>();
        try {
            InputStream is = getAssets().open("karate_syllabus.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String grade = obj.getString("grade");
                String beltColour = obj.getString("beltColour");
                beltColours.put(grade, beltColour); // Store belt colour
                expandableListDetail.put(grade, new HashMap<>());
                List<String> basics = new ArrayList<>();
                List<String> kicks = new ArrayList<>();
                List<String> kata = new ArrayList<>();
                List<String> kumite = new ArrayList<>();
                List<String> combinations = new ArrayList<>();

                for (int j = 0; j < obj.getJSONArray("basics").length(); j++) {
                    basics.add(obj.getJSONArray("basics").getString(j));
                }

                for (int j = 0; j < obj.getJSONArray("kicks").length(); j++) {
                    kicks.add(obj.getJSONArray("kicks").getString(j));
                }

                for (int j = 0; j < obj.getJSONArray("kata").length(); j++) {
                    kata.add(obj.getJSONArray("kata").getString(j));
                }

                for (int j = 0; j < obj.getJSONArray("kumite").length(); j++) {
                    kumite.add(obj.getJSONArray("kumite").getString(j));
                }

                for (int j = 0; j < obj.getJSONArray("combinations").length(); j++) {
                    combinations.add(obj.getJSONArray("combinations").getString(j));
                }

                expandableListDetail.get(grade).put("basics", basics);
                expandableListDetail.get(grade).put("kicks", kicks);
                expandableListDetail.get(grade).put("kata", kata);
                expandableListDetail.get(grade).put("kumite", kumite);
                expandableListDetail.get(grade).put("combinations", combinations);
            }
        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
        }
        return expandableListDetail;
    }

    private void updateListForCategory(String category) {
        HashMap<String, HashMap<String, List<String>>> filteredDetail = new LinkedHashMap<>();

        if (category.equals("All")) {
            filteredDetail = expandableListDetail;
        } else {
            for (String grade : expandableListDetail.keySet()) {
                HashMap<String, List<String>> details = expandableListDetail.get(grade);
                List<String> categoryList = details.get(category);
                if (categoryList != null && !categoryList.isEmpty()) {
                    HashMap<String, List<String>> filteredCategoryDetail = new HashMap<>();
                    filteredCategoryDetail.put(category, categoryList);
                    filteredDetail.put(grade, filteredCategoryDetail);
                }
            }
        }

        expandableListAdapter.updateData(new ArrayList<>(filteredDetail.keySet()), filteredDetail, beltColours);
    }
}
