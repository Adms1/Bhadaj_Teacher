package anandniketan.com.anbcteacher.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import com.wdullaer.materialdatetimepicker.Utils;

import org.json.JSONObject;

import java.util.HashMap;

import anandniketan.com.anbcteacher.Utility.AppConfiguration;
import anandniketan.com.anbcteacher.Utility.Utility;
import anandniketan.com.anbcteacher.WebServicesCall.WebServicesCall;

import static anandniketan.com.anbcteacher.Utility.AppConfiguration.LIVE_BASE_URL;

public class GetAPIURLTask extends AsyncTask<Void, Void, Boolean> {
    HashMap<String, String> param = new HashMap<String, String>();
    private Context context;

    public GetAPIURLTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        String responseString = null;
        boolean success = false;
        try {
            responseString = WebServicesCall.RunScript("http://anandniketanbhadaj.org/appService/5b9a72856992e144c74fc836ed6e76a2/appsUrl",0);
            success = parseJson(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public boolean parseJson(String responseString) {
        try {
            JSONObject reader = new JSONObject(responseString);
            String readerString = reader.getString("succcess");
            String apiUrl  =  reader.getString("appsUrl");

            AppConfiguration.DOMAIN_LIVE = apiUrl +"MobileApp_Service.asmx/";
            LIVE_BASE_URL = apiUrl;
            AppConfiguration.DOMAIN_LIVE_IMAGES = LIVE_BASE_URL+"skool360-Category-Images/Teacher/";

            //ICONS URL
            //Local
//    public static String DOMAIN_LIVE_ICONS = "http://192.168.1.7:8086/skool360-Design-Icons/Teacher/";
            //Live
            AppConfiguration.DOMAIN_LIVE_ICONS = LIVE_BASE_URL+"skool360-Design-Icons/Teacher/";

            Utility.setPref(context, "live_base_url",LIVE_BASE_URL);//result.get("TermID"));

            if (readerString.equalsIgnoreCase("1")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
