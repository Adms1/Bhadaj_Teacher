package anandniketan.com.anbcteacher.Models.LessonPlanResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admsandroid on 10/16/2017.
 */

public class MainResponseLesson {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayLesson> finalArrayLesson = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayLesson> getFinalArrayLesson() {
        return finalArrayLesson;
    }

    public void setFinalArrayLesson(List<FinalArrayLesson> finalArrayLesson) {
        this.finalArrayLesson = finalArrayLesson;
    }

}
