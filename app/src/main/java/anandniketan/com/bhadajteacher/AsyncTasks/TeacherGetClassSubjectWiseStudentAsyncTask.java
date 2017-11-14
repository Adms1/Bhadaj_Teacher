package anandniketan.com.bhadajteacher.AsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.HashMap;

import anandniketan.com.bhadajteacher.Models.PTMCreateResponse.MainResponseDisplayStudent;
import anandniketan.com.bhadajteacher.Utility.AppConfiguration;
import anandniketan.com.bhadajteacher.WebServicesCall.WebServicesCall;

/**
 * Created by admsandroid on 10/25/2017.
 */

public class TeacherGetClassSubjectWiseStudentAsyncTask extends AsyncTask<Void, Void, MainResponseDisplayStudent> {
    HashMap<String, String> param = new HashMap<String, String>();

    public TeacherGetClassSubjectWiseStudentAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected MainResponseDisplayStudent doInBackground(Void... params) {
        String responseString = null;
        MainResponseDisplayStudent mainResponseDisplayStudent = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.TeacherGetClassSubjectWiseStudent), param);
            Gson gson = new Gson();
            mainResponseDisplayStudent = gson.fromJson(responseString, MainResponseDisplayStudent.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return mainResponseDisplayStudent;
    }

    @Override
    protected void onPostExecute(MainResponseDisplayStudent result) {
        super.onPostExecute(result);
    }
}
