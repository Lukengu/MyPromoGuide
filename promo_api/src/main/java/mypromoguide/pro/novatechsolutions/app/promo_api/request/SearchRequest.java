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
import mypromoguide.pro.novatechsolutions.app.promo_api.entity.Store;
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.ClientException;
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.OnServiceResponseListener;

public class SearchRequest  extends AsyncHttpClient {
    private OnServiceResponseListener<Object> mCallBack;
    private final static String MODULE = "search";
    public SearchRequest( OnServiceResponseListener mCallBack) {
        this.mCallBack = mCallBack;
    }
    public void keywords() {
        final String path = APIConfig.ENDPOINT+MODULE+"/keywords";
        Log.i("Request", path);
        post(path,  new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    Log.i("Request", path);
                    Log.i("Response", response.toString());
                    List<String> keywords = new ArrayList();
                    for (int i =0; i < response.length(); i++){
                        String keyword = response.getString(i);

                        keywords.add(keyword);
                    }

                    mCallBack.onSuccess(keywords);
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
    public void search(String query) {
        final String path = APIConfig.ENDPOINT+MODULE+"/"+query;
        Log.i("Request", path);
        get(path,  new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    Log.i("Request", path);
                    Log.i("Response", response.toString());
                    List<Store> stores = new ArrayList<>();
                    for (int i =0; i < response.length(); i++){
                        Store store = new Store();
                        store.setId(response.getJSONObject(i).optInt("id"));
                        store.setAvatar(response.getJSONObject(i).optString("avatar"));
                        store.setEnd_date(response.getJSONObject(i).optString("end_date"));
                        store.setName(response.getJSONObject(i).optString("store_name"));
                        store.setPromotion_count(response.getJSONObject(i).optInt("ct"));
                        store.setStart_date(response.getJSONObject(i).optString("start_date"));
                        store.setShopping_center(response.getJSONObject(i).optString("shopping_center"));
                        store.setLat(response.getJSONObject(i).optDouble("lat"));
                        store.setLng(response.getJSONObject(i).optDouble("lng"));
                        store.setShopping_center(response.getJSONObject(i).optString("shopping_center"));
                        store.setAddress(response.getJSONObject(i).optString("address"));
                        store.setOt_public_holidays(response.getJSONObject(i).optString("public_holidays"));
                        store.setOt_weekdays(response.getJSONObject(i).optString("weekdays"));
                        store.setOt_saturdays(response.getJSONObject(i).optString("saturdays"));
                        store.setOt_sundays(response.getJSONObject(i).optString("sundays"));
                        store.setParent_id(response.getJSONObject(i).optInt("parent_id"));

                        stores.add(store);
                    }

                    mCallBack.onSuccess(stores);
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
