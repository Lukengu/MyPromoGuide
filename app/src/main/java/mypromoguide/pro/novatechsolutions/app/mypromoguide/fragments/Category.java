
package mypromoguide.pro.novatechsolutions.app.mypromoguide.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import mypromoguide.pro.novatechsolutions.app.mypromoguide.adapters.CategoryAdapter;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.listeners.RecycleViewAdapterItemListener;
import mypromoguide.pro.novatechsolutions.app.promo_api.entity.Store;
import mypromoguide.pro.novatechsolutions.app.promo_api.request.CategoryRequest;
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.ClientException;
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.OnServiceResponseListener;

public class Category extends Fragment implements OnServiceResponseListener, RecycleViewAdapterItemListener {



    RecyclerView mRecyclerView;
    List<mypromoguide.pro.novatechsolutions.app.promo_api.entity.Category> categories = new ArrayList<>();
    CategoryAdapter categoryAdapter;
    Store store;
    MenuItem back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stores,
                container, false);

        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        categoryAdapter = new CategoryAdapter(getActivity(), categories);
        categoryAdapter.setOnRecycleViewAdapterItemListener(this);
        mRecyclerView.setAdapter(categoryAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        Bundle b = getArguments();
        store = (Store) b.getSerializable("store");

        getActivity().setTitle(store.getName()+" "+store.getShopping_center());

        new CategoryRequest(this).category_list(store.getId());

        setHasOptionsMenu(true);


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here


        menu.findItem(R.id.action_back).setVisible(true);
        inflater.inflate(R.menu.content, menu);
        super.onCreateOptionsMenu(menu, inflater);

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
            ((Content) getActivity()).loadFragment("home", null);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(Object object) {
        categories.addAll((List<mypromoguide.pro.novatechsolutions.app.promo_api.entity.Category>) object);
        categoryAdapter.notifyDataSetChanged();


    }

    @Override
    public void onFailure(ClientException e) {

    }

    @Override
    public void onItemClick(View v, int position) {
        mypromoguide.pro.novatechsolutions.app.promo_api.entity.Category category =
                categoryAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("category", category);
        bundle.putSerializable("store", store);
        bundle.putInt("store_id", store.getId());
       // Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG).show();
        ((Content) getActivity()).loadFragment("product", bundle);



    }
}
