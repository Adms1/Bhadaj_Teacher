package anandniketan.com.anbcteacher.Models.WorkPlanResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admsandroid on 11/13/2017.
 */

public class FinalArrayWorkPlan {
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("Class")
    @Expose
    private String _class;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("Month")
    @Expose
    private String month;
    @SerializedName("Data")
    @Expose
    private List<WorkPlanDatum> data = null;

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<WorkPlanDatum> getData() {
        return data;
    }

    public void setData(List<WorkPlanDatum> data) {
        this.data = data;
    }
}
