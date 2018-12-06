package anandniketan.com.anbcteacher.AsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.HashMap;

import anandniketan.com.anbcteacher.Models.HomeWorkResponse.TeacherStudentHomeworkStatusModel;
import anandniketan.com.anbcteacher.Utility.AppConfiguration;
import anandniketan.com.anbcteacher.WebServicesCall.WebServicesCall;


public class TeacherStudentHomeworkStatusAsynctask extends AsyncTask<Void, Void, TeacherStudentHomeworkStatusModel> {
    HashMap<String, String> param = new HashMap<String, String>();

    public TeacherStudentHomeworkStatusAsynctask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected TeacherStudentHomeworkStatusModel doInBackground(Void... params) {
        String responseString = null;
        TeacherStudentHomeworkStatusModel teacherStudentHomeworkStatusModel = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.TeacherStudentHomeworkStatus), param);
            Gson gson = new Gson();
            teacherStudentHomeworkStatusModel = gson.fromJson(responseString, TeacherStudentHomeworkStatusModel.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return teacherStudentHomeworkStatusModel;
    }

    @Override
    protected void onPostExecute(TeacherStudentHomeworkStatusModel result) {
        super.onPostExecute(result);
    }
}

