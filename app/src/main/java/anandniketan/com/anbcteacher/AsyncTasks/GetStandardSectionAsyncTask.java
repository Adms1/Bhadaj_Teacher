package anandniketan.com.anbcteacher.AsyncTasks;

import android.os.AsyncTask;


import com.google.gson.Gson;

import java.util.HashMap;

import anandniketan.com.anbcteacher.Models.TimeTable.GetStandardSectionModel;
import anandniketan.com.anbcteacher.Utility.AppConfiguration;
import anandniketan.com.anbcteacher.WebServicesCall.WebServicesCall;

/**
 * Created by admsandroid on 11/14/2017.
 */

public class GetStandardSectionAsyncTask extends AsyncTask<Void, Void, GetStandardSectionModel> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetStandardSectionAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected GetStandardSectionModel doInBackground(Void... params) {
        String responseString = null;
        GetStandardSectionModel getStandardSectionModel = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetStandardSection), param);
            Gson gson = new Gson();
            getStandardSectionModel = gson.fromJson(responseString, GetStandardSectionModel.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return getStandardSectionModel;
    }

    @Override
    protected void onPostExecute(GetStandardSectionModel result) {
        super.onPostExecute(result);
    }
}
