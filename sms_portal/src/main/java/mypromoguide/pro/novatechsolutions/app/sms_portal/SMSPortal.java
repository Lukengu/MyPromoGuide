

package mypromoguide.pro.novatechsolutions.app.sms_portal;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mypromoguide.pro.novatechsolutions.app.sms_portal.client.Auth;
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.BulkMessages;
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.ClientException;
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.OnServiceResponseListener;

public class SMSPortal  {
    private OnServiceResponseListener<Object> mCallBack;
    private Context context;


    public SMSPortal(Context context, OnServiceResponseListener mCallBack) {
        this.mCallBack = mCallBack;
        this.context = context;
    }

    public void sendMS(String message, ArrayList<String> numbers) {

        JSONArray messages = new JSONArray();
        JSONObject finalFormat = new JSONObject();
        int i = 0;
        try {
            for(String number : numbers) {
                Map individual = new HashMap();
                individual.put("Content", message);
                individual.put("Destination", number);
                JSONObject obj = new JSONObject(individual);
                messages.put(i++, obj);
            }
            finalFormat.put("Messages", messages);
            new Auth(context, mCallBack, finalFormat).sendMessage();
        } catch (JSONException e) {
        }
    }
}
