package mypromoguide.pro.novatechsolutions.app.mypromoguide;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mypromoguide.pro.novatechsolutions.app.mypromoguide.utils.AppPreferences;
import mypromoguide.pro.novatechsolutions.app.mypromoguide.utils.AppTypeFace;

public class Register extends Activity {

    private EditText name,suburb,city,province;
    private TextView gender_text,dob_text;
    private RadioButton female,male;
    private DatePicker dob;
    private Button btn_send;
    private Typeface plain;
    private Typeface bold;
    private AppPreferences appPreferences;
    private String cell_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.name);
        suburb = findViewById(R.id.suburb);
        city = findViewById(R.id.city);
        province = findViewById(R.id.province);
        female  = findViewById(R.id.female);
        male = findViewById(R.id.male);
        dob = findViewById(R.id.dob);
        btn_send = findViewById(R.id.btn_send);

        plain = AppTypeFace.NewInstance(Register.this).getTypeFace();
        bold = AppTypeFace.NewInstance(Register.this).getBoldTypeFace();

        appPreferences =  AppPreferences.NewInstance(Register.this);

        name.setTypeface(plain);
        suburb.setTypeface(plain);
        city.setTypeface(plain);
        province.setTypeface(plain);
        female.setTypeface(plain);
        male.setTypeface(plain);
        //dob.setTypeface(plain);
        btn_send.setTypeface(plain);
        cell_no = getIntent().getStringExtra("cell_no");


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(name.getText())) {
                    name.setError("Name is Required");
                } else {
                    Map m = new HashMap<String,String>();
                    m.put("name", name.getText());
                    m.put("suburb", suburb.getText());
                    m.put("city", city.getText());
                    m.put("province", province.getText());
                    m.put("male", male.getText());
                    m.put("cell_no", cell_no);

                    JSONObject user = new JSONObject(m);
                    appPreferences.saveUserInfos(user.toString());
                    appPreferences.UnsetFirstTineRun();
                    startActivity(new Intent(Register.this, Content.class));
                    finish();
                }
            }
        });


    }
}
