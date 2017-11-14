package anandniketan.com.bhadajteacher.AsyncTasks;

import android.os.AsyncTask;


import java.util.ArrayList;
import java.util.HashMap;

import anandniketan.com.bhadajteacher.Models.LoginModel;
import anandniketan.com.bhadajteacher.Utility.AppConfiguration;
import anandniketan.com.bhadajteacher.Utility.ParseJSON;
import anandniketan.com.bhadajteacher.WebServicesCall.WebServicesCall;

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