package anandniketan.com.anbcteacher.AsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import anandniketan.com.anbcteacher.Models.LeaveModel.LeaveMainModel;
import anandniketan.com.anbcteacher.Models.LoginModel;
import anandniketan.com.anbcteacher.Utility.AppConfiguration;
import anandniketan.com.anbcteacher.Utility.ParseJSON;
import anandniketan.com.anbcteacher.WebServicesCall.WebServicesCall;

public class ForgotPasswordAsyncTask extends AsyncTask<Void, Void, LeaveMainModel> {
    HashMap<String, String> param = new HashMap<String, String>();

    public ForgotPasswordAsyncTask(HashMap<String, String> param) {
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
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.ForgetIDPasswordStaff), param);
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
