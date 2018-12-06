package anandniketan.com.anbcteacher.AsyncTasks;

import android.os.AsyncTask;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import anandniketan.com.anbcteacher.Models.TestModel.GetEditTestModel;
import anandniketan.com.anbcteacher.Utility.AppConfiguration;
import anandniketan.com.anbcteacher.Utility.ParseJSON;
import anandniketan.com.anbcteacher.WebServicesCall.WebServicesCall;

/**
 * Created by admsandroid on 9/26/2017.
 */

public class TeacherGetTestSyllabusAsyncTask  extends AsyncTask<Void, Void,GetEditTestModel> {
    HashMap<String, String> param = new HashMap<String, String>();

    public TeacherGetTestSyllabusAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected GetEditTestModel doInBackground(Void... params) {
        String responseString = null;
        GetEditTestModel editTestModel=null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetTeacherGetTestSyllabus), param);
            Gson gson = new Gson();
            editTestModel = gson.fromJson(responseString, GetEditTestModel.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return editTestModel;
    }

    @Override
    protected void onPostExecute(GetEditTestModel result) {
        super.onPostExecute(result);
    }
}