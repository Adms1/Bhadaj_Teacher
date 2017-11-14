package anandniketan.com.bhadajteacher.AsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.HashMap;

import anandniketan.com.bhadajteacher.Models.TeacherInsertAssignStudentSubjectModel.TeacherInsertSubjectMainResponse;
import anandniketan.com.bhadajteacher.Utility.AppConfiguration;
import anandniketan.com.bhadajteacher.WebServicesCall.WebServicesCall;

/**
 * Created by admsandroid on 10/30/2017.
 */

public class TeacherInsertAssignStudentSubjectAsyncTask  extends AsyncTask<Void, Void,TeacherInsertSubjectMainResponse> {
    HashMap<String, String> param = new HashMap<String, String>();

    public TeacherInsertAssignStudentSubjectAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected TeacherInsertSubjectMainResponse doInBackground(Void... params) {
        String responseString = null;
        TeacherInsertSubjectMainResponse response =null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.TeacherInsertAssignStudentSubject), param);

            Gson gson = new Gson();
            response = gson.fromJson(responseString, TeacherInsertSubjectMainResponse.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(TeacherInsertSubjectMainResponse result) {
        super.onPostExecute(result);
    }
}

