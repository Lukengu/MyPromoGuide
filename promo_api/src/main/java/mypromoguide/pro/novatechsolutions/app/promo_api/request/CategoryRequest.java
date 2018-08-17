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
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.ClientException;
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.OnServiceResponseListener;

public class CategoryRequest  extends AsyncHttpClient {
    private OnServiceResponseListener<Object> mCallBack;
    private final static String MODULE = "categories";
    public CategoryRequest( OnServiceResponseListener mCallBack) {
        this.mCallBack = mCallBack;
    }

    public void  category_list(int store_id) {
        final String path = APIConfig.ENDPOINT+MODULE+"/"+store_id;
        Log.i("Request", path);

        get(path,  new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    Log.i("Request", path);
                    Log.i("Response", response.toString());
                    List<Category> categories = new ArrayList<>();
                    for (int i =0; i < response.length(); i++){
                        Category category = new Category();
                        category.setBrand_name(response.getJSONObject(i).optString("brand_name"));
                        category.setCategory(response.getJSONObject(i).optString("category"));
                        category.setCategory_id(response.getJSONObject(i).optInt("category_id"));
                        category.setEnd_date(response.getJSONObject(i).optString("end_date"));
                        category.setPromotion_count(response.getJSONObject(i).optInt("ct"));
                        category.setStart_date(response.getJSONObject(i).optString("start_date"));

                        categories.add(category);
                    }

                    mCallBack.onSuccess(categories);
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
