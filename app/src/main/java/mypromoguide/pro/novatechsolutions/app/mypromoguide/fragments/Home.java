package mypromoguide.pro.novatechsolutions.app.mypromoguide.fragments;

import android.Manifest;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import mypromoguide.pro.novatechsolutions.app.mypromoguide.Content;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.R;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.adapters.StoreAdapter;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.adapters.ViewPagerAdapter;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.adapters.indicators.PageIndicator;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.listeners.RecycleViewAdapterItemListener;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.utils.AppTypeFace;
import mypromoguide.pro.novatechsolutions.app.promo_api.entity.Store;
import mypromoguide.pro.novatechsolutions.app.promo_api.request.SearchRequest;
import mypromoguide.pro.novatechsolutions.app.promo_api.request.StoreRequest;
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.ClientException;
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.OnServiceResponseListener;

public class Home extends Fragment  implements RecycleViewAdapterItemListener, LocationListener, OnServiceResponseListener {

    private ViewPager viewPager;
    private Location location;
    private List<Store> storeList = new ArrayList<>();
    private final static int MAX_GRID_SIZE =  12;
    private LinearLayout indicators;
    private PageIndicator pageIndicator;
    private TextView no_location_store;
    private List<String> keywords = new ArrayList<>();
    private RecyclerView my_recycler_view;
    private   StoreAdapter storeAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home,
                container, false);
        viewPager = view.findViewById(R.id.viewpager);
        indicators = view.findViewById(R.id.indicators);
        my_recycler_view = view.findViewById(R.id.my_recycler_view);
        no_location_store = view.findViewById(R.id.no_location_store);
        no_location_store.setTypeface(AppTypeFace.NewInstance(getActivity()).getTypeFace());


        viewPager.setVisibility(View.VISIBLE);
        no_location_store.setVisibility(View.GONE);
        storeAdapter = new StoreAdapter(getActivity(), new ArrayList<Store>());
        load();




        return view;

    }




    public void load()
    {
        getCurrentLocation();

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        menu.findItem(R.id.action_location).setVisible(false);
        menu.findItem(R.id.menu_search).setVisible(true);



        inflater.inflate(R.menu.content, menu);


        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService( getActivity().SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));

        final SearchView.SearchAutoComplete searchAutoComplete =
                searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

        searchAutoComplete.setDropDownBackgroundResource(R.color.colorWhite);
        searchAutoComplete.setDropDownAnchor(R.id.menu_search);


        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoComplete.setText("" + queryString);


            }
        });

       // searchAutoComplete.setThreshold(0);
        String dataArr[] = keywords.toArray(new String[]{});

        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, dataArr);
       // ArrayAdapter<String> autoCompleteAdapter =
                new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, keywords.toArray(new String[]{}));
        searchAutoComplete.setAdapter(autoCompleteAdapter);




        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                new SearchRequest(new OnServiceResponseListener() {
                    @Override
                    public void onSuccess(Object object) {
                        showGrid((List<Store>) object, true);
                        //searchAutoComplete.setText("" + query);
                    }

                    @Override
                    public void onFailure(ClientException e) {

                    }
                }).search(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(final String query) {
                new SearchRequest(new OnServiceResponseListener() {
                    @Override
                    public void onSuccess(Object object) {
                        showGrid((List<Store>) object, true);
                        searchAutoComplete.setText("" + query);
                    }

                    @Override
                    public void onFailure(ClientException e) {

                    }
                }).search(query);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);

    }





    @Override
    public void onPrepareOptionsMenu(Menu menu) {


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        handleIntent(getActivity().getIntent());
        super.onCreate(savedInstanceState);
    }


    protected void onNewIntent(Intent intent) {
        getActivity().setIntent(intent);
        handleIntent(intent);
    }



    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            Toast.makeText(getActivity(), query, Toast.LENGTH_LONG).show();

            new SearchRequest(new OnServiceResponseListener() {
                @Override
                public void onSuccess(Object object) {
                    showGrid((List<Store>) object, true);
                }

                @Override
                public void onFailure(ClientException e) {

                }
            }).search(query);

        }
    }



    private void storeListing(){
        new StoreRequest(this).store_by_radius(location.getLatitude(), location.getLongitude());
    }

    private void getCurrentLocation() {

        LocationManager mLocationManager = (LocationManager)
                ((Content)getActivity()).getSystemService(((Content) getActivity()).LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
                    0, this);

            location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            storeListing();



        }
    }

    @Override
    public void onSuccess(Object object) {
        if( ((List<Store>) object).size() > 0 ) {
            no_location_store.setVisibility(View.GONE);
        }
        viewPager.setVisibility(View.VISIBLE);

        showGrid((List<Store>) object, false);
        new SearchRequest(new OnServiceResponseListener(){

            @Override
            public void onSuccess(Object object) {
                keywords = ( List<String> ) object;
                setHasOptionsMenu(true);
            }

            @Override
            public void onFailure(ClientException e) {

            }
        }).keywords();

    }


    private void  showGrid(List<Store> storeList, boolean fromsearch) {
        //this.storeList.clear();
        //this.storeList.addAll(storeList);

        Log.d("SHOWGRID", ""+storeList.size()+""+fromsearch);

        SharedPreferences spref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        spref.edit().putBoolean("from_search", fromsearch).commit();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());


       if(!fromsearch) {
           viewPager.setVisibility(View.VISIBLE);
           my_recycler_view.setVisibility(View.GONE);
           int size = storeList.size();

           if (size == 0) {
               viewPager.setVisibility(View.GONE);
               no_location_store.setVisibility(View.VISIBLE);
           }

           if (size > MAX_GRID_SIZE) {
               int pages = (int) Math.floor(size / MAX_GRID_SIZE) + 1;


               for (int i = 0; i < pages; i++) {


                   int offset = i == 0 ? (i * MAX_GRID_SIZE) : (i * MAX_GRID_SIZE) + 1;
                   int diff = size - offset;
                   int limit = (int) Math.floor(diff / MAX_GRID_SIZE) == 1 ? MAX_GRID_SIZE : offset + diff;
                   List<Store> s = storeList.subList(offset, limit);


                   // System.out.println(s.toArray().toString());
                   Stores fragment = new Stores();
                   fragment.setList(storeList);
                   //Bundle b = new Bundle();

                   adapter.addFrag(fragment, "");

               }

               adapter.notifyDataSetChanged();
               viewPager.setAdapter(adapter);

               pageIndicator = new PageIndicator(getActivity(), indicators, viewPager, R.drawable.indicators);
               pageIndicator.setPageCount(pages);
               pageIndicator.show();
           } else {

               Stores fragment = new Stores();
               fragment.setList(storeList);
               adapter.addFrag(fragment, "");
               adapter.notifyDataSetChanged();
               viewPager.setAdapter(adapter);
           }
       } else {
           viewPager.setVisibility(View.GONE);
           my_recycler_view.setVisibility(View.VISIBLE);
           GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        //   StoreAdapter storeAdapter = new StoreAdapter(getActivity(), storeList);

           if (this.storeList.size() == 0) {
               viewPager.setVisibility(View.GONE);
               no_location_store.setVisibility(View.VISIBLE);
           }
           storeAdapter = new StoreAdapter(getActivity(), this.storeList);
           storeAdapter.setOnRecycleViewAdapterItemListener(this);
           my_recycler_view.setAdapter(storeAdapter);
           my_recycler_view.setLayoutManager(mGridLayoutManager);
           my_recycler_view.setHasFixedSize(true);
           indicators.setVisibility(View.GONE);

       }
    }

    @Override
    public void onFailure(ClientException e) {

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
    public void onItemClick(View v, int position) {
        Store store = storeAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("store",store);

        ((Content) getActivity()).loadFragment("category", bundle);
    }
}
