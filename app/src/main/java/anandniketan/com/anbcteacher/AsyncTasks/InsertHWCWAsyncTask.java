package anandniketan.com.anbcteacher.AsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.HashMap;

import anandniketan.com.anbcteacher.Models.HomeWorkResponse.HomeworkStatusInsertUpdateModel;
import anandniketan.com.anbcteacher.Utility.AppConfiguration;
import anandniketan.com.anbcteacher.WebServicesCall.WebServicesCall;

public class InsertHWCWAsyncTask extends AsyncTask<Void, Void, HomeworkStatusInsertUpdateModel> {
    HashMap<String, String> param = new HashMap<String, String>();

    public InsertHWCWAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected HomeworkStatusInsertUpdateModel doInBackground(Void... params) {
        String responseString = null;
        HomeworkStatusInsertUpdateModel homeworkStatusInsertUpdateModel = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.UpdateHWCW), param);
            Gson gson = new Gson();
            homeworkStatusInsertUpdateModel = gson.fromJson(responseString, HomeworkStatusInsertUpdateModel.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return homeworkStatusInsertUpdateModel;
    }

    @Override
    protected void onPostExecute(HomeworkStatusInsertUpdateModel result) {
        super.onPostExecute(result);
    }
}
