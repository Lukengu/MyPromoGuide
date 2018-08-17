package mypromoguide.pro.novatechsolutions.app.mypromoguide.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import java.util.List;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.R;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.listeners.RecycleViewAdapterItemListener;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.utils.AppTypeFace;
import mypromoguide.pro.novatechsolutions.app.promo_api.entity.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.StoreListViewHolder> {

    private Context mContext;
    private List<Product>  productList;
    private RecycleViewAdapterItemListener recycleViewAdapterItemListener;

    public ProductAdapter(Context mContext, List<Product> productList) {
        this.mContext = mContext;
        this.productList = productList;
        // Toast.makeText(mContext, "Adapter"+ storeList.size(), Toast.LENGTH_LONG).show();
    }

    public void setOnRecycleViewAdapterItemListener(RecycleViewAdapterItemListener recycleViewAdapterItemListener ){
        this.recycleViewAdapterItemListener = recycleViewAdapterItemListener;
    }

    @Override
    public StoreListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_grid_view, parent, false);
        final StoreListViewHolder mViewHolder = new StoreListViewHolder(mView);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycleViewAdapterItemListener.onItemClick(v, mViewHolder.getAdapterPosition());
            }
        });
        return mViewHolder;

    }

    public Product getItem(int position){
        return productList.get(position);
    }


    @Override
    public void onBindViewHolder(StoreListViewHolder holder, int position) {
       Product product = productList.get(position);

        Glide.with(mContext).load(product.getAvatar()).into(holder.avatar);

        Typeface plain = AppTypeFace.NewInstance(mContext).getTypeFace();
       // holder.brand_name.setSelected(true);
        holder.brand_name.setTypeface(plain);
        holder.brand_name.setText(product.getBrand_name());
        holder.name.setTypeface(plain);
        holder.conjonction.setTypeface(plain);

      //  holder.category_name.setTypeface(plain);
        holder.name.setText(product.getProduct_name());
        //holder.category_name.setText(product.getCategory_name());
        holder.brand_name.setText(product.getBrand_name());

        switch(product.getPromo_type_id()) {
            case 1 : // Was & Now
                holder.price.setText("Was R "+ product.getWas_price());
                holder.conjonction.setText("Now");
                holder.promo_price.setText("R "+ product.getNow_price());
                break;
            case 2 : // Buy  & Get
                holder.price.setText(" Buy for"+ product.getGet_product());
                holder.conjonction.setText("And Get");
                holder.promo_price.setText(product.getBuy_product());
                break;
            case 3 : // Buy  & Save
                holder.price.setText("Buy For "+ product.getBuy());
                holder.conjonction.setText("And Save");
                holder.promo_price.setText("R "+product.getSave());
                break;
            case 4 : // Bulks Deals
                holder.price.setText("Buy "+ product.getProduct_no());
                holder.conjonction.setText("For ");
                holder.promo_price.setText(""+ product.getProduct_price());
                break;
        }

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    class StoreListViewHolder extends RecyclerView.ViewHolder {

        ImageView avatar;
        TextView name,category_name,brand_name,price,promo_price,conjonction;

        public StoreListViewHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            name = itemView.findViewById(R.id.name);
          //  category_name  = itemView.findViewById(R.id.category_name);
            brand_name  = itemView.findViewById(R.id.brand_name);
            price  = itemView.findViewById(R.id.price);
            promo_price  = itemView.findViewById(R.id.promo_price);
            conjonction  = itemView.findViewById(R.id.conjonction);

        }

    }

}