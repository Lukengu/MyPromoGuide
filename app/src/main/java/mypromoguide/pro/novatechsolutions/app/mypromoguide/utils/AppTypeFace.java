package mypromoguide.pro.novatechsolutions.app.mypromoguide.utils;

import android.content.Context;
import android.graphics.Typeface;


public class AppTypeFace {
    private static final AppTypeFace instance = new AppTypeFace();
    private  static Context mContext;
    private static final String FONT_PATH = "fonts/Montserrat-Regular.ttf";
    private static final String FONT_BOLD_PATH_ = "fonts/Montserrat-Bold.ttf";


    public  static AppTypeFace NewInstance(Context context) {
        mContext = context;
        return instance;
    }

    public Typeface getTypeFace() {
        return  Typeface.createFromAsset(mContext.getAssets(),  FONT_PATH);
    }
    public Typeface getBoldTypeFace() {
        return  Typeface.createFromAsset(mContext.getAssets(),  FONT_BOLD_PATH_);
    }
}
