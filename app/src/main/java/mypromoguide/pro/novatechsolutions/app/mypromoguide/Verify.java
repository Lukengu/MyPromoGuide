package mypromoguide.pro.novatechsolutions.app.mypromoguide;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import mypromoguide.pro.novatechsolutions.app.mypromoguide.utils.AppPreferences;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.utils.AppTypeFace;
import mypromoguide.pro.novatechsolutions.app.sms_portal.SMSPortal;
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.ClientException;
import mypromoguide.pro.novatechsolutions.app.sms_portal.client.OnServiceResponseListener;

public class Verify extends AppCompatActivity {
    private AppPreferences appPreferences;
    private EditText cellphone_number;
    private TextView label;
    private Button btn_send, btn_resend;
    private Typeface plain;
    private Typeface bold;
    private String cell_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        cellphone_number = findViewById(R.id.cellphone_number);
        label = findViewById(R.id.label);
        btn_send = findViewById(R.id.btn_send);
        btn_resend = findViewById(R.id.btn_resend);

        plain = AppTypeFace.NewInstance(Verify.this).getTypeFace();
        bold = AppTypeFace.NewInstance(Verify.this).getBoldTypeFace();

        cellphone_number.setTypeface(plain);
        label.setTypeface(plain);
        btn_send.setTypeface(bold);
        btn_resend.setTypeface(bold);
        appPreferences = AppPreferences.NewInstance(Verify.this);

        cell_no = getIntent().getStringExtra("cell_no");

        //Toast.makeText(this, appPreferences.getAuthOTP()+" ", Toast.LENGTH_LONG).show();
        cellphone_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.equals(String.valueOf(appPreferences.getAuthOTP()))) {
                    startActivity(new Intent(Verify.this, Register.class));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(cellphone_number.getText().toString())) {
                    if (cellphone_number.getText().toString().equals(
                            String.valueOf(appPreferences.getAuthOTP()))) {
                        startActivity(new Intent(Verify.this, Register.class).putExtra("cell_no", cell_no));
                    } else {
                        cellphone_number.setError("Incorrect Pin");
                    }
                } else {
                    cellphone_number.setError("Pin Required");
                }
            }
        });

        btn_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int min = 11111;
                int max = 99999;

                Random r = new Random();
                int  otp = r.nextInt((max - min) + 1) + min;
                appPreferences.setAuthOTP(otp);
                SMSPortal sms = new SMSPortal(getApplicationContext(), new OnServiceResponseListener() {
                    @Override
                    public void onSuccess(Object object) {

                    }

                    @Override
                    public void onFailure(ClientException e) {

                    }
                });
                ArrayList<String> numbers =  new ArrayList<String>();
                numbers.add(cell_no);
                sms.sendMS("Your MyPromoGuide OTP for Registration is "+otp ,numbers);
            }
        });
    }
}
