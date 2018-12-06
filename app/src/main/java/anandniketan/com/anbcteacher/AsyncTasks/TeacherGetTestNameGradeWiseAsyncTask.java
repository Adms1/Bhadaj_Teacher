package anandniketan.com.anbcteacher.AsyncTasks;

import android.os.AsyncTask;


import java.util.ArrayList;
import java.util.HashMap;

import anandniketan.com.anbcteacher.Models.TestModel.TeacherGetTestNameModel;
import anandniketan.com.anbcteacher.Utility.AppConfiguration;
import anandniketan.com.anbcteacher.Utility.ParseJSON;
import anandniketan.com.anbcteacher.WebServicesCall.WebServicesCall;

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
