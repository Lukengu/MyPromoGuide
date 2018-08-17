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
import mypromoguide.pro.novatechsolutions.app.promo_api.entity.Store;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreListViewHolder> {

    private Context mContext;
    private List<Store> storeList;
    private RecycleViewAdapterItemListener recycleViewAdapterItemListener;

    public StoreAdapter(Context mContext, List<Store> storeList) {
        this.mContext = mContext;
        this.storeList = storeList;
        // Toast.makeText(mContext, "Adapter"+ storeList.size(), Toast.LENGTH_LONG).show();
    }

    public void setOnRecycleViewAdapterItemListener(RecycleViewAdapterItemListener recycleViewAdapterItemListener ){
        this.recycleViewAdapterItemListener = recycleViewAdapterItemListener;
    }

    @Override
    public StoreListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_view, parent, false);
        final StoreListViewHolder mViewHolder = new StoreListViewHolder(mView);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycleViewAdapterItemListener.onItemClick(v, mViewHolder.getAdapterPosition());
            }
        });
        return mViewHolder;

    }

    public Store getItem(int position){
        return storeList.get(position);
    }


    @Override
    public void onBindViewHolder(StoreListViewHolder holder, int position) {
       /* Glide
                .with(mContext)
                .load(storeList.get(position).getAvatar())


                // .centerCrop()
                //.placeholder(R.drawable.loading_spinner)
                .into(holder.mImage);*/

        Glide.with(mContext).load(storeList.get(position).getAvatar()).into(holder.mImage);

        //Log.i("Image",  storeList.get(position).getAvatar() );
        //  Toast.makeText(mContext, storeList.get(position).getAvatar(), Toast.LENGTH_LONG).show();
        // Toast.makeText(mContext, storeList.get(position).getName(), Toast.LENGTH_LONG).show();

        Typeface bold = AppTypeFace.NewInstance(mContext).getBoldTypeFace();
        holder.mTitle.setSelected(true);
        holder.mTitle.setTypeface(bold);
        StringBuilder sb = new StringBuilder("");
        sb.append(storeList.get(position).getName())
                .append("(")
                .append(storeList.get(position).getPromotion_count())
                .append(")");

        holder.mTitle.setText(sb.toString());
    }


    @Override
    public int getItemCount() {
        return storeList.size();
    }

    class StoreListViewHolder extends RecyclerView.ViewHolder {

        ImageView mImage;
        TextView mTitle;

        public StoreListViewHolder(View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.avatar);
            mTitle = itemView.findViewById(R.id.tvTitle);

        }



    }



}