package anandniketan.com.anbcteacher.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.JsonObject;
import com.wdullaer.materialdatetimepicker.Utils;

import anandniketan.com.anbcteacher.AsyncTasks.GetAPIURLTask;
import anandniketan.com.anbcteacher.R;
import anandniketan.com.anbcteacher.Utility.ApiClient;
import anandniketan.com.anbcteacher.Utility.ApiHandler;
import anandniketan.com.anbcteacher.Utility.AppConfiguration;
import anandniketan.com.anbcteacher.Utility.FontsOverride;
import anandniketan.com.anbcteacher.Utility.Utility;
import anandniketan.com.anbcteacher.Utility.WebServices;
import retrofit2.Call;
import retrofit2.Callback;

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
//            new GetAPIURLTask(mAppcontext).execute();

            if (!Utility.isNetworkConnected(mAppcontext)) {
//                Utils.(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), (Activity) getApplicationContext());
                return;
            }

            WebServices apiService = ApiClient.getClient().create(WebServices.class);

            Call<JsonObject> call = apiService.getBaseUrl(AppConfiguration.GET_API_URL);
            call.enqueue(new Callback<JsonObject>() {

                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull retrofit2.Response<JsonObject> response) {
//                    Utils.dismissDialog();
                    if (response.body() == null) {
                        Utility.ping(mAppcontext, "Something went wrong");
                        return;
                    }
                    if (response.body().get("succcess") == null) {
                        Utility.ping(mAppcontext, "Something went wrong");
                        return;
                    }
                    if (response.body().get("succcess").getAsString().equalsIgnoreCase("0")) {
                        Utility.ping(mAppcontext, "Something went wrong");
                        return;
                    }
                    if (response.body().get("succcess").getAsString().equalsIgnoreCase("1")) {
                        Utility.setPref(getAppContext(),"live_base_url", response.body().get("appsUrl").getAsString());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
//                    Utils.dismissDialog();
                    t.printStackTrace();
                    t.getMessage();
                    Utility.ping(mAppcontext, "something went wrong");
                }
            });

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static Context getAppContext() {
        return mAppcontext;
    }
}
