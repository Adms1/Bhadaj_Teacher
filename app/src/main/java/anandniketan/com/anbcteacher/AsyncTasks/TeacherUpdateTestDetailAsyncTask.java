package anandniketan.com.anbcteacher.AsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.HashMap;

import anandniketan.com.anbcteacher.Models.TestModel.UpdateTestDetailModel;
import anandniketan.com.anbcteacher.Utility.AppConfiguration;
import anandniketan.com.anbcteacher.WebServicesCall.WebServicesCall;

/**
 * Created by admsandroid on 11/6/2017.
 */

public class TeacherUpdateTestDetailAsyncTask  extends AsyncTask<Void, Void, UpdateTestDetailModel> {
    HashMap<String, String> param = new HashMap<String, String>();

    public TeacherUpdateTestDetailAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected UpdateTestDetailModel doInBackground(Void... params) {
        String responseString = null;
        UpdateTestDetailModel updateTestDetailModel = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetTeacherInsertTestDetail), param);
            Gson gson = new Gson();
            updateTestDetailModel = gson.fromJson(responseString, UpdateTestDetailModel.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return updateTestDetailModel;
    }

    @Override
    protected void onPostExecute(UpdateTestDetailModel result) {
        super.onPostExecute(result);
    }
}

