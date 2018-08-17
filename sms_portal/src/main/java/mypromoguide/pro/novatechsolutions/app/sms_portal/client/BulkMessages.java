package mypromoguide.pro.novatechsolutions.app.sms_portal.client;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import mypromoguide.pro.novatechsolutions.app.sms_portal.AppConfig;

public class BulkMessages extends AsyncHttpClient {

    private OnServiceResponseListener<Object> mCallBack;
    private final static String MODULE = "bulkmessages";
    private Context mContext;


    public BulkMessages(Context mContext, OnServiceResponseListener mCallBack, String token ) {
        this.mCallBack = mCallBack;
        this.mContext = mContext;
        addHeader("Content-type", "application/json");
        addHeader("Authorization", "Bearer " +token);
        addHeader("Accept", "application/json");



    }

    public void send(JSONObject messages) throws UnsupportedEncodingException {
        Log.i("message", messages.toString());

        StringEntity entity = new StringEntity(messages.toString());
        Log.i("Request", AppConfig.API_ENDPOINT+MODULE);
        post(mContext,AppConfig.API_ENDPOINT+MODULE,null,entity,"application/json",new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("Request", AppConfig.API_ENDPOINT+MODULE);
                Log.i("Response", response.toString());
                mCallBack.onSuccess(response);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Log.e("Response", t.getMessage());
                mCallBack.onFailure(new ClientException(t));

            }
        });
    }

}
