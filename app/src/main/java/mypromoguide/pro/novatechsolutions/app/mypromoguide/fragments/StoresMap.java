package mypromoguide.pro.novatechsolutions.app.mypromoguide.fragments;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import mypromoguide.pro.novatechsolutions.app.mypromoguide.Content;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.R;
import mypromoguide.pro.novatechsolutions.app.promo_api.entity.Store;
import mypromoguide.pro.novatechsolutions.app.promo_api.request.StoreRequest;
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.ClientException;
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.OnServiceResponseListener;

public class StoresMap   extends Fragment implements OnMapReadyCallback, LocationListener, OnServiceResponseListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private boolean isChecked = true;
    private Location location;
    private Store  store;

    private GoogleMap mMap;
    private Bundle bundle;
    private boolean from_search;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps,
                container, false);

        bundle =  getArguments();
        store = (Store) bundle.getSerializable("store");
        getActivity().setTitle(store.getName()+" "+store.getShopping_center());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setHasOptionsMenu(true);
        SharedPreferences spref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        from_search = spref.getBoolean("from_search", false);

        return view;
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

        if (id == R.id.action_back) {


            ((Content) getActivity()).loadFragment("product", bundle);

            return true;
        }




        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here

        menu.findItem(R.id.action_back).setVisible(true);
        inflater.inflate(R.menu.content, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {

    }
    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpMapIfNeeded();
    }
    @Override public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.

            // Try to obtain the map from the SupportMapFragment.
            SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);

    }

    private void setUpMap(boolean from_search) {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(store.getLat(), store.getLng())).title("Marker"));
        // new StoreRequest(this).store_by_radius(location.getLatitude(), location.getLongitude());

        Double longitude =  from_search ? store.getLng() : location.getLongitude();
        Double latitude = from_search ? store.getLat() : location.getLatitude();

        new StoreRequest( new OnServiceResponseListener(){

            @Override
            public void onSuccess(Object object) {
                List<Store> stores =  (List<Store> ) object;


                for(Store s : stores){
                    LatLng store_location = new LatLng(s.getLat(), s.getLng());
                    Log.i("distance", "" + s.getDistance());
                    Marker m = mMap.addMarker(new MarkerOptions()
                            .position(store_location)
                            .title(s.getName().concat(" ").concat(s.getShopping_center()))
                            .snippet(s.getPromotion_count() + " Promotion(s) - Distance : " + s.getDistance() + " km")
                    );
                    m.showInfoWindow();
                    m.setTag(s);
                }

            }

            @Override
            public void onFailure(ClientException e) {

            }
        }).nearby_store(store.getId(),latitude,longitude);

        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);







    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LocationManager mLocationManager = (LocationManager)
                getActivity().getSystemService(((Content) getActivity()).LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
                    0, this);

            location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
           //   Toast.makeText(getActivity(), "Location "+location.getLatitude(), Toast.LENGTH_LONG).show();
            LatLng current;
            if(from_search) {
                current = new LatLng(store.getLat(), store.getLng());

            } else {
                current = new LatLng(location.getLatitude(), location.getLongitude());

            }
            //  mMap.addMarker(new MarkerOptions().position(current).title("Marker in Sydney"));
            setUpMap(from_search);

            mMap.animateCamera( CameraUpdateFactory.newLatLngZoom(current, 12.0f));

        }




    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onSuccess(Object object) {
        List<Store> stores = (List<Store>) object;
        if(stores.size() == 0 ){
            Toast.makeText(getActivity(), "No stores with promotion  have been found in the nearest 6 KM \n Please" , Toast.LENGTH_LONG).show();
        } else {

        }
    }

    @Override
    public void onFailure(ClientException e) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {


        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Store store = (Store) marker.getTag();
        Bundle bundle = new Bundle();
        bundle.putSerializable("store",store);

        ((Content) getActivity()).loadFragment("category", bundle);
    }
}
