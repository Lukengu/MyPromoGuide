package mypromoguide.pro.novatechsolutions.app.mypromoguide;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import mypromoguide.pro.novatechsolutions.app.mypromoguide.utils.AppPreferences;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.utils.AppTypeFace;
import mypromoguide.pro.novatechsolutions.app.sms_portal.AppConfig;
import mypromoguide.pro.novatechsolutions.app.sms_portal.SMSPortal;
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.Auth;
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.ClientException;
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.OnServiceResponseListener;

public class Splash extends Activity implements OnServiceResponseListener {

    private AppPreferences appPreferences;
    private EditText cellphone_number;
    private TextView label;
    private Button btn_send;
    private Typeface plain;
    private Typeface bold;
    private  JSONObject user_infos =  null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView logo = findViewById(R.id.logo);
        cellphone_number = findViewById(R.id.cellphone_number);
        label = findViewById(R.id.label);
        btn_send = findViewById(R.id.btn_send);

        plain = AppTypeFace.NewInstance(Splash.this).getTypeFace();
        bold = AppTypeFace.NewInstance(Splash.this).getBoldTypeFace();

        cellphone_number.setTypeface(plain);
        label.setTypeface(plain);
        btn_send.setTypeface(bold);



        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_infos != null) {
                    try {
                        if(cellphone_number.equals(user_infos.getString("cell_no"))){
                            startActivity(new Intent(Splash.this, Content.class));
                        } else {
                            cellphone_number.setError("Invalid Credentials. To reset please clear the application data");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    if (TextUtils.isEmpty(cellphone_number.getText())) {
                        cellphone_number.setError("Cellphone number is required");

                        /*
                         */
                    } else {

                        int min = 11111;
                        int max = 99999;

                        Random r = new Random();

                        int otp = r.nextInt((max - min) + 1) + min;
                        appPreferences.setAuthOTP(otp);
                        SMSPortal sms = new SMSPortal(getApplicationContext(), Splash.this);
                        ArrayList<String> numbers = new ArrayList<String>();
                        numbers.add(cellphone_number.getText().toString());
                        sms.sendMS("Your MyPromoGuide OTP for Registration is " + otp, numbers);

                        startActivity(new Intent(Splash.this, Verify.class).putExtra("cell_no", cellphone_number.getText()));
                        finish();

                    }
                }
            }
        });

        Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        final Animation textAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein_fast);

        logo.startAnimation(startAnimation);
        appPreferences = AppPreferences.NewInstance(Splash.this);
        if(!TextUtils.isEmpty(appPreferences.getUser())) {
            try {
                user_infos = new JSONObject(appPreferences.getUser());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(user_infos != null) {
            label.setText("Please enter your cellphone number to login");
            btn_send.setText("Login");
        }
        //Toast.makeText(this, "" +appPreferences.IsfirstTimeRun(), Toast.LENGTH_LONG).show();
        //Toast.makeText(this, "" + AppConfig.API_USERNAME, Toast.LENGTH_LONG).show();








        startAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(appPreferences.IsfirstTimeRun()){
                    cellphone_number.setVisibility(View.VISIBLE);
                    label.setVisibility(View.VISIBLE);
                    btn_send.setVisibility(View.VISIBLE);
                    cellphone_number.startAnimation(textAnimation);
                    label.startAnimation(textAnimation);
                    btn_send.startAnimation(textAnimation);

                } else {
                    startActivity(new Intent(Splash.this, Content.class));
                    finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    public void onSuccess(Object object) {
     //   Toast.makeText(getApplicationContext(), ((JSONObject) object).toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(ClientException e) {

    }
}
