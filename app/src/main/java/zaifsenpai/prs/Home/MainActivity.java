package zaifsenpai.prs.Home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import zaifsenpai.prs.General.Recommendation;
import zaifsenpai.prs.General.RecommendationAdapter;
import zaifsenpai.prs.General._Methods;
import zaifsenpai.prs.General._Properties;
import zaifsenpai.prs.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean doubleBackToExitPressedOnce = false;
    private RecyclerView RecommendationsRecyclerView;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Recommendation> recommendationList;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(MainActivity.this));

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recommendationList = new ArrayList<>();
        adapter = new RecommendationAdapter(getApplicationContext(), recommendationList);
        RecommendationsRecyclerView = findViewById(R.id.RecommendationsList);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(RecommendationsRecyclerView.getContext(), linearLayoutManager.getOrientation());

        RecommendationsRecyclerView.setHasFixedSize(true);
        RecommendationsRecyclerView.setLayoutManager(linearLayoutManager);
        RecommendationsRecyclerView.addItemDecoration(dividerItemDecoration);
        RecommendationsRecyclerView.setAdapter(adapter);

        LoadRecommendations();
    }

    private void LoadRecommendations() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest;
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Recommendations...");
        progressDialog.show();

        if (_Methods.hasPermissions(this, _Properties.permissions)) {
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    _Properties.SERVER_Recommendation_API_ADDRESS,
                    null,
                    new Response.Listener<JSONObject>() {
                        Recommendation recommendation;
                        JSONArray array;
                        JSONObject object;

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                array = response.getJSONArray("objects");
                                for (int i = 0; i < array.length(); i++) {
                                    object = (JSONObject) array.get(i);
                                    recommendation = new Recommendation();

                                    recommendation.Name = object.getString("Name");
                                    recommendation.Image = object.getString("Image");
                                    recommendation.Url = object.getString("Url");

                                    recommendationList.add(recommendation);
                                }
                                adapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                            } catch (Exception e) {
                                progressDialog.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(_Properties.LOG_TAG, error.getMessage());
                    progressDialog.dismiss();
                }
            });
            queue.add(jsonObjectRequest);
        } else {
            Toast.makeText(this, "Unable to load recommendations.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        }
//        else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to leave", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void share_onClickListener(MenuItem item) {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Checkout this app!");
            String sAux = "\nLet me recommend you this amazing application.!\n\n";
            sAux = sAux + "http://play.google.com/store/apps/details?id=zaifsenpai.prs\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Choose one"));
        } catch (Exception ignored) { }
    }
}
