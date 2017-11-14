package anandniketan.com.bhadajteacher.AsyncTasks;

import android.os.AsyncTask;


import java.util.ArrayList;
import java.util.HashMap;

import anandniketan.com.bhadajteacher.Models.LessonPlanModel;
import anandniketan.com.bhadajteacher.Utility.AppConfiguration;
import anandniketan.com.bhadajteacher.Utility.ParseJSON;
import anandniketan.com.bhadajteacher.WebServicesCall.WebServicesCall;

/**
 * Created by admsandroid on 10/13/2017.
 */

public class GetTeacherLessonPlanScheduleAsyncTask  extends AsyncTask<Void, Void, ArrayList<LessonPlanModel>> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetTeacherLessonPlanScheduleAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<LessonPlanModel> doInBackground(Void... params) {
        String responseString = null;
        ArrayList<LessonPlanModel> result = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetTeacherLessonPlanSchedule), param);
            result = ParseJSON.parseTeacherLessonPlanScheduleJson(responseString);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<LessonPlanModel> result) {
        super.onPostExecute(result);
    }
}
