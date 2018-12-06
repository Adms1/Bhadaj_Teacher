package anandniketan.com.anbcteacher.AsyncTasks;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;

import anandniketan.com.anbcteacher.Models.TimeTable.TeacherGetTimetableModel;
import anandniketan.com.anbcteacher.Utility.AppConfiguration;
import anandniketan.com.anbcteacher.Utility.ParseJSON;
import anandniketan.com.anbcteacher.WebServicesCall.WebServicesCall;

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