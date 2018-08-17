package mypromoguide.pro.novatechsolutions.app.mypromoguide.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import java.util.ArrayList;
import java.util.List;

import mypromoguide.pro.novatechsolutions.app.mypromoguide.Content;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.R;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.SingleProduct;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.adapters.ProductAdapter;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.listeners.RecycleViewAdapterItemListener;
import mypromoguide.pro.novatechsolutions.app.promo_api.entity.Store;
import mypromoguide.pro.novatechsolutions.app.promo_api.request.ProductRequest;
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.ClientException;
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.OnServiceResponseListener;

public class Product  extends Fragment implements OnServiceResponseListener, RecycleViewAdapterItemListener {
    RecyclerView mRecyclerView;
    List<mypromoguide.pro.novatechsolutions.app.promo_api.entity.Product> productList = new ArrayList<>();
    ProductAdapter productAdapter;
    Store store;
    Button btn_see_store;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product,
                container, false);

        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        productAdapter = new ProductAdapter(getActivity(),productList );
        btn_see_store = view.findViewById(R.id.btn_see_store);
        productAdapter.setOnRecycleViewAdapterItemListener(this);
        mRecyclerView.setAdapter(productAdapter);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);




        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onStart() {
        fillProduct();
        super.onStart();
    }


    private void fillProduct()
    {
        final Bundle bundle =  getArguments();

        final mypromoguide.pro.novatechsolutions.app.promo_api.entity.Category category =
                ( mypromoguide.pro.novatechsolutions.app.promo_api.entity.Category) bundle.getSerializable("category");


        getActivity().setTitle(category.getCategory());




        btn_see_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((Content) getActivity()).loadFragment("map", bundle);
            }
        });


        store = (Store) bundle.getSerializable("store");
        int store_id = bundle.getInt("store_id");
        new ProductRequest(this).promos(category.getCategory_id(), store_id);

    }



    @Override
    public void onSuccess(Object object) {
        productList.addAll((List<mypromoguide.pro.novatechsolutions.app.promo_api.entity.Product>) object);
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(ClientException e) {

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
            Bundle bundle = new Bundle();
            bundle.putSerializable("store",store);

            ((Content) getActivity()).loadFragment("category", bundle);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(View v, int position) {

        mypromoguide.pro.novatechsolutions.app.promo_api.entity.Product product
                =  productAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);
        bundle.putSerializable("store", store);

        getActivity().startActivity(new Intent(getActivity(), SingleProduct.class).putExtras(bundle));


    }
}
