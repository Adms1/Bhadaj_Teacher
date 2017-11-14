package anandniketan.com.bhadajteacher.AsyncTasks;

import android.os.AsyncTask;


import java.util.ArrayList;
import java.util.HashMap;

import anandniketan.com.bhadajteacher.Models.TeacherGetTestNameModel;
import anandniketan.com.bhadajteacher.Utility.AppConfiguration;
import anandniketan.com.bhadajteacher.Utility.ParseJSON;
import anandniketan.com.bhadajteacher.WebServicesCall.WebServicesCall;

/**
 * Created by admsandroid on 9/28/2017.
 */

public class TeacherGetTestNameGradeWiseAsyncTask extends AsyncTask<Void, Void, ArrayList<TeacherGetTestNameModel>> {
    HashMap<String, String> param = new HashMap<String, String>();

    public TeacherGetTestNameGradeWiseAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<TeacherGetTestNameModel> doInBackground(Void... params) {
        String responseString = null;
        ArrayList<TeacherGetTestNameModel> result = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetTeacherGetTestNameGradeWise), param);
            result = ParseJSON.parseTeacherGetTestNameJson(responseString);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<TeacherGetTestNameModel> result) {
        super.onPostExecute(result);
    }
}
