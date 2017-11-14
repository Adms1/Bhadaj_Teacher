package anandniketan.com.bhadajteacher.AsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.HashMap;

import anandniketan.com.bhadajteacher.Models.WorkPlanResponse.WorkPlanMainResponseModel;
import anandniketan.com.bhadajteacher.Utility.AppConfiguration;
import anandniketan.com.bhadajteacher.WebServicesCall.WebServicesCall;

/**
 * Created by admsandroid on 11/13/2017.
 */

public class GetTeacherWorkPlanAsyncTask extends AsyncTask<Void, Void,WorkPlanMainResponseModel> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetTeacherWorkPlanAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected WorkPlanMainResponseModel doInBackground(Void... params) {
        String responseString = null;
        WorkPlanMainResponseModel workPlanMainResponseModel =null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetTeacherWorkPlan), param);
            Gson gson = new Gson();
            workPlanMainResponseModel = gson.fromJson(responseString, WorkPlanMainResponseModel.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return workPlanMainResponseModel;
    }

    @Override
    protected void onPostExecute(WorkPlanMainResponseModel result) {
        super.onPostExecute(result);
    }
}
