package anandniketan.com.anbcteacher.AsyncTasks;

import android.os.AsyncTask;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import anandniketan.com.anbcteacher.Models.LeaveModel.LeaveMainModel;
import anandniketan.com.anbcteacher.Models.UserProfileModel;
import anandniketan.com.anbcteacher.Utility.AppConfiguration;
import anandniketan.com.anbcteacher.Utility.ParseJSON;
import anandniketan.com.anbcteacher.WebServicesCall.WebServicesCall;

/**
 * Created by admsandroid on 9/20/2017.
 */

public class GetStaffProfileAsyncTask extends AsyncTask<Void, Void, UserProfileModel> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetStaffProfileAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected UserProfileModel doInBackground(Void... params) {
        String responseString = null;
        UserProfileModel userProfileModel = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetStaffProfile), param);
            Gson gson = new Gson();
            userProfileModel = gson.fromJson(responseString, UserProfileModel.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return userProfileModel;
    }

    @Override
    protected void onPostExecute(UserProfileModel result) {
        super.onPostExecute(result);
    }
}