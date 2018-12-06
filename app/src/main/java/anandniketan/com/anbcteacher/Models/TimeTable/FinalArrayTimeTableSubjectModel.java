package anandniketan.com.anbcteacher.Models.TimeTable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 1/11/2018.
 */

public class FinalArrayTimeTableSubjectModel {
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("SubjectID")
    @Expose
    private Integer subjectID;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(Integer subjectID) {
        this.subjectID = subjectID;
    }
//==============Lecture Timing=========
    @SerializedName("StartTimeHour")
    @Expose
    private String startTimeHour;
    @SerializedName("StartTimeMin")
    @Expose
    private String startTimeMin;
    @SerializedName("EndTimeHour")
    @Expose
    private String endTimeHour;
    @SerializedName("EndTimeMin")
    @Expose
    private String endTimeMin;

    public String getStartTimeHour() {
        return startTimeHour;
    }

    public void setStartTimeHour(String startTimeHour) {
        this.startTimeHour = startTimeHour;
    }

    public String getStartTimeMin() {
        return startTimeMin;
    }

    public void setStartTimeMin(String startTimeMin) {
        this.startTimeMin = startTimeMin;
    }

    public String getEndTimeHour() {
        return endTimeHour;
    }

    public void setEndTimeHour(String endTimeHour) {
        this.endTimeHour = endTimeHour;
    }

    public String getEndTimeMin() {
        return endTimeMin;
    }

    public void setEndTimeMin(String endTimeMin) {
        this.endTimeMin = endTimeMin;
    }
}
