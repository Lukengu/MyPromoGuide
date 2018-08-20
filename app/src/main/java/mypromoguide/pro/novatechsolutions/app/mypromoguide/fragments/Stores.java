package mypromoguide.pro.novatechsolutions.app.mypromoguide.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mypromoguide.pro.novatechsolutions.app.mypromoguide.Content;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.R;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.listeners.RecycleViewAdapterItemListener;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.adapters.StoreAdapter;
import mypromoguide.pro.novatechsolutions.app.promo_api.entity.Store;

public class Stores extends Fragment implements  RecycleViewAdapterItemListener {


    RecyclerView mRecyclerView;
    List<Store> storeList = new ArrayList<>();
    StoreAdapter storeAdapter;







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stores,
                container, false);



        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        storeAdapter = new StoreAdapter(getActivity(), storeList);
        storeAdapter.setOnRecycleViewAdapterItemListener(this);
        mRecyclerView.setAdapter(storeAdapter);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);


        setHasOptionsMenu(true);

      //  new StoreRequest(this).store_list();




        return view;
    }

    public void setList(List<Store> storeList) {
        this.storeList = storeList;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_location) {
            isChecked = !item.isChecked();
            item.setChecked(isChecked);
            item.setIcon(isChecked ? R.drawable.ic_location_on_black_24dp : R.drawable.ic_location_off_black_24dp);
            if(isChecked) {

                ((Content) getActivity()).loadFragment("stores", null);
            }



        }



        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        menu.findItem(R.id.action_location).setVisible(false);
        inflater.inflate(R.menu.content, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    private boolean isChecked = false;

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem checkable = menu.findItem(R.id.action_location);
        checkable.setChecked(isChecked);

    }


    @Override
    public void onItemClick(View v, int position) {
        Store store = storeAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("store",store);

        ((Content) getActivity()).loadFragment("category", bundle);
    }


}
