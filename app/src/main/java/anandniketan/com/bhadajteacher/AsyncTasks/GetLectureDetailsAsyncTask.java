package anandniketan.com.bhadajteacher.AsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.HashMap;

import anandniketan.com.bhadajteacher.Models.TimeTable.GetTimeTableSubjectModel;
import anandniketan.com.bhadajteacher.Utility.AppConfiguration;
import anandniketan.com.bhadajteacher.WebServicesCall.WebServicesCall;

public class GetLectureDetailsAsyncTask extends AsyncTask<Void, Void, GetTimeTableSubjectModel> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetLectureDetailsAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected GetTimeTableSubjectModel doInBackground(Void... params) {
        String responseString = null;
        GetTimeTableSubjectModel timeTableSubjectModel = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetLectureDetails), param);
            Gson gson = new Gson();
            timeTableSubjectModel = gson.fromJson(responseString, GetTimeTableSubjectModel.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return timeTableSubjectModel;
    }

    @Override
    protected void onPostExecute(GetTimeTableSubjectModel result) {
        super.onPostExecute(result);
    }
}