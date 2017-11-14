package anandniketan.com.bhadajteacher.AsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.HashMap;

import anandniketan.com.bhadajteacher.Models.NewResponse.MainResponse;
import anandniketan.com.bhadajteacher.Utility.AppConfiguration;
import anandniketan.com.bhadajteacher.WebServicesCall.WebServicesCall;

/**
 * Created by admsandroid on 9/27/2017.
 */

public class TeacherGetTestMarksAsyncTask extends AsyncTask<Void, Void,MainResponse> {
    HashMap<String, String> param = new HashMap<String, String>();

    public TeacherGetTestMarksAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected MainResponse doInBackground(Void... params) {
        String responseString = null;
        MainResponse response =null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetTeacherGetTestMarks), param);
            Gson gson = new Gson();
            response = gson.fromJson(responseString, MainResponse.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(MainResponse result) {
        super.onPostExecute(result);
    }
}
