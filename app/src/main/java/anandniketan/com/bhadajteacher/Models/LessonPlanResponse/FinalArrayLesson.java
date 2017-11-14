package anandniketan.com.bhadajteacher.Models.LessonPlanResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admsandroid on 10/16/2017.
 */

public class FinalArrayLesson {

    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("Data")
    @Expose
    private List<LessonDatum> data = null;

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<LessonDatum> getData() {
        return data;
    }

    public void setData(List<LessonDatum> data) {
        this.data = data;
    }

}

