package zaifsenpai.prs.Home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
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
import zaifsenpai.prs.General.SmsGetService;
import zaifsenpai.prs.General.SmsUploadService;
import zaifsenpai.prs.General._Methods;
import zaifsenpai.prs.General._Properties;
import zaifsenpai.prs.R;
import zaifsenpai.prs.Welcome.WelcomeActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean doubleBackToExitPressedOnce = false;
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

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent i = new Intent(this, WelcomeActivity.class);
        startActivityForResult(i, 111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            try {
                ShowRecommendations();
                // Get permissions
                ActivityCompat.requestPermissions(this, _Properties.permissions, 1);
            } catch (Exception e) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setMessage(e.getMessage());
                alertDialog.setTitle("Error occurred");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.setCancelable(true);
                alertDialog.create().show();
            }
        } else {
            finish();
        }
    }

    private void ShowRecommendations() {
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(MainActivity.this));

        recommendationList = new ArrayList<>();
        adapter = new RecommendationAdapter(getApplicationContext(), recommendationList);
        RecyclerView recommendationsRecyclerView = findViewById(R.id.RecommendationsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recommendationsRecyclerView.getContext(), linearLayoutManager.getOrientation());

        recommendationsRecyclerView.setHasFixedSize(true);
        recommendationsRecyclerView.setLayoutManager(linearLayoutManager);
        recommendationsRecyclerView.addItemDecoration(dividerItemDecoration);
        recommendationsRecyclerView.setAdapter(adapter);

        LoadRecommendations();
    }

    private void LoadRecommendations() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest;
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Recommendations...");
        progressDialog.show();

        try {
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    _Properties.DJANGO_SERVER_Recommendation_API_ADDRESS,
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
                                    recommendation.Price = object.getString("Price");

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
        } catch (Exception e) {
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
            finish();
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
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (_Methods.hasPermissions(this, permissions)) {
            startService(new Intent(MainActivity.this, SmsGetService.class));
            startService(new Intent(MainActivity.this, SmsUploadService.class));
        } else
            Toast.makeText(MainActivity.this, "Unable to start services.", Toast.LENGTH_LONG).show();
    }
}
