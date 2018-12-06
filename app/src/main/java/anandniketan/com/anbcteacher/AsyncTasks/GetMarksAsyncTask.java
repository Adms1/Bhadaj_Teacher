package anandniketan.com.anbcteacher.AsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.HashMap;

import anandniketan.com.anbcteacher.Models.LeaveModel.LeaveMainModel;
import anandniketan.com.anbcteacher.Utility.AppConfiguration;
import anandniketan.com.anbcteacher.WebServicesCall.WebServicesCall;

public class GetMarksAsyncTask extends AsyncTask<Void, Void, LeaveMainModel> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetMarksAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected LeaveMainModel doInBackground(Void... params) {
        String responseString = null;
        LeaveMainModel getLeaveHead = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetMarks), param);
            Gson gson = new Gson();
            getLeaveHead = gson.fromJson(responseString, LeaveMainModel.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return getLeaveHead;
    }

    @Override
    protected void onPostExecute(LeaveMainModel result) {
        super.onPostExecute(result);
    }
}


