package mypromoguide.pro.novatechsolutions.app.mypromoguide;



import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar ;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import mypromoguide.pro.novatechsolutions.app.mypromoguide.fragments.Product;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.utils.AppTypeFace;
import mypromoguide.pro.novatechsolutions.app.promo_api.entity.Store;

public class SingleProduct extends AppCompatActivity {

    ImageView avatar;
    TextView name,category_name,brand_name,price,promo_price,conjonction,store_name,operating_times;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        avatar = findViewById(R.id.avatar);
        name = findViewById(R.id.name);
        //  category_name  = itemView.findViewById(R.id.category_name);
        brand_name  = findViewById(R.id.brand_name);
        price  = findViewById(R.id.price);
        promo_price  = findViewById(R.id.promo_price);
        conjonction  = findViewById(R.id.conjonction);
        store_name = findViewById(R.id.store_name);
        operating_times = findViewById(R.id.operating_times);

      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        mypromoguide.pro.novatechsolutions.app.promo_api.entity.Product  product =
                (mypromoguide.pro.novatechsolutions.app.promo_api.entity.Product )
                        getIntent().getExtras().getSerializable("product");

        Store store =   (Store) getIntent().getExtras().getSerializable("store");



        Glide.with(this).load(product.getAvatar()).into(avatar);
        setTitle(product.getProduct_name());
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.header_background));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        Typeface plain = AppTypeFace.NewInstance(this).getTypeFace();
        // holder.brand_name.setSelected(true);
      brand_name.setTypeface(plain);
      brand_name.setText(product.getBrand_name());
      name.setTypeface(plain);
      conjonction.setTypeface(plain);

      operating_times.setTypeface(plain);
      store_name.setTypeface(plain);

        StringBuilder store_infos_name = new StringBuilder("");
        StringBuilder operating_time_infos = new StringBuilder("");

        store_infos_name.append(store.getName());

        if(!TextUtils.isEmpty(store.getShopping_center()))
            store_infos_name.append("-").append(store.getShopping_center());

        store_infos_name.append("<br />").append(store.getAddress());

        operating_time_infos.append("Weekdays : ").append(store.getOt_weekdays()).append("<br />")
                .append("Saturdays : ").append(store.getOt_saturdays()).append("<br />")
                .append("Sundays : ").append(store.getOt_sundays()).append("<br />")
                .append("Public Holidays : ").append(store.getOt_public_holidays()).append("<br />");


      store_name.setText(Html.fromHtml(store_infos_name.toString()));
      operating_times.setText(Html.fromHtml(operating_time_infos.toString()));

        //category_name.setTypeface(plain);
      name.setText(product.getProduct_name());
        //holder.category_name.setText(product.getCategory_name());
      brand_name.setText(product.getBrand_name());

        switch(product.getPromo_type_id()) {
            case 1 : // Was & Now
                price.setText("Was R "+ product.getWas_price());
                conjonction.setText("Now");
                promo_price.setText("R "+ product.getNow_price());
                break;
            case 2 : // Buy  & Get
                price.setText(" Buy for"+ product.getGet_product());
                conjonction.setText("And Get");
                promo_price.setText(product.getBuy_product());
                break;
            case 3 : // Buy  & Save
                price.setText("Buy For "+ product.getBuy());
                conjonction.setText("And Save");
                promo_price.setText("R "+product.getSave());
                break;
            case 4 : // Bulks Deals
                price.setText("Buy "+ product.getProduct_no());
                conjonction.setText("For ");
                promo_price.setText(""+ product.getProduct_price());
                break;
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
            return false;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            //do actions like show message
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
