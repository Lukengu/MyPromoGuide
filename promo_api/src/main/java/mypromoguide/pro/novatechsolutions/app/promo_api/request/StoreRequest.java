package mypromoguide.pro.novatechsolutions.app.promo_api.request;

import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import mypromoguide.pro.novatechsolutions.app.promo_api.APIConfig;
import mypromoguide.pro.novatechsolutions.app.promo_api.entity.Store;
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.ClientException;
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.OnServiceResponseListener;
import java.math.BigDecimal;


public class StoreRequest  extends AsyncHttpClient  {
    private OnServiceResponseListener<Object> mCallBack;
    private final static String MODULE = "stores";

    public StoreRequest( OnServiceResponseListener mCallBack) {
        this.mCallBack = mCallBack;
    }

    public void store_list() {
        final String path = APIConfig.ENDPOINT+MODULE;
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

    public void nearby_store(int store_id,double lat, double lng) {
        final String path = APIConfig.ENDPOINT+MODULE+"/around/"+store_id+"/"+lat+"/"+lng;
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

                        float distance = Float.valueOf(response.getJSONObject(i).getString("distance"));
                       // distance = distance ;
                        Log.i("Org Distance", String.format("%.02f", distance));
                        store.setDistance(String.format("%.02f", distance));
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

    public void store_by_radius(double lat, double lng) {
        final String path = APIConfig.ENDPOINT+MODULE+"/"+lat+"/"+lng+"/3000";
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

                        float distance = Float.valueOf(response.getJSONObject(i).getString("distance"));
                       // distance = distance * 1000;
                        Log.i("Org Distance", String.format("%.02f", distance));
                        store.setDistance(String.format("%.02f", distance));
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


    //http://mypromoguide.com/admin/api/v1/app/stores/-26.093611/28.006390/15000

}
