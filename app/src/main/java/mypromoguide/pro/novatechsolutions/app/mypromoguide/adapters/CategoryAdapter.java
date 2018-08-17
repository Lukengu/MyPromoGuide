package mypromoguide.pro.novatechsolutions.app.mypromoguide.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mypromoguide.pro.novatechsolutions.app.mypromoguide.R;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.listeners.RecycleViewAdapterItemListener;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.utils.AppTypeFace;
import mypromoguide.pro.novatechsolutions.app.promo_api.entity.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context mContext;
    private List<Category> categories;
    private RecycleViewAdapterItemListener recycleViewAdapterItemListener;

    public CategoryAdapter(Context mContext,List<Category> categories) {
        this.mContext = mContext;
        this.categories = categories;
       // Toast.makeText(mContext, "Adapter"+ storeList.size(), Toast.LENGTH_LONG).show();
    }
    public void setOnRecycleViewAdapterItemListener(RecycleViewAdapterItemListener recycleViewAdapterItemListener ){
        this.recycleViewAdapterItemListener = recycleViewAdapterItemListener;
    }
    public Category getItem(int position){
        return categories.get(position);
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_view, parent, false);
        final CategoryAdapter.CategoryViewHolder mViewHolder = new CategoryAdapter.CategoryViewHolder(mView);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycleViewAdapterItemListener.onItemClick(v, mViewHolder.getAdapterPosition());
            }
        });
        return mViewHolder;
    }



    @Override
    public void onBindViewHolder( CategoryViewHolder holder, int position) {
       /* Glide
                .with(mContext)
                .load(storeList.get(position).getAvatar())


                // .centerCrop()
                //.placeholder(R.drawable.loading_spinner)
                .into(holder.mImage);*/

      //  Glide.with(mContext).load(storeList.get(position).getAvatar()).into(holder.mImage);

        //Log.i("Image",  storeList.get(position).getAvatar() );
      //  Toast.makeText(mContext, storeList.get(position).getAvatar(), Toast.LENGTH_LONG).show();
       // Toast.makeText(mContext, storeList.get(position).getName(), Toast.LENGTH_LONG).show();
        Category category = categories.get(position);


        Typeface plain = AppTypeFace.NewInstance(mContext).getTypeFace();
        //holder.category_name.setSelected(true);
        holder.category_name.setTypeface(plain);
        StringBuilder sb = new StringBuilder("");
        sb.append(category.getCategory())
                .append("(")
                .append(category.getPromotion_count())
                .append(")");

        holder.category_name.setText(sb.toString());
        holder.brand_name.setText("");
    }





    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView category_name, brand_name;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            category_name = itemView.findViewById(R.id.category_name);
            brand_name = itemView.findViewById(R.id.brand_name);

        }

    }
}