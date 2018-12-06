package anandniketan.com.anbcteacher.AsyncTasks;

import android.os.AsyncTask;


import java.util.ArrayList;
import java.util.HashMap;

import anandniketan.com.anbcteacher.Models.LoginModel;
import anandniketan.com.anbcteacher.Utility.AppConfiguration;
import anandniketan.com.anbcteacher.Utility.ParseJSON;
import anandniketan.com.anbcteacher.WebServicesCall.WebServicesCall;

/**
 * Created by admsandroid on 9/15/2017.
 */

public class LoginAsyncTask   extends AsyncTask<Void, Void, ArrayList<LoginModel>> {
    HashMap<String, String> param = new HashMap<String, String>();

    public LoginAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<LoginModel> doInBackground(Void... params) {
        String responseString = null;
        ArrayList<LoginModel> result = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetStaffLogin), param);
            result = ParseJSON.parseLoginJson(responseString);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<LoginModel> result) {
        super.onPostExecute(result);
    }
}