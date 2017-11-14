package anandniketan.com.bhadajteacher.AsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.HashMap;

import anandniketan.com.bhadajteacher.Models.HomeworkStatusInsertUpdateModel;
import anandniketan.com.bhadajteacher.Utility.AppConfiguration;
import anandniketan.com.bhadajteacher.WebServicesCall.WebServicesCall;

/**
 * Created by admsandroid on 11/8/2017.
 */

public class TeacherStudentHomeworkStatusInsertUpdateAsyncTask extends AsyncTask<Void, Void, HomeworkStatusInsertUpdateModel> {
    HashMap<String, String> param = new HashMap<String, String>();

    public TeacherStudentHomeworkStatusInsertUpdateAsyncTask(HashMap<String, String> param) {
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
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.TeacherStudentHomeworkStatusInsertUpdate), param);
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
