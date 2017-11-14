package anandniketan.com.bhadajteacher.AsyncTasks;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;

import anandniketan.com.bhadajteacher.Models.TeacherGetTimetableModel;
import anandniketan.com.bhadajteacher.Utility.AppConfiguration;
import anandniketan.com.bhadajteacher.Utility.ParseJSON;
import anandniketan.com.bhadajteacher.WebServicesCall.WebServicesCall;

/**
 * Created by admsandroid on 9/25/2017.
 */

public class GetTeacherGetTimetableAsyncTask extends AsyncTask<Void, Void, ArrayList<TeacherGetTimetableModel>> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetTeacherGetTimetableAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<TeacherGetTimetableModel> doInBackground(Void... params) {
        String responseString = null;
        ArrayList<TeacherGetTimetableModel> result = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetTeacherGetTimetable), param);
            result = ParseJSON.parseTeachertTimetableJson(responseString);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<TeacherGetTimetableModel> result) {
        super.onPostExecute(result);
    }
}