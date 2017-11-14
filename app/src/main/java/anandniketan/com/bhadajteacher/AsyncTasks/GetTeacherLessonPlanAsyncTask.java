package anandniketan.com.bhadajteacher.AsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.HashMap;

import anandniketan.com.bhadajteacher.Models.LessonPlanResponse.MainResponseLesson;
import anandniketan.com.bhadajteacher.Utility.AppConfiguration;
import anandniketan.com.bhadajteacher.WebServicesCall.WebServicesCall;

/**
 * Created by admsandroid on 10/13/2017.
 */

public class GetTeacherLessonPlanAsyncTask extends AsyncTask<Void, Void, MainResponseLesson> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetTeacherLessonPlanAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected MainResponseLesson doInBackground(Void... params) {
        String responseString = null;
        MainResponseLesson mainResponseLesson = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetTeacherLessonPlan), param);
            Gson gson = new Gson();
            mainResponseLesson = gson.fromJson(responseString, MainResponseLesson.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return mainResponseLesson;
    }

    @Override
    protected void onPostExecute(MainResponseLesson result) {
        super.onPostExecute(result);
    }
}