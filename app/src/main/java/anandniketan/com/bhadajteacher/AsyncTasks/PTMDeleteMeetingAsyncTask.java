package anandniketan.com.bhadajteacher.AsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.HashMap;

import anandniketan.com.bhadajteacher.Models.PTMResponse.MainPtmSentDeleteResponse;
import anandniketan.com.bhadajteacher.Utility.AppConfiguration;
import anandniketan.com.bhadajteacher.WebServicesCall.WebServicesCall;

/**
 * Created by admsandroid on 10/25/2017.
 */

public class PTMDeleteMeetingAsyncTask extends AsyncTask<Void, Void, MainPtmSentDeleteResponse> {
    HashMap<String, String> param = new HashMap<String, String>();

    public PTMDeleteMeetingAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected MainPtmSentDeleteResponse doInBackground(Void... params) {
        String responseString = null;
        MainPtmSentDeleteResponse mainPtmSentDeleteResponse = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.PTMDeleteMeeting), param);
            Gson gson = new Gson();
            mainPtmSentDeleteResponse = gson.fromJson(responseString, MainPtmSentDeleteResponse.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return mainPtmSentDeleteResponse;
    }

    @Override
    protected void onPostExecute(MainPtmSentDeleteResponse result) {
        super.onPostExecute(result);
    }
}
