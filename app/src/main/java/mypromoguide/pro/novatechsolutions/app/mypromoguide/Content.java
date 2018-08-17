package mypromoguide.pro.novatechsolutions.app.mypromoguide;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import mypromoguide.pro.novatechsolutions.app.mypromoguide.fragments.Category;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.fragments.Home;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.fragments.Product;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.fragments.Stores;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.fragments.StoresMap;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.utils.AppPreferences;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.utils.AppTypeFace;

public class Content extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AppPreferences appPreferences;
    private Typeface plain;
    private Typeface bold;
    private LocationManager locationManager;

    private final int REQUEST_PERMISSION_LOCATION_STATE = 1;
    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.GONE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.textView);
        appPreferences = AppPreferences.NewInstance(Content.this);
        plain = AppTypeFace.NewInstance(Content.this).getTypeFace();
        bold = AppTypeFace.NewInstance(Content.this).getBoldTypeFace();
        navUsername.setTypeface(plain);
        try {
            JSONObject user_infos = new JSONObject(appPreferences.getUser());
            navUsername.setText("Welcome, ".concat(user_infos.getString("name")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //  if (ActivityCompat.shouldShowRequestPermissionRationale(this,
            //  Manifest.permission.ACCESS_FINE_LOCATION) {
            showExplanation("Location need to be enabled", "Your location settings needs to be enabled for the app to work", Manifest.permission.READ_PHONE_STATE, REQUEST_PERMISSION_LOCATION_STATE);
            // } else {
            //   requestPermission(Manifest.permission.READ_PHONE_STATE, REQUEST_PERMISSION_LOCATION_STATE);

        } else {
          //  locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            loadFragment("home", null);
        }


    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED  ||
                            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.

                        loadFragment("home", null);
                    }

                   // Toast.makeText(MainActivity.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                } else {
                   // Toast.makeText(MainActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }



    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermissions(INITIAL_PERMS, permissionRequestCode);
                    }
                });
        builder.create().show();
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadFragment(String name, @Nullable Bundle bundle) {

        Fragment f = null;
        //String tag = "";

        if( "home".equals(name)){

            // insert detail fragment into detail container
            f = new Home();
           // tag = "home";
//            ((Home)f).load();
            setTitle("Stores");

        }
        if( "category".equals(name)){

            // insert detail fragment into detail container
            f = new Category();
            //tag = "category";
          //  setTitle("Stores");

        }
        if( "product".equals(name)){

            // insert detail fragment into detail container
            f = new Product();
            //tag = "product";
            //  setTitle("Stores");

        }
        if( "map".equals(name)){

            // insert detail fragment into detail container
           f = new StoresMap();
            //tag = "product";
            //  setTitle("Stores");

        }

        if(bundle != null)
            f.setArguments(bundle);


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.flContent, f)
                .commit();


    }
}
