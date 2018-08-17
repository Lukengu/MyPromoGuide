package mypromoguide.pro.novatechsolutions.app.sms_portal.client;

import android.text.TextUtils;

import org.junit.Test;


import static org.junit.Assert.*;

public class SMSPortalUnitTest {

    @Test
    public void authorize(){
        new Auth(new OnServiceResponseListener() {
            @Override
            public void onSuccess(Object object) {
                System.out.println((String) object);
                assertTrue(!TextUtils.isEmpty((String) object));
            }

            @Override
            public void onFailure(ClientException e) {

            }
        }).authorize();
    }
}
