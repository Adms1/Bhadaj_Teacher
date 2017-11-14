package anandniketan.com.bhadajteacher.AsyncTasks;

import android.os.AsyncTask;


import java.util.ArrayList;
import java.util.HashMap;

import anandniketan.com.bhadajteacher.Models.Test_SyllabusModel;
import anandniketan.com.bhadajteacher.Utility.AppConfiguration;
import anandniketan.com.bhadajteacher.Utility.ParseJSON;
import anandniketan.com.bhadajteacher.WebServicesCall.WebServicesCall;

/**
 * Created by admsandroid on 9/26/2017.
 */

public class TeacherGetTestSyllabusAsyncTask extends AsyncTask<Void, Void, ArrayList<Test_SyllabusModel>> {
    HashMap<String, String> param = new HashMap<String, String>();

    public TeacherGetTestSyllabusAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Test_SyllabusModel> doInBackground(Void... params) {
        String responseString = null;
        ArrayList<Test_SyllabusModel> result = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetTeacherGetTestSyllabus), param);
            result = ParseJSON.parseTestSyllabusJson(responseString);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<Test_SyllabusModel> result) {
        super.onPostExecute(result);
    }
}
