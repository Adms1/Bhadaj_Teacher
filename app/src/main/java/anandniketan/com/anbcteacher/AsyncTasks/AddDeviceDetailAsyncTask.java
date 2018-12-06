package anandniketan.com.anbcteacher.AsyncTasks;

import android.os.AsyncTask;
import org.json.JSONObject;

import java.util.HashMap;

import anandniketan.com.anbcteacher.Utility.AppConfiguration;
import anandniketan.com.anbcteacher.WebServicesCall.WebServicesCall;

public class AddDeviceDetailAsyncTask extends AsyncTask<Void, Void, Boolean> {
    HashMap<String, String> param = new HashMap<String, String>();

    public AddDeviceDetailAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        String responseString = null;
        boolean success = false;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.AddDeviceDetailStaff), param);
            success = parseJson(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
    }

    public boolean parseJson(String responseString) {
        try {
            JSONObject reader = new JSONObject(responseString);
            String readerString = reader.getString("Success");
            if (readerString.equalsIgnoreCase("True")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}