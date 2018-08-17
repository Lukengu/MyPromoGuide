package mypromoguide.pro.novatechsolutions.app.promo_api.request;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import mypromoguide.pro.novatechsolutions.app.promo_api.APIConfig;
import mypromoguide.pro.novatechsolutions.app.promo_api.entity.Category;
import mypromoguide.pro.novatechsolutions.app.promo_api.entity.Product;
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.ClientException;
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.OnServiceResponseListener;

public class ProductRequest extends AsyncHttpClient {
    private OnServiceResponseListener<Object> mCallBack;
    private final static String MODULE = "products";

    public ProductRequest( OnServiceResponseListener mCallBack) {
        this.mCallBack = mCallBack;
    }

    public void promos(int category_id, int store_id) {
        final String path = APIConfig.ENDPOINT+MODULE+"/"+category_id+"/"+store_id;
        Log.i("Request", path);
        get(path,  new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    Log.i("Request", path);
                    Log.i("Response", response.toString());
                    List<Product> products = new ArrayList<>();
                    for (int i =0; i < response.length(); i++){
                        Product product = new Product();
                        product.setAvatar(response.getJSONObject(i).optString("avatar"));
                        product.setBrand_name(response.getJSONObject(i).optString("brand_name"));
                        product.setBuy(response.getJSONObject(i).optDouble("buy"));
                        product.setSave(response.getJSONObject(i).optDouble("save"));
                        product.setBuy_product(response.getJSONObject(i).optString("buy_product"));
                        product.setPromo_type_id(response.getJSONObject(i).optInt("promo_type_id"));
                        product.setCategory_name(response.getJSONObject(i).optString("category_name"));
                        product.setDescription(response.getJSONObject(i).optString("description"));
                        product.setGet_product(response.getJSONObject(i).optString("get_product"));
                        product.setGroup(response.getJSONObject(i).optString("group"));
                        product.setNow_price(response.getJSONObject(i).optDouble("now_price"));
                        product.setProduct_name(response.getJSONObject(i).optString("product_name"));
                        product.setProduct_no(response.getJSONObject(i).optString("product_no"));
                        product.setProduct_price(response.getJSONObject(i).optDouble("product_price"));
                        product.setPromo_type(response.getJSONObject(i).optString("promo_type"));
                        product.setPromotion_id(response.getJSONObject(i).optInt("promotion_id"));
                        product.setWas_price(response.getJSONObject(i).optDouble("was_price"));
                        products.add(product);
                    }

                    mCallBack.onSuccess(products);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Log.e("Response", t.getMessage());
                mCallBack.onFailure(new ClientException(t));

            }
        });
    }

}
