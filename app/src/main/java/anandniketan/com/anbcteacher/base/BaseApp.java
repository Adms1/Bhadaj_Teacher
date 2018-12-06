package anandniketan.com.anbcteacher.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import anandniketan.com.anbcteacher.AsyncTasks.GetAPIURLTask;
import anandniketan.com.anbcteacher.Utility.FontsOverride;
import anandniketan.com.anbcteacher.Utility.Utility;

public class BaseApp extends Application {

    public static Context mAppcontext;
    @Override
    public void onCreate() {
        super.onCreate();

        mAppcontext = getApplicationContext();
       // FontsOverride.setDefaultFont(this, "DEFAULT", "font/brush_script_mt_kursiv.ttf");

        Log.d("Token",Utility.getPref(getApplicationContext(), "registration_id"));

        Utility.setPref(mAppcontext,"user_birthday_wish","0");


//        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/TitilliumWeb-Regular.ttf");
//        FontsOverride.setDefaultFont(this, "SERIF", "fonts/TitilliumWeb-Regular.ttf");
//        FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/TitilliumWeb-Regular.ttf");

        try {
            new GetAPIURLTask(mAppcontext).execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static Context getAppContext() {
        return mAppcontext;
    }
}
