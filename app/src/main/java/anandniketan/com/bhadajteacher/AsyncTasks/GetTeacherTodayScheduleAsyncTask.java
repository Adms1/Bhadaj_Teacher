package anandniketan.com.bhadajteacher.AsyncTasks;

import android.os.AsyncTask;


import java.util.ArrayList;
import java.util.HashMap;

import anandniketan.com.bhadajteacher.Models.TeacherTodayScheduleModel;
import anandniketan.com.bhadajteacher.Utility.AppConfiguration;
import anandniketan.com.bhadajteacher.Utility.ParseJSON;
import anandniketan.com.bhadajteacher.WebServicesCall.WebServicesCall;

/**
 * Created by admsandroid on 9/22/2017.
 */

public class GetTeacherTodayScheduleAsyncTask extends AsyncTask<Void, Void, ArrayList<TeacherTodayScheduleModel>> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetTeacherTodayScheduleAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<TeacherTodayScheduleModel> doInBackground(Void... params) {
        String responseString = null;
        ArrayList<TeacherTodayScheduleModel> result = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetTeacherTodaySchedule), param);
            result = ParseJSON.parseTeacherTodayscheduleJson(responseString);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<TeacherTodayScheduleModel> result) {
        super.onPostExecute(result);
    }
}