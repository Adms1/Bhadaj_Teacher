package anandniketan.com.bhadajteacher.AsyncTasks;

import android.os.AsyncTask;


import java.util.ArrayList;
import java.util.HashMap;

import anandniketan.com.bhadajteacher.Models.TeacherAssignedSubjectModel;
import anandniketan.com.bhadajteacher.Utility.AppConfiguration;
import anandniketan.com.bhadajteacher.Utility.ParseJSON;
import anandniketan.com.bhadajteacher.WebServicesCall.WebServicesCall;

/**
 * Created by admsandroid on 9/25/2017.
 */

public class GetTeacherAssignedSubjectAsyncTask extends AsyncTask<Void, Void, ArrayList<TeacherAssignedSubjectModel>> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetTeacherAssignedSubjectAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<TeacherAssignedSubjectModel> doInBackground(Void... params) {
        String responseString = null;
        ArrayList<TeacherAssignedSubjectModel> result = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetTeacherAssignedSubject), param);
            result = ParseJSON.parseTeacherAssignedSubjectJson(responseString);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<TeacherAssignedSubjectModel> result) {
        super.onPostExecute(result);
    }
}
