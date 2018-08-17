package mypromoguide.pro.novatechsolutions.app.sms_portal.client;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import mypromoguide.pro.novatechsolutions.app.sms_portal.AppConfig;

public class Auth extends AsyncHttpClient {

    private OnServiceResponseListener<Object> mCallBack;
    private final static String MODULE = "Authentication";
    private Context mContext;
    private JSONObject messages;

    public Auth(Context mContext, OnServiceResponseListener mCallBack, JSONObject messages) {
        this.mCallBack = mCallBack;
        this.mContext = mContext;
        this.messages = messages;
        addHeader("Content-type", "application/json");
        addHeader("Authorization", "Basic "+AppConfig.LOGIN_TOKEN);
        //   setBasicAuth(AppConfig.CLIENT_ID, AppConfig.CLIENT_KEY);
    }


    public void sendMessage(){
        final String endpoint = AppConfig.API_ENDPOINT + MODULE;
        Log.i("Sending Request", endpoint);
         get(endpoint,  new JsonHttpResponseHandler() {
             @Override
             public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                 try {
                     Log.i("Request", endpoint);
                     Log.i("Response", response.toString());
                     new BulkMessages(mContext,mCallBack, response.getString("token")).send(messages);
                    // mCallBack.onSuccess(response.getString("token"));
                 } catch (JSONException e) {
                     e.printStackTrace();
                 } catch (UnsupportedEncodingException e) {
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
